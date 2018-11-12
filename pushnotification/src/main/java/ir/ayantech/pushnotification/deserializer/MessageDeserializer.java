package ir.ayantech.pushnotification.deserializer;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageDeserializer()).create();
        Class<? extends PushNotificationAction> classOfAction;
        switch (actionType) {
            case "CustomizableDialog":
                classOfAction = CustomizableDialogAction.class;
                break;
            case "DownloadFile":
                classOfAction = DownloadFileAction.class;
                break;
            case "OpenUrl":
                classOfAction = OpenUrlAction.class;
                break;
            case "Share":
                classOfAction = ShareAction.class;
                break;
            case "TargetedClass":
                classOfAction = TargetedClassAction.class;
                break;
            case "Custom":
                classOfAction = CustomAction.class;
                break;
            default:
                classOfAction = NoAction.class;
                break;
        }
        action = gson.fromJson(json.getAsJsonObject().get("action"), classOfAction);
        return new Message<>(action, actionType, notificationToShow);
    }
}