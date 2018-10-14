package ir.ayantech.pushnotification.networking.api;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Response;

/**
 * Created by shadoWalker on 5/9/17.
 */

public abstract class PNReasonModel {

    private static final String NO_INTERNET = "دستگاه شما به اینترنت متصل نیست. لطفا بعد از بررسی دوباره تلاش نمایید.";
    private static final String NO_HOST = "ارتباط با سرور برقرار نشد. لطفا دوباره تلاش نمایید.";

    protected void handleError(PNAPI offerAPI, Throwable t, PNResponseStatus status) {
        boolean callback = true;
        boolean canTry = true;
        String message;
        if (t instanceof UnknownHostException)
            message = NO_INTERNET;
        else if (t instanceof TimeoutException)
            message = NO_HOST;
        else if (t instanceof SocketTimeoutException)
            message = NO_HOST;
        else if (t instanceof SocketException) {
            canTry = false;
            message = "";
            callback = false;
        } else if (t instanceof IOException) {
            canTry = false;
            message = "";
            callback = true;
        } else
            message = NO_HOST;
        if (callback)
            status.onFail(offerAPI, message, canTry);
    }

    public boolean isCodeOk(int code) {
        if (code == 200)
            return true;
        return false;
    }

    public <T extends PNResponseModel> T convertJsonStringToObject(String json, Class<T> object) {
        return new Gson().fromJson(json, object);
    }

    public <T extends PNResponseModel> T handleResponse(Response<T> response, Class<T> tClass) throws IOException {
        if (isCodeOk(response.code())) {
            return response.body();
        } else {
            String error = response.errorBody().string();
            Log.e("ERROR: ", error);
            return convertJsonStringToObject(error, tClass);
        }
    }
}
