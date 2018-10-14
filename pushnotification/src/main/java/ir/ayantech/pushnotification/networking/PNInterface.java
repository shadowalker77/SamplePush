package ir.ayantech.pushnotification.networking;

import ir.ayantech.pushnotification.networking.model.PNRequestModel;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by shadoWalker on 5/9/17.
 */

public interface PNInterface {
    @POST("ReportNewDevice")
    Call<PNResponseModel> reportNewDevice(@Body PNRequestModel requestModel);

    @POST("ReportDeviceMobileNumber")
    Call<PNResponseModel> reportDeviceMobileNumber(@Body PNRequestModel requestModel);

    @POST("UpdateDeviceToken")
    Call<PNResponseModel> updateDeviceToken(@Body PNRequestModel requestModel);
}
