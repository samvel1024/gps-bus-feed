package com.abrahamyans.gpsbusfeed.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received broadcast");
    }

    private Date getDateForImmediate() {
        Date curr = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.MILLISECOND, 500);
        return cal.getTime();
    }

}