package com.abrahamyans.gpsbusfeed.client.tracker.time;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class TimeTestHelper {

    public static Calendar fromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    public static Date composeDate(int year, int mon, int day, int h, int m, int s){
        Calendar cal = Calendar.getInstance();
        cal.set(year, mon, day, h, m, s);
        return cal.getTime();
    }
}
