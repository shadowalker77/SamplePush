package ir.ayantech.pushnotification.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import ir.ayantech.pushnotification.R;
import ir.ayantech.pushnotification.helper.ImageHelper;

public class NotificationUtils {

//    private Context mContext;
//
//    public NotificationUtils(Context mContext) {
//        this.mContext = mContext;
//    }

    public static void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        showNotificationMessage(context, title, message, timeStamp, intent, null);
    }

    public static void showNotificationMessage(final Context context, final String title, final String message, final String timeStamp, Intent intent, String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.drawable.pushIcon;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context, "push");

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                ImageHelper.downloadImage(imageUrl, new ImageHelper.OnBitmapDownloaded() {
                    @Override
                    public void onBitmapDownloaded(Bitmap bitmap) {
                        playNotificationSound(context);
                        if (bitmap != null) {
                            showBigNotification(context, bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                        } else {
                            showSmallNotification(mBuilder, context, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                        }
                    }
                });
            }
        } else {
            showSmallNotification(mBuilder, context, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            playNotificationSound(context);
        }
    }


    private static void showSmallNotification(NotificationCompat.Builder mBuilder,
                                              Context context,
                                              int icon,
                                              String title,
                                              String message,
                                              String timeStamp,
                                              PendingIntent resultPendingIntent,
                                              Uri alarmSound) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);


        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(R.drawable.pushIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        /////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "push";// The id of the channel.
            CharSequence name = context.getResources().getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        /////////////////

        if (notificationManager != null) {
            notificationManager.notify(Config.NOTIFICATION_ID, notification);
        }
    }

    private static void showBigNotification(Context context,
                                            Bitmap bitmap,
                                            NotificationCompat.Builder mBuilder,
                                            int icon,
                                            String title,
                                            String message,
                                            String timeStamp,
                                            PendingIntent resultPendingIntent,
                                            Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(R.drawable.pushIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        /////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "push";// The id of the channel.
            CharSequence name = context.getResources().getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        /////////////////
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    // Playing notification sound
    public static void playNotificationSound(Context context) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
