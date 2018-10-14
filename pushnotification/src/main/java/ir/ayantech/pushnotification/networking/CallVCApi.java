package ir.ayantech.pushnotification.networking;


import ir.ayantech.pushnotification.networking.api.PNResponseStatus;
import ir.ayantech.pushnotification.networking.model.PNInputModel;

/**
 * Created by shadoWalker on 5/9/17.
 */

public interface CallVCApi<RequestModel extends PNInputModel> {
    void callApi(PNResponseStatus status, RequestModel inputModel);
}
