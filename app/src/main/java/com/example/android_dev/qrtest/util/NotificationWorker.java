package com.example.android_dev.qrtest.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat.Builder;

import com.example.android_dev.qrtest.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationWorker {
    private static final int NOTIFICATION_ID = 25;
    private Builder builder;
    private Context context;

    public NotificationWorker(Context context) {
        this.context = context;
    }

    public void showNotification(String msg) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder = new Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setSound(alarmSound)
                .setContentText(msg);
        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(600);
        v.vibrate(600);

    }

}
