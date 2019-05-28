package ir.ayantech.pushnotification.networking.api;

import ir.ayantech.pushnotification.networking.model.PNInputModel;
import ir.ayantech.pushnotification.networking.model.PNRequestModel;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;
import retrofit2.Call;

public class ReportDeviceReceivedNotificationStatus extends PNAPI<ReportDeviceReceivedNotificationStatus.ReportDeviceReceivedNotificationStatusInput, PNResponseModel> {

    @Override
    protected Call<PNResponseModel> getApi(ReportDeviceReceivedNotificationStatusInput inputModel) {
        return getApiService().reportDeviceReceivedNotificationStatus(new PNRequestModel(inputModel));
    }

    public static class ReportDeviceReceivedNotificationStatusInput extends PNInputModel {
        private String MessageID;
        private String Status;
        private Object ExtraInfo;

        public ReportDeviceReceivedNotificationStatusInput(String messageID, String status, Object extraInfo) {
            MessageID = messageID;
            Status = status;
            ExtraInfo = extraInfo;
        }
    }
}
