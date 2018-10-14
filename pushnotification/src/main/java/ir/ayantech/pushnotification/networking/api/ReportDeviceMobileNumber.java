package ir.ayantech.pushnotification.networking.api;

import ir.ayantech.pushnotification.networking.model.PNInputModel;
import ir.ayantech.pushnotification.networking.model.PNRequestModel;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Call;

/**
 * Created by shadoWalker on 1/19/18.
 */

public class ReportDeviceMobileNumber extends PNAPI<ReportDeviceMobileNumber.ReportDeviceMobileNumberInputModel, PNResponseModel> {
    @Override
    protected Call<PNResponseModel> getApi(ReportDeviceMobileNumberInputModel inputModel) {
        return getApiService().reportDeviceMobileNumber(new PNRequestModel(inputModel));
    }

    public static class ReportDeviceMobileNumberInputModel extends PNInputModel {
        private String MobileNumber;
        private String RegistrationToken;

        public ReportDeviceMobileNumberInputModel(String mobileNumber, String registrationToken) {
            MobileNumber = mobileNumber;
            RegistrationToken = registrationToken;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            MobileNumber = mobileNumber;
        }

        public String getRegistrationToken() {
            return RegistrationToken;
        }

        public void setRegistrationToken(String registrationToken) {
            RegistrationToken = registrationToken;
        }
    }
}
