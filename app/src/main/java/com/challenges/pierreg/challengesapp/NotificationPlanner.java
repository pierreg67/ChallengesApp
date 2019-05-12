package com.challenges.pierreg.challengesapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by pierreg on 11/05/2019.
 */

public class NotificationPlanner {

    private static Calendar m_calendar = null;
    private long test = System.currentTimeMillis();

    public static void start(Context context){
        if(m_calendar == null){
            m_calendar = Calendar.getInstance();
            m_calendar.set(Calendar.HOUR_OF_DAY, 18);
            m_calendar.set(Calendar.MINUTE, 0);
        }
        Intent notifyIntent = new Intent(context ,NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  m_calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY , pendingIntent);
    }

    public static void changeNotifDate(Calendar date){
        m_calendar = Calendar.getInstance();
        m_calendar.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY));
        m_calendar.set(Calendar.MINUTE, date.get(Calendar.MINUTE));
    }
}
