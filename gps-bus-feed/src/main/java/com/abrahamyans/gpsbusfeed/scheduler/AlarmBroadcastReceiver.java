package com.abrahamyans.gpsbusfeed.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

        TrackerManager trackerManager = TrackerManager.getInstance(context);

        if (!trackerManager.isTrackerEnabled()) {
            Log.d(TAG, "Tracker stop is requested, shutting down broadcast receiver");
            return;
        }
 
        LocationTracker tracker = trackerManager.getRunningTracker();
        RequestDate nextRequestDate = tracker.getNextRequestDate(trackerManager.getLastLocationRequestDate());

        if (nextRequestDate == RequestDate.NEVER) {
            Log.d(TAG, "Next request date: NEVER. Stopping");
            trackerManager.stopTracker();
            return;
        }

        context.sendBroadcast(new Intent(context, ServiceInvokerReceiver.class));
        Date nextDate = null;
        if (nextRequestDate.isDatePresent()) {
            nextDate = nextRequestDate.getDate();
        } else if (nextRequestDate == RequestDate.IMMEDIATELY) {
            nextDate = getDateForImmediate();
        }
        scheduleLocationBroadcast(context, nextDate);
        trackerManager.close(context);
    }

    private void scheduleLocationBroadcast(Context context, Date date) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent gpsTrackerIntent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                date.getTime(),
                pendingIntent);

        TrackerManager.getInstance(context).saveNextRequestDate(date);
        Log.d(TAG, "Scheduled broadcast for " + date);
    }


    private Date getDateForImmediate() {
        Date curr = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.MILLISECOND, 500);
        return cal.getTime();
    }


}