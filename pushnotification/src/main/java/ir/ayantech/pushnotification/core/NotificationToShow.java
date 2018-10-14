package ir.ayantech.pushnotification.core;

import java.io.Serializable;

public class NotificationToShow implements Serializable {
    private String title;
    private String message;
    private String imageUrl;

    public NotificationToShow(String title, String message, String imageUrl) {
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
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
}
