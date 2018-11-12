package ir.ayantech.pushnotification.deserializer;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ir.ayantech.pushnotification.action.CustomAction;
import ir.ayantech.pushnotification.action.CustomizableDialogAction;
import ir.ayantech.pushnotification.action.DownloadFileAction;
import ir.ayantech.pushnotification.action.NoAction;
import ir.ayantech.pushnotification.action.OpenUrlAction;
import ir.ayantech.pushnotification.action.PushNotificationAction;
import ir.ayantech.pushnotification.action.ShareAction;
import ir.ayantech.pushnotification.action.TargetedClassAction;
import ir.ayantech.pushnotification.core.Message;
import ir.ayantech.pushnotification.core.NotificationToShow;

public class MessageDeserializer implements JsonDeserializer<Message> {
    @Override
    public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String actionType = json.getAsJsonObject().get("actionType").getAsString();
        NotificationToShow notificationToShow = new Gson().fromJson(json.getAsJsonObject().get("notificationToShow"), NotificationToShow.class);
        PushNotificationAction action;
        switch (actionType) {
            case "CustomizableDialog":
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), CustomizableDialogAction.class);
                break;
            case "DownloadFile":
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), DownloadFileAction.class);
                break;
            case "OpenUrl":
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), OpenUrlAction.class);
                break;
            case "Share":
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), ShareAction.class);
                break;
            case "TargetedClass":
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), TargetedClassAction.class);
                break;
            case "Custom":
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), CustomAction.class);
                break;
            default:
                action = new Gson().fromJson(json.getAsJsonObject().get("action"), NoAction.class);
                break;
        }
        return new Message<>(action, actionType, notificationToShow);
    }
}