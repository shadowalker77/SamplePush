package ir.ayantech.pushnotification.core;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

import ir.ayantech.pushnotification.action.CustomAction;
import ir.ayantech.pushnotification.action.CustomizableDialogAction;
import ir.ayantech.pushnotification.action.DownloadFileAction;
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
            Message message = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer()).create().fromJson(json.toString(), new TypeToken<Message<CustomAction>>() {
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
                    message = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer()).create().fromJson(json.toString(), actionType);
            }
            return message;
        }
    }
}
