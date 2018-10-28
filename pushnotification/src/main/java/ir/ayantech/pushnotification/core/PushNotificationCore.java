package ir.ayantech.pushnotification.core;

import android.content.Context;
import android.content.Intent;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ir.ayantech.pushnotification.action.CustomAction;
import ir.ayantech.pushnotification.action.CustomizableDialogAction;
import ir.ayantech.pushnotification.action.DownloadFileAction;
import ir.ayantech.pushnotification.action.OpenUrlAction;
import ir.ayantech.pushnotification.action.ShareAction;
import ir.ayantech.pushnotification.action.TargetedClassAction;
import ir.ayantech.pushnotification.activity.IncomeMessageActivity;
import ir.ayantech.pushnotification.helper.PreferencesManager;
import ir.ayantech.pushnotification.networking.api.PNAPIs;
import ir.ayantech.pushnotification.networking.model.ExtraInfo;

public class PushNotificationCore {

    static final String SERVER_NOTIFIED_TOKEN = "serverNotifiedToken";
    static final String SERVER_NOTIFIED_MOBILE = "serverNotifiedMobile";

    public static void start(Context context) {
        PreferencesManager.initialize(context);
        PNAPIs.initialize();
        if (isServerNotifiedToken() && isServerNotifiedMobile())
            return;
        if (PushNotificationUser.getPushNotificationToken().isEmpty())
            return;
        if ((!isServerNotifiedToken()) && !(PushNotificationUser.getPushNotificationToken().isEmpty()))
            MyFirebaseMessagingService.newDevice(context, PushNotificationUser.getPushNotificationToken());
        if ((!isServerNotifiedMobile()) && (!PushNotificationUser.getUserMobile().isEmpty()))
            MyFirebaseMessagingService.reportUserMobileNumber(PushNotificationUser.getUserMobile());

    }

    private static boolean isServerNotifiedToken() {
        return PreferencesManager.readBooleanFromSharedPreferences(SERVER_NOTIFIED_TOKEN);
    }

    private static boolean isServerNotifiedMobile() {
        return PreferencesManager.readBooleanFromSharedPreferences(SERVER_NOTIFIED_MOBILE);
    }

    private static void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationUtils.showNotificationMessage(context, title, message, timeStamp, intent, imageUrl);
    }

    public static Message convertStringToMessage(String data) {
        Message message = new GsonBuilder().create().fromJson(data, new TypeToken<Message<CustomAction>>() {
        }.getType());
        if (message.getAction() != null) {
            Type actionType = null;
            switch (message.getActionType()) {
                case "CustomizableDialog":
                    actionType = new TypeToken<Message<CustomizableDialogAction>>() {
                    }.getType();
                    break;
                case "DownloadFile":
                    actionType = new TypeToken<Message<DownloadFileAction>>() {
                    }.getType();
                    break;
                case "OpenUrl":
                    actionType = new TypeToken<Message<OpenUrlAction>>() {
                    }.getType();
                    break;
                case "Share":
                    actionType = new TypeToken<Message<ShareAction>>() {
                    }.getType();
                    break;
                case "TargetedClass":
                    actionType = new TypeToken<Message<TargetedClassAction>>() {
                    }.getType();
                    break;
                case "Custom":
                    actionType = new TypeToken<Message<CustomAction>>() {
                    }.getType();
                    break;
            }
            if (actionType != null)
                message = new GsonBuilder().create().fromJson(data, actionType);
        }
        return message;
    }

    public static void receivedMessageLogic(Context context, Message message) {
        if (message.getNotificationToShow() == null && message.getAction() != null) {
            message.getAction().setContext(context);
            message.getAction().doAction();
        }
        if (message.getNotificationToShow() != null && message.getAction() == null) {
            showNotificationMessageWithBigImage(context,
                    message.getNotificationToShow().getTitle(),
                    message.getNotificationToShow().getMessage(),
                    "0",
                    new Intent(),
                    message.getNotificationToShow().getImageUrl());
        }
        if (message.getNotificationToShow() != null && message.getAction() != null) {
            Intent intent = new Intent(context, IncomeMessageActivity.class);
            intent.putExtra("messageTag", message);
            showNotificationMessageWithBigImage(context,
                    message.getNotificationToShow().getTitle(),
                    message.getNotificationToShow().getMessage(),
                    "0",
                    intent,
                    message.getNotificationToShow().getImageUrl());
        }
    }

    public static <T extends ExtraInfo> void reportExtraInfo(Context context, T extraInfo) {
        PushNotificationUser.setPushNotificationExtraInfo(extraInfo);
        if (isServerNotifiedToken())
            MyFirebaseMessagingService.sendRegistrationToServer(context, PushNotificationUser.getPushNotificationToken(), extraInfo);
    }

    public static void reportUserMobileNumber(String mobileNumber) {
        PushNotificationUser.setUserMobile(mobileNumber);
        PreferencesManager.saveToSharedPreferences(SERVER_NOTIFIED_MOBILE, false);
        MyFirebaseMessagingService.reportUserMobileNumber(mobileNumber);
    }
}
