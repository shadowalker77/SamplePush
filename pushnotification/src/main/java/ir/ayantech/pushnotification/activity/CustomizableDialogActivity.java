package ir.ayantech.pushnotification.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ir.ayantech.pushnotification.R;
import ir.ayantech.pushnotification.action.CustomizableDialogAction;
import ir.ayantech.pushnotification.action.PushNotificationAction;
import ir.ayantech.pushnotification.helper.ImageHelper;

public class CustomizableDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customizable);
        setFinishOnTouchOutside(false);
        WholeView wholeView = deserializeIntent();
        if (wholeView == null) {
            finish();
            return;
        }
        List<ButtonView> buttonViews = new ArrayList<>();
        for (Button button : wholeView.buttons) {
            buttonViews.add(new ButtonView(this, button));
        }
        ((TextView) findViewById(R.id.titleTv)).setText(wholeView.title);
        ((TextView) findViewById(R.id.messageTv)).setText(wholeView.message);
        for (ButtonView buttonView : buttonViews) {
            ((LinearLayout) findViewById(R.id.buttonsLl)).addView(buttonView);
        }
        if (wholeView.imageUrl == null)
            return;
        if (wholeView.imageUrl.isEmpty())
            return;
        ((ImageView) findViewById(R.id.bannerIv)).setImageBitmap(ImageHelper.getBitmapFromURL(wholeView.imageUrl));
    }

    private WholeView deserializeIntent() {
        try {
            return (WholeView) getIntent().getSerializableExtra("wholeView");
        } catch (Exception e) {
            return null;
        }
    }

    public static void createAndStartActivity(Context context,
                                              CustomizableDialogAction.Model model) {
        WholeView wholeView = new WholeView(model.getTitle(),
                model.getMessage(),
                model.getImageUrl(),
                model.getButtons());
        Intent intent = new Intent(context, CustomizableDialogActivity.class);
        intent.putExtra("wholeView", wholeView);
        context.startActivity(intent);
    }

    public static class WholeView implements Serializable {
        private String title;
        private String message;
        private String imageUrl;
        private List<Button> buttons;

        public WholeView(String title, String message, String imageUrl, List<Button> buttons) {
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
            this.buttons = buttons;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        public void setButtons(List<Button> buttons) {
            this.buttons = buttons;
        }
    }

    public static class ButtonView extends AppCompatTextView implements Serializable {

        private Button button;
        private Activity activity;

        public ButtonView(Activity activity, final Button button) {
            super(activity);
            this.button = button;
            this.activity = activity;
            initialize();
        }

        public ButtonView(Activity activity, AttributeSet attrs, Button button) {
            super(activity, attrs);
            this.button = button;
            this.activity = activity;
            initialize();
        }

        public ButtonView(Activity activity, AttributeSet attrs, int defStyleAttr, Button button) {
            super(activity, attrs, defStyleAttr);
            this.button = button;
            this.activity = activity;
            initialize();
        }

        private void initialize() {
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            this.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
            this.setText(button.getText());
            if (this.getLayoutParams() == null)
                this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout.LayoutParams) this.getLayoutParams()).rightMargin = 20;
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                    if (button.getAction() != null)
                        button.getAction().doAction();
                }
            });
        }
    }

    public static class Button<T extends PushNotificationAction> implements Serializable {
        private String text;
        private T action;

        public Button(String text, T action) {
            this.text = text;
            this.action = action;
        }

        public String getText() {
            return text;
        }

        public T getAction() {
            return action;
        }
    }
}
