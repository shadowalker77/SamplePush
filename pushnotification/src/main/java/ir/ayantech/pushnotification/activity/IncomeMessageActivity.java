package ir.ayantech.pushnotification.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ir.ayantech.pushnotification.core.Message;

public class IncomeMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Message message = (Message) getIntent().getSerializableExtra("messageTag");
        message.getAction().setContext(this);
        message.getAction().doAction();
        finish();
    }
}
