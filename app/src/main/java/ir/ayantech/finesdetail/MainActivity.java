package ir.ayantech.finesdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import ir.ayantech.pushnotification.action.OpenUrlAction;
import ir.ayantech.pushnotification.activity.CustomizableDialogActivity;
import ir.ayantech.pushnotification.core.Message;
import ir.ayantech.pushnotification.core.NotificationUtils;
import ir.ayantech.pushnotification.core.PushNotificationCore;
import ir.ayantech.pushnotification.networking.model.ExtraInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushNotificationCore.start(this);
        setContentView(R.layout.activity_main);
        PushNotificationCore.reportExtraInfo(this, new AppExtraInfo("s"));

        findViewById(R.id.notifBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CustomizableDialogActivity.Button> buttons = new ArrayList<>();
                buttons.add(new CustomizableDialogActivity.Button("but1", new Message(new OpenUrlAction("https://www.google.com"), "OpenUrl", null, "1")));
                buttons.add(new CustomizableDialogActivity.Button("but2", new Message(new OpenUrlAction("https://www.google.com"), "OpenUrl", null, "1")));
                buttons.add(new CustomizableDialogActivity.Button("but3", new Message(new OpenUrlAction("https://www.google.com"), "OpenUrl", null, "1")));
                NotificationUtils.showNotificationMessage(MainActivity.this,
                        "Test title",
                        "Test message bodu lorem ipsum",
                        "0",
                        new Intent(),
                        null,
                        buttons);
            }
        });
    }

    public class AppExtraInfo extends ExtraInfo {
        private String HookToken;
        private String HookApplicationName = "TrafficFines";

        public AppExtraInfo(String hookToken) {
            HookToken = hookToken;
        }
    }
}
