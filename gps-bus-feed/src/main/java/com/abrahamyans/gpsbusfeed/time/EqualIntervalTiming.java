package com.abrahamyans.gpsbusfeed.time;

import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class EqualIntervalTiming implements RequestTiming {

    private static final int MIN_INTERVAL_MILLIS = 1500;
    private static final long serialVersionUID = -8871637799063373295L;

    private final int deltaMillis;

    public EqualIntervalTiming(int deltaMillis) {
        if (deltaMillis <= MIN_INTERVAL_MILLIS)
            throw new IllegalStateException("Illegal value for deltaMillis " + deltaMillis);
        this.deltaMillis = deltaMillis;
    }

    @Override
    public RequestDate nextRequestDate(Date lastRequestDate) {
        lastRequestDate = lastRequestDate == null ? new Date(): lastRequestDate;
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastRequestDate);
        cal.add(Calendar.MILLISECOND, deltaMillis);

        return new RequestDate(cal.getTime());
    }



}
