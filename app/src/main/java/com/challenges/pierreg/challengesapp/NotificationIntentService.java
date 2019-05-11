package com.challenges.pierreg.challengesapp;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by pierreg on 05/05/2019.
 */
public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 3;

    public NotificationIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationSender sender = new NotificationSender("titre", "test sender", this);
        sender.sendNotif();
    }
}
