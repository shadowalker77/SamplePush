package ir.ayantech.pushnotification.networking.api;

import android.support.annotation.Nullable;

import ir.ayantech.pushnotification.networking.model.PNResponseModel;

/**
 * Created by shadoWalker on 5/9/17.
 */

public interface PNResponseStatus {
    void onSuccess(PNAPI PNAPI, String message, @Nullable PNResponseModel responseModel);

    void onFail(PNAPI PNAPI,
                String error,
                boolean canTry);
}
