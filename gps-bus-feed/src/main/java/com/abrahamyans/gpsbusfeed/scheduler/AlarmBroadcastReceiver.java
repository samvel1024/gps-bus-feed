package com.abrahamyans.gpsbusfeed.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.abrahamyans.gpsbusfeed.LocationTracker;
import com.abrahamyans.gpsbusfeed.TrackerManager;

/**
 * @author Samvel Abrahamyan
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        TrackerManager trackerManager = TrackerManager.getInstance(context);
        LocationTracker runningTracker = trackerManager.getRunningTracker();

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent gpsTrackerIntent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);

        if (!trackerManager.isTrackerEnabled())
            alarmManager.cancel(pendingIntent);

        RequestDate nextRequestDate = runningTracker.getNextRequestDate(trackerManager.getLastRequestDate());
        if (nextRequestDate.isDatePresent()){
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    nextRequestDate.getDate().getTime(),
                    pendingIntent);
            trackerManager.saveNextRequestDate(nextRequestDate.getDate());
        }
        else if (nextRequestDate == RequestDate.IMMEDIATELY){

        }
        else if (nextRequestDate == RequestDate.NEVER){
            alarmManager.cancel(pendingIntent);
        }
    }
}