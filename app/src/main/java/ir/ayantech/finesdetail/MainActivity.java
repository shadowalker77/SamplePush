package ir.ayantech.finesdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import ir.ayantech.pushnotification.action.CustomizableDialogAction;
import ir.ayantech.pushnotification.action.DownloadFileAction;
import ir.ayantech.pushnotification.action.NoAction;
import ir.ayantech.pushnotification.action.OpenUrlAction;
import ir.ayantech.pushnotification.action.ShareAction;
import ir.ayantech.pushnotification.action.TargetedClassAction;
import ir.ayantech.pushnotification.activity.CustomizableDialogActivity;
import ir.ayantech.pushnotification.core.Message;
import ir.ayantech.pushnotification.core.NotificationToShow;
import ir.ayantech.pushnotification.core.PushNotificationCore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushNotificationCore.start(this);
        setContentView(R.layout.activity_main);
        Message message = new Message<>(new ShareAction("https://www.google.com"),
                "Share",
                new NotificationToShow("title", "body", null));
        Log.d("json", new Gson().toJson(message));
    }
}
