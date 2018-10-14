package ir.ayantech.pushnotification.networking.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.ayantech.pushnotification.networking.CallVCApi;
import ir.ayantech.pushnotification.networking.PNClient;
import ir.ayantech.pushnotification.networking.PNInterface;
import ir.ayantech.pushnotification.networking.model.PNInputModel;
import ir.ayantech.pushnotification.networking.model.PNRequestModel;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/10/2017.
 */

public abstract class PNAPI<Request extends PNInputModel, ResponseModel extends PNResponseModel>
        extends PNReasonModel implements CallVCApi<Request> {

    private static PNInterface apiService;
    private ResponseModel responseModel;
    private boolean isRunning;
    private List<WrappedRequest> wrappedRequests;

    public static PNInterface getApiService() {
        if (apiService == null)
            apiService = PNClient.getClient().create(PNInterface.class);
        return apiService;
    }

    public PNAPI() {
        this.wrappedRequests = new ArrayList<>();
    }

    public boolean isCallSuccessful(String errorCode) {
        return errorCode.contentEquals("G00000");
    }

    public boolean showSuccessMessage(String errorCode) {
        return !errorCode.contentEquals(PNErrorCode.RESULT_SUCCESS);
    }

    @Override
    public void callApi(PNResponseStatus status, Request inputModel) {
        WrappedRequest wrappedRequest = new WrappedRequest(status, inputModel);
        wrappedRequests.add(wrappedRequest);
        resumeCalls();
    }

    public void resumeCalls() {
        if (!wrappedRequests.isEmpty()) {
            if (!isRunning())
                wrappedRequests.get(wrappedRequests.size() - 1).call();
        }
    }

    protected abstract Call<ResponseModel> getApi(Request inputModel);

    public ResponseModel getResponse() {
        return responseModel;
    }

    public void cancelCall() {
        if (wrappedRequests == null)
            return;
        for (WrappedRequest wrappedRequest : wrappedRequests) {
            wrappedRequest.getResponseModelCall().cancel();
        }
        wrappedRequests.clear();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public class WrappedRequest implements Callback<ResponseModel> {
        private PNResponseStatus responseStatus;
        private Request inputModel;
        private Call<ResponseModel> responseModelCall;

        public WrappedRequest(PNResponseStatus responseStatus, Request inputModel) {
            this.responseStatus = responseStatus;
            this.inputModel = inputModel;
            this.responseModelCall = getApi(inputModel);
        }

        public PNResponseStatus getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(PNResponseStatus responseStatus) {
            this.responseStatus = responseStatus;
        }

        public Request getInputModel() {
            return inputModel;
        }

        public void setInputModel(Request inputModel) {
            this.inputModel = inputModel;
        }

        public Call<ResponseModel> getResponseModelCall() {
            return responseModelCall;
        }

        public void setResponseModelCall(Call<ResponseModel> responseModelCall) {
            this.responseModelCall = responseModelCall;
        }

        public void call() {
            if (inputModel != null)
                Log.d("Request", new PNRequestModel(inputModel).toString());
            isRunning = true;
            getResponseModelCall().clone().enqueue(this);
        }

        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            try {
                wrappedRequests.remove(wrappedRequests.size() - 1);
                isRunning = false;
                responseModel = response.body();
                if (responseModel != null) {
                    Log.d("Response", responseModel.toString());
                    handleCallback(responseModel);
                    if (!wrappedRequests.isEmpty())
                        resumeCalls();
                } else {
                    onFailure(call, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            isRunning = false;
            handleError(PNAPI.this, t, getResponseStatus());
        }

        private <P> void handleCallback(P response) {
            PNResponseModel responseModel = (PNResponseModel) response;
            if (isCallSuccessful(responseModel.getStatus().getCode())) {
                if (showSuccessMessage(responseModel.getStatus().getCode()))
                    getResponseStatus().onSuccess(PNAPI.this, responseModel.getStatus().getDescription(), responseModel);
                else
                    getResponseStatus().onSuccess(PNAPI.this, "", responseModel);
            } else {
                getResponseStatus().onFail(PNAPI.this, responseModel.getStatus().getDescription(), false);
            }
        }
    }
}
