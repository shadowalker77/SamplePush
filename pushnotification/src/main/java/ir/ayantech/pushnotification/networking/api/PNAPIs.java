package ir.ayantech.pushnotification.networking.api;

/**
 * Created by Administrator on 11/5/2017.
 */

public class PNAPIs {

    private static PNAPIs instance;

    private PNAPIs() {
        initialize();
    }

    public static PNAPIs getInstance() {
        if (instance == null)
            instance = new PNAPIs();
        return instance;
    }

    public ReportNewDevice reportNewDevice;
    public ReportDeviceMobileNumber reportDeviceMobileNumber;
    public UpdateDeviceToken updateDeviceToken;
    public ReportDeviceReceivedNotificationStatus reportDeviceReceivedNotificationStatus;

    private void initialize() {
        reportNewDevice = new ReportNewDevice();
        reportDeviceMobileNumber = new ReportDeviceMobileNumber();
        updateDeviceToken = new UpdateDeviceToken();
        reportDeviceReceivedNotificationStatus = new ReportDeviceReceivedNotificationStatus();
    }
}
