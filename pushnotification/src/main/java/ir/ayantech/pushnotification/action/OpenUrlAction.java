package ir.ayantech.pushnotification.action;

import android.content.Context;

import java.io.Serializable;

import ir.ayantech.pushnotification.helper.UrlHelper;

public class OpenUrlAction extends PushNotificationAction implements Serializable {

    private Model model;

    public OpenUrlAction(String url) {
        this.model = new Model(url);
    }

    @Override
    public void doAction() {
        UrlHelper.openUrl(getContext(), model.getUrl());
    }

    public static class Model extends ActionModel {
        private String url;

        public Model(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
