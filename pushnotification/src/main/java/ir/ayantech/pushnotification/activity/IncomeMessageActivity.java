package ir.ayantech.pushnotification.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ir.ayantech.pushnotification.core.Config;
import ir.ayantech.pushnotification.core.Message;
import ir.ayantech.pushnotification.core.PushNotificationCore;

public class IncomeMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Message message = (Message) getIntent().getSerializableExtra("messageTag");
        if (message != null) {
            message.getAction().setContext(this);
            message.getAction().doAction();
            PushNotificationCore.reportMessageStatus(message.getMessageId(), "clicked");
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            notificationManager.cancel(Config.NOTIFICATION_ID);
        } catch (Exception e) {
        }
        try {
            notificationManager.cancel(Config.NOTIFICATION_ID_BIG_IMAGE);
        } catch (Exception e) {
        }
        try {
            notificationManager.cancel(Config.NOTIFICATION_ID_CUSTOM);
        } catch (Exception e) {
        }
        finish();
    }
}
