package ir.ayantech.pushnotification.core;

import java.io.Serializable;
import java.util.List;

import ir.ayantech.pushnotification.activity.CustomizableDialogActivity;

public class NotificationToShow implements Serializable {
    private String title;
    private String message;
    private String imageUrl;
    private List<CustomizableDialogActivity.Button> buttons;

    public NotificationToShow(String title, String message, String imageUrl, List<CustomizableDialogActivity.Button> buttons) {
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.buttons = buttons;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<CustomizableDialogActivity.Button> getButtons() {
        return buttons;
    }
}
