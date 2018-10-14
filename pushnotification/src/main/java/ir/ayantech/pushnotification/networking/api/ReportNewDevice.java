package ir.ayantech.pushnotification.networking.api;

import ir.ayantech.pushnotification.networking.model.ExtraInfo;
import ir.ayantech.pushnotification.networking.model.PNInputModel;
import ir.ayantech.pushnotification.networking.model.PNRequestModel;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Call;

/**
 * Created by shadoWalker on 1/19/18.
 */

public class ReportNewDevice extends PNAPI<ReportNewDevice.ReportNewDeviceInputModel, PNResponseModel> {
    @Override
    protected Call<PNResponseModel> getApi(ReportNewDeviceInputModel inputModel) {
        return getApiService().reportNewDevice(new PNRequestModel(inputModel));
    }

    public static class ReportNewDeviceInputModel extends PNInputModel {
        private String ApplicationName;
        private String ApplicationType;
        private String ApplicationVersion;
        private String BusinessName;
        private ExtraInfo ExtraInfo;
        private String OperatorName;
        private String Platform;
        private String RegistrationToken;

        public ReportNewDeviceInputModel(String applicationName, String applicationType, String applicationVersion, String businessName, ir.ayantech.pushnotification.networking.model.ExtraInfo extraInfo, String operatorName, String platform, String registrationToken) {
            ApplicationName = applicationName;
            ApplicationType = applicationType;
            ApplicationVersion = applicationVersion;
            BusinessName = businessName;
            ExtraInfo = extraInfo;
            OperatorName = operatorName;
            Platform = platform;
            RegistrationToken = registrationToken;
        }

        public String getApplicationName() {
            return ApplicationName;
        }

        public void setApplicationName(String applicationName) {
            ApplicationName = applicationName;
        }

        public String getApplicationType() {
            return ApplicationType;
        }

        public void setApplicationType(String applicationType) {
            ApplicationType = applicationType;
        }

        public String getApplicationVersion() {
            return ApplicationVersion;
        }

        public void setApplicationVersion(String applicationVersion) {
            ApplicationVersion = applicationVersion;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String businessName) {
            BusinessName = businessName;
        }

        public ir.ayantech.pushnotification.networking.model.ExtraInfo getExtraInfo() {
            return ExtraInfo;
        }

        public void setExtraInfo(ir.ayantech.pushnotification.networking.model.ExtraInfo extraInfo) {
            ExtraInfo = extraInfo;
        }

        public String getOperatorName() {
            return OperatorName;
        }

        public void setOperatorName(String operatorName) {
            OperatorName = operatorName;
        }

        public String getPlatform() {
            return Platform;
        }

        public void setPlatform(String platform) {
            Platform = platform;
        }

        public String getRegistrationToken() {
            return RegistrationToken;
        }

        public void setRegistrationToken(String registrationToken) {
            RegistrationToken = registrationToken;
        }
    }
}
