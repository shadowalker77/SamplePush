package ir.ayantech.pushnotification.core;

import java.io.Serializable;

import ir.ayantech.pushnotification.action.PushNotificationAction;

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
