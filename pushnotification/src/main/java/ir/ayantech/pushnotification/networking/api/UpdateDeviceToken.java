package ir.ayantech.pushnotification.networking.api;

import ir.ayantech.pushnotification.networking.model.PNInputModel;
import ir.ayantech.pushnotification.networking.model.PNRequestModel;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Call;

/**
 * Created by shadoWalker on 1/19/18.
 */

public class UpdateDeviceToken extends PNAPI<UpdateDeviceToken.UpdateDeviceTokenInputModel, PNResponseModel> {
    @Override
    protected Call<PNResponseModel> getApi(UpdateDeviceTokenInputModel inputModel) {
        return getApiService().updateDeviceToken(new PNRequestModel(inputModel));
    }

    public static class UpdateDeviceTokenInputModel extends PNInputModel {
        private String NewRegistrationToken;
        private String OldRegistrationToken;

        public UpdateDeviceTokenInputModel(String newRegistrationToken, String oldRegistrationToken) {
            NewRegistrationToken = newRegistrationToken;
            OldRegistrationToken = oldRegistrationToken;
        }

        public String getNewRegistrationToken() {
            return NewRegistrationToken;
        }

        public void setNewRegistrationToken(String newRegistrationToken) {
            NewRegistrationToken = newRegistrationToken;
        }

        public String getOldRegistrationToken() {
            return OldRegistrationToken;
        }

        public void setOldRegistrationToken(String oldRegistrationToken) {
            OldRegistrationToken = oldRegistrationToken;
        }
    }
}
