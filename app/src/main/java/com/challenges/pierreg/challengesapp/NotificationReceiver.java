package com.challenges.pierreg.challengesapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by pierreg on 05/05/2019.
 */

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, NotificationIntentService.class);
        context.startService(intent1);
    }
}
