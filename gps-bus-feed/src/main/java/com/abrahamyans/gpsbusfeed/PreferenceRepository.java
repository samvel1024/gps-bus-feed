package com.abrahamyans.gpsbusfeed;

import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Samvel Abrahamyan
 */

public class PreferenceRepository {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private SharedPreferences preferences;

    public PreferenceRepository(SharedPreferences preferences){
        this.preferences = preferences;
    }

    public boolean isTrackerRunning(){
        return preferences.getBoolean("tracker_enabled", false);
    }

    public void setTrackerRunningState(boolean state){
        preferences.edit().putBoolean("tracker_enabled", state).apply();
    }

    public Date getLastRequestDate(){
        String dateStr = preferences.getString("last_request_date", null);
        if (dateStr == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalStateException("Error parsing date from preferences", e);
        }

    }

    public void setLastRequestDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        preferences.edit().putString("last_request_date", format.format(date)).apply();
    }


}
