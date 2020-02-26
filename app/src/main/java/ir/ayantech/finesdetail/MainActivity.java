package ir.ayantech.finesdetail;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import ir.ayantech.pushnotification.action.CustomizableDialogAction;
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
                buttons.add(new CustomizableDialogActivity.Button("دکمه ۱", new Message(new OpenUrlAction("https://www.google.com"), "OpenUrl", null, "1")));
                buttons.add(new CustomizableDialogActivity.Button("دکمه ۲", new Message(
                        new CustomizableDialogAction("عنوان", "پیام اینه", "", null),
                        "OpenUrl", null, "1")));
//                buttons.add(new CustomizableDialogActivity.Button("but3", new Message(new OpenUrlAction("https://www.google.com"), "OpenUrl", null, "1")));
                NotificationUtils.showNotificationMessage(MainActivity.this,
                        "عنوان تستی",
                        "یک پیام طولانی جهت تست چند خطی شدن نوتیفیکیشن برای این پیام در نظر گرفته شده است. این پیام باید امکان مشاهده کامل را داشته باشد. لذا کامل چک شود.",
                        "0",
                        new Intent(),
                        "https://static.farakav.com/files/pictures/thumb/01476557.jpg",
//                        "",
                        buttons,
                        "https://www.pinclipart.com/picdir/big/158-1587103_report-icons-test-icon-clipart.png",
//                        "",
                        true);
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
