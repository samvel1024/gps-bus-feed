package com.abrahamyans.gpsbusfeed;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfig;
import com.abrahamyans.gpsbusfeed.client.tracker.time.RequestDate;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * @author Samvel Abrahamyan
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();

    @Inject
    LocationTracker currentTracker;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received broadcast");
        ((TrackerAwareApplication) context.getApplicationContext()).getGpsBusFeedComponent().inject(this);

        if (!currentTracker.isTrackerRunning()) {
            Log.d(TAG, "Tracker stop requested, shutting down tracker...");
            return;
        }

        TrackerConfig config = currentTracker.getRunningTrackerConfig();
        Date lastRequestDate = currentTracker.getLastRequestDate();
        Date nextRequestDate = extractDate(config.getNextRequestDate(lastRequestDate));
        currentTracker.setLastRequestDate(nextRequestDate);

        if (lastRequestDate != null){
            context.startService(new Intent(context, LocationService.class));
        }

        if (nextRequestDate != null) {
            scheduleNextLocationBroadcast(context, nextRequestDate);
        }
    }


    private Date extractDate(RequestDate requestDate) {
        if (requestDate.isDatePresent()) {
            return requestDate.getDate();
        }
        if (requestDate == RequestDate.IMMEDIATELY) {
            return getDateForImmediate();
        }
        return null;
    }

    private void scheduleNextLocationBroadcast(Context context, Date date) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent gpsTrackerIntent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                date.getTime(),
                pendingIntent);

        currentTracker.setLastRequestDate(date);
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