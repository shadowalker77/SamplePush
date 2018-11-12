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

    public static class MessageDeserializer implements JsonDeserializer<Message> {
        @Override
        public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String actionType = json.getAsJsonObject().get("actionType").getAsString();
            NotificationToShow notificationToShow = new Gson().fromJson(json.getAsJsonObject().get("notificationToShow"), NotificationToShow.class);
            switch (actionType) {
                case "CustomizableDialog":
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), CustomizableDialogAction.class),
                            actionType,
                            notificationToShow);
                case "DownloadFile":
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), DownloadFileAction.class),
                            actionType,
                            notificationToShow);
                case "OpenUrl":
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), OpenUrlAction.class),
                            actionType,
                            notificationToShow);
                case "Share":
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), ShareAction.class),
                            actionType,
                            notificationToShow);
                case "TargetedClass":
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), TargetedClassAction.class),
                            actionType,
                            notificationToShow);
                case "Custom":
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), CustomAction.class),
                            actionType,
                            notificationToShow);
                default:
                    return new Message<>(new Gson().fromJson(json.getAsJsonObject().get("action"), NoAction.class),
                            actionType,
                            notificationToShow);
            }
        }
    }
}
