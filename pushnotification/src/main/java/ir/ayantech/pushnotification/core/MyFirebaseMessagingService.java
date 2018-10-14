package ir.ayantech.pushnotification.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ir.ayantech.pushnotification.R;
import ir.ayantech.pushnotification.helper.PreferencesManager;
import ir.ayantech.pushnotification.networking.api.PNAPI;
import ir.ayantech.pushnotification.networking.api.PNAPIs;
import ir.ayantech.pushnotification.networking.api.PNResponseStatus;
import ir.ayantech.pushnotification.networking.api.ReportDeviceMobileNumber;
import ir.ayantech.pushnotification.networking.api.ReportNewDevice;
import ir.ayantech.pushnotification.networking.model.ExtraInfo;
import ir.ayantech.pushnotification.networking.model.PNResponseModel;

import static ir.ayantech.pushnotification.core.PushNotificationCore.SERVER_NOTIFIED_MOBILE;
import static ir.ayantech.pushnotification.core.PushNotificationCore.SERVER_NOTIFIED_TOKEN;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null)
            return;
        if (remoteMessage.getData().size() > 0) {
            try {
                handleDataMessage(remoteMessage.getData());
            } catch (Exception e) {
            }
        }
    }

    private void handleDataMessage(Map<String, String> arrayMap) {
        try {
            String body = arrayMap.get("message");
            PushNotificationCore.receivedMessageLogic(getApplicationContext(), PushNotificationCore.convertStringToMessage(body));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("newToken", s);
        PreferencesManager.saveToSharedPreferences(SERVER_NOTIFIED_TOKEN, false);
        PNAPIs.initialize();
        newDevice(getApplicationContext(), s);
    }


    public static void newDevice(Context context, String token) {
        storeRegIdInPref(token);
        sendRegistrationToServer(context, token, new ExtraInfo());
    }

    public static <T extends ExtraInfo> void sendRegistrationToServer(Context context, final String token, T extraInfo) {
        PNAPIs.reportNewDevice.callApi(getResponseStatus(),
                new ReportNewDevice.ReportNewDeviceInputModel(context.getResources().getString(R.string.applicationName),
                        context.getResources().getString(R.string.applicationType),
                        getApplicationVersion(context),
                        context.getResources().getString(R.string.businessName),
                        extraInfo,
                        getOperatorName(context),
                        "android",
                        token));
    }

    private static String getOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            return manager.getNetworkOperatorName();
        } else
            return null;
    }

    private static String getApplicationVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void reportUserMobileNumber(String mobileNumber) {
        PNAPIs.reportDeviceMobileNumber.callApi(new PNResponseStatus() {
            @Override
            public void onSuccess(PNAPI PNAPI, String message, @Nullable PNResponseModel responseModel) {
                PreferencesManager.saveToSharedPreferences(SERVER_NOTIFIED_MOBILE, true);
            }

            @Override
            public void onFail(PNAPI PNAPI, String error, boolean canTry) {

            }
        }, new ReportDeviceMobileNumber.ReportDeviceMobileNumberInputModel(mobileNumber, PushNotificationUser.getPushNotificationToken()));
    }

    public static void storeRegIdInPref(String token) {
        PushNotificationUser.setPushNotificationToken(token);
    }

    public static PNResponseStatus getResponseStatus() {
        return new PNResponseStatus() {
            @Override
            public void onSuccess(PNAPI PNAPI, String message, @Nullable PNResponseModel responseModel) {
                PreferencesManager.saveToSharedPreferences(SERVER_NOTIFIED_TOKEN, true);
            }

            @Override
            public void onFail(PNAPI PNAPI, String error, boolean canTry) {
                Log.e("Error", error);
            }
        };
    }
}
