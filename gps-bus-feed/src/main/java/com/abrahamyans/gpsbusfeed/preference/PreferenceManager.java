package com.abrahamyans.gpsbusfeed.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Samvel Abrahamyan
 */

public class PreferenceManager {

    private static final String PREF_NAME = "gps_bus_feed_preferences";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String PREF_LAST_REQUEST_DATE = "last_request_date";

    private static final String PREF_TRACKING_ENABLED = "tracking_enabled";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public synchronized Date getLastLocationRequestDate() {
        String dateStr = prefs.getString(PREF_LAST_REQUEST_DATE, null);
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalStateException("Cannot parse the stored date", e);
        }
    }

    public synchronized void updateLastLocationRequestDate(Date date) {
        String dateStr = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(date);
        prefs.edit().putString(PREF_LAST_REQUEST_DATE, dateStr).apply();
    }

    public synchronized void setTrackingEnabled(boolean tracking){
        prefs.edit().putBoolean(PREF_TRACKING_ENABLED, tracking).apply();
    }

    public synchronized boolean isTrackingEnabled(){
        return prefs.getBoolean(PREF_TRACKING_ENABLED, false);
    }

    public void reset(){
        prefs.edit().clear().apply();
    }
}
