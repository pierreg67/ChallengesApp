package com.challenges.pierreg.challengesapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by pierreg on 11/05/2019.
 */

public class NotificationSender{

    private String m_title;
    private String m_message;
    private Context m_context;
    private NotificationCompat.Builder m_notifBuilder;

    public NotificationSender(String title, String message, Context context) {
        m_title = title;
        m_message = message;
        m_context = context;
        m_notifBuilder = createNotif();
    }

    private NotificationCompat.Builder createNotif(){

        Intent notifyIntent = new Intent(m_context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(m_context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(m_context)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle(m_title)
                .setContentText(m_message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public void sendNotif(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(m_context);
        notificationManager.notify(1, m_notifBuilder.build());
    }
}
