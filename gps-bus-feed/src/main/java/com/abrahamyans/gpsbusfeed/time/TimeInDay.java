package com.abrahamyans.gpsbusfeed.time;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * @author Samvel Abrahamyan
 */

public class TimeInDay implements Comparable<TimeInDay> {

    private int hour;
    private int minute;
    private int second;

    public TimeInDay(Calendar calendar) {
        this(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public TimeInDay(int hour, int minute) {
        this(hour, minute, 0);
    }

    public TimeInDay(int hour, int minute, int second) {

        if (hour < 0 || hour >= 24)
            throw new IllegalArgumentException("Hour should be in [0, 24) found " + hour);
        if (minute < 0 || minute >= 60)
            throw new IllegalArgumentException("Minute should be in [0, 60) found " + minute);
        if (second < 0 || second >= 60)
            throw new IllegalArgumentException("Second should be in [0, 60) found " + second);

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static TimeInDay startOfDay(){
        return new TimeInDay(0, 0, 0);
    }

    public static TimeInDay endOfDay(){
        return new TimeInDay(23, 59, 59);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int millisTo(TimeInDay o) {
        int hDiff = this.hour - o.hour;
        int mDiff = this.minute - o.minute;
        int sDiff = this.second - o.second;

        return 1000 * (3600 * (hDiff) + mDiff * 60 + sDiff);
    }

    @Override
    public int compareTo(@NonNull TimeInDay o) {
        return millisTo(o);
    }

    @Override
    public String toString() {
        return "TimeInDay{" + hour + ":" + minute + ":" + second + '}';
    }
}
