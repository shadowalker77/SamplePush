package ir.ayantech.pushnotification.core;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

import ir.ayantech.pushnotification.action.CustomAction;
import ir.ayantech.pushnotification.action.CustomizableDialogAction;
import ir.ayantech.pushnotification.action.DownloadFileAction;
import ir.ayantech.pushnotification.action.NoAction;
import ir.ayantech.pushnotification.action.OpenUrlAction;
import ir.ayantech.pushnotification.action.PushNotificationAction;
import ir.ayantech.pushnotification.action.ShareAction;
import ir.ayantech.pushnotification.action.TargetedClassAction;

public class Message<T extends PushNotificationAction> implements Serializable {
    private T action;
    private String actionType;
    private NotificationToShow notificationToShow;

    public Message(T action, String actionType, NotificationToShow notificationToShow) {
        this.action = action;
        this.actionType = actionType;
        this.notificationToShow = notificationToShow;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public T getAction() {
        return action;
    }

    public void setAction(T action) {
        this.action = action;
    }

    public NotificationToShow getNotificationToShow() {
        return notificationToShow;
    }

    public void setNotificationToShow(NotificationToShow notificationToShow) {
        this.notificationToShow = notificationToShow;
    }
}
