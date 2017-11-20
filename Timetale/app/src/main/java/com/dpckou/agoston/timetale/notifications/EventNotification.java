package com.dpckou.agoston.timetale.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by agoston on 2017.11.20..
 */

public class EventNotification extends BroadcastReceiver {

    public static final String NOTIFICATION = "notification";
    public static final String NOTIFICATION_ID = "notification_id";

    @Override
    public void onReceive(final Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
    }
}
