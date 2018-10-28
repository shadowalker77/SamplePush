package ir.ayantech.pushnotification.core;

import com.google.gson.Gson;

import ir.ayantech.pushnotification.helper.PreferencesManager;
import ir.ayantech.pushnotification.networking.model.ExtraInfo;

public class PushNotificationUser {

    public static final String PUSH_NOTIFICATION_TOKEN = "pushNotificationToken";
    public static final String USER_MOBILE = "userMobile";
    public static final String PUSH_NOTIFICATION_EXTRA_INFO = "pushNotificationExtraInfo";
    private static final String PUSH_NOTIFICATION_EXTRA_INFO_CLASS = "pushNotificationExtraInfoClass";

    public static String getPushNotificationToken() {
        return PreferencesManager.readStringFromSharedPreferences(PUSH_NOTIFICATION_TOKEN);
    }

    public static String getUserMobile() {
        return PreferencesManager.readStringFromSharedPreferences(USER_MOBILE);
    }

    public static <T extends ExtraInfo> T getPushNotificationExtraInfo() throws ClassNotFoundException {
        return ((T) new Gson().fromJson(PreferencesManager.readStringFromSharedPreferences(PUSH_NOTIFICATION_EXTRA_INFO),
                Class.forName(getPushNotificationExtraInfoClass())));
    }

    private static String getPushNotificationExtraInfoClass() {
        return PreferencesManager.readStringFromSharedPreferences(PUSH_NOTIFICATION_EXTRA_INFO_CLASS);
    }

    public static void setPushNotificationToken(String pushNotificationToken) {
        PreferencesManager.saveToSharedPreferences(PUSH_NOTIFICATION_TOKEN, pushNotificationToken);
    }

    public static void setUserMobile(String userMobile) {
        PreferencesManager.saveToSharedPreferences(USER_MOBILE, userMobile);
    }

    public static <T extends ExtraInfo> void setPushNotificationExtraInfo(T pushNotificationExtraInfo) {
        setPushNotificationExtraInfoClass(pushNotificationExtraInfo);
        PreferencesManager.saveToSharedPreferences(PUSH_NOTIFICATION_EXTRA_INFO, new Gson().toJson(pushNotificationExtraInfo));
    }

    private static <T extends ExtraInfo> void setPushNotificationExtraInfoClass(T pushNotificationExtraInfo) {
        PreferencesManager.saveToSharedPreferences(PUSH_NOTIFICATION_EXTRA_INFO_CLASS, pushNotificationExtraInfo.getClass().getName());
    }
}
