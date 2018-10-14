package ir.ayantech.pushnotification.core;

import ir.ayantech.pushnotification.helper.PreferencesManager;

public class PushNotificationUser {

    public static final String PUSH_NOTIFICATION_TOKEN = "pushNotificationToken";
    public static final String USER_MOBILE = "userMobile";

    public static String getPushNotificationToken() {
        return PreferencesManager.readStringFromSharedPreferences(PUSH_NOTIFICATION_TOKEN);
    }

    public static String getUserMobile() {
        return PreferencesManager.readStringFromSharedPreferences(USER_MOBILE);
    }

    public static void setPushNotificationToken(String pushNotificationToken) {
        PreferencesManager.saveToSharedPreferences(PUSH_NOTIFICATION_TOKEN, pushNotificationToken);
    }

    public static void setUserMobile(String userMobile) {
        PreferencesManager.saveToSharedPreferences(USER_MOBILE, userMobile);
    }
}
