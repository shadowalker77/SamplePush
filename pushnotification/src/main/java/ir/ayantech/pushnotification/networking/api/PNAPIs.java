package ir.ayantech.pushnotification.networking.api;

/**
 * Created by Administrator on 11/5/2017.
 */

public class PNAPIs {

    public static ReportNewDevice reportNewDevice;
    public static ReportDeviceMobileNumber reportDeviceMobileNumber;
    public static UpdateDeviceToken updateDeviceToken;

    public static void initialize() {
        reportNewDevice = new ReportNewDevice();
        reportDeviceMobileNumber = new ReportDeviceMobileNumber();
        updateDeviceToken = new UpdateDeviceToken();
    }
}
