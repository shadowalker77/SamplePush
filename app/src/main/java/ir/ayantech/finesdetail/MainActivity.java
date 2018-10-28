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
import ir.ayantech.pushnotification.networking.model.ExtraInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushNotificationCore.start(this);
        setContentView(R.layout.activity_main);
        PushNotificationCore.reportExtraInfo(this, new AppExtraInfo("s"));
    }

    public class AppExtraInfo extends ExtraInfo {
        private String HookToken;
        private String HookApplicationName = "TrafficFines";

        public AppExtraInfo(String hookToken) {
            HookToken = hookToken;
        }
    }
}
