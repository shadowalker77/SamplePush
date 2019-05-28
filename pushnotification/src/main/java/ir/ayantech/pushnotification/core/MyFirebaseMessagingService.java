package ir.ayantech.pushnotification.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import ir.ayantech.pushnotification.R;
import ir.ayantech.pushnotification.deserializer.MessageDeserializer;
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
        PushNotificationCore.reportMessageStatus(remoteMessage.getMessageId(), "delivered");
        if (remoteMessage.getData().size() > 0) {
            try {
                handleDataMessage(remoteMessage.getData(), remoteMessage.getMessageId());
            } catch (Exception e) {
            }
        }
    }

    private void handleDataMessage(Map<String, String> arrayMap, String messageId) {
        try {
            String body = arrayMap.get("message");
            Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer(messageId)).create();
            PushNotificationCore.receivedMessageLogic(getApplicationContext(), gson.fromJson(body, Message.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("newToken", s);
        PreferencesManager.saveToSharedPreferences(SERVER_NOTIFIED_TOKEN, false);
        if (!s.isEmpty())
            newDevice(getApplicationContext(), s);
    }

    public static void newDevice(Context context, String token) {
        storeRegIdInPref(token);
        ExtraInfo extraInfo = new ExtraInfo();
        try {
            extraInfo = PushNotificationUser.getPushNotificationExtraInfo();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sendRegistrationToServer(context, token, extraInfo);
    }

    public static <T extends ExtraInfo> void sendRegistrationToServer(Context context, final String token, T extraInfo) {
        if (token.isEmpty())
            return;
        PNAPIs.getInstance().reportNewDevice.callApi(getResponseStatus(),
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
        if (PushNotificationUser.getPushNotificationToken().isEmpty())
            return;
        PNAPIs.getInstance().reportDeviceMobileNumber.callApi(new PNResponseStatus() {
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
                Log.d("AyanPush", "FCM token successfully reported to the server.");
            }

            @Override
            public void onFail(PNAPI PNAPI, String error, boolean canTry) {
                Log.e("Error", error);
                Log.e("AyanPush", "FCM token not reported to the server. Did you correctly set \"properties.xml\" file?");
            }
        };
    }
}
