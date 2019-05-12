package com.challenges.pierreg.challengesapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Calendar;

/**
 * Created by pierreg on 05/05/2019.
 */
public class NotificationIntentService extends IntentService {

    public NotificationIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String message = getNotifMessage(this);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifBuilder =  new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle("titre")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notifBuilder.build());
    }

    public void sendNotif(Context context){
        String message = getNotifMessage(context);
        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifBuilder =  new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle("titre")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notifBuilder.build());
    }

    private String getNotifMessage(Context context) {
        ChallengeDAO dao = new ChallengeDAO(context);
        int nbr = 0;
        try {
            dao.open();
            nbr = dao.countChallengeAtDate(Calendar.getInstance());
        }catch (Exception e){
            e.printStackTrace();
        }
        return nbr + " challenge to do!";
    }
}
