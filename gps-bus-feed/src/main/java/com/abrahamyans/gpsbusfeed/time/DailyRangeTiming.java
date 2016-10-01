package com.abrahamyans.gpsbusfeed.time;

import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class DailyRangeTiming implements RequestTiming{

    private RequestTiming requestTiming;

    private final TimeInDay from;
    private final TimeInDay to;

    /**
     * Timing strategy between certain times in day (i.e. from 10:30 to 22:45)
     * @param from starting time
     * @param to end time
     * @param timing the base timing strategy on top of which the bounds will be applied
     */
    DailyRangeTiming(TimeInDay from, TimeInDay to, RequestTiming timing) {
        this.from = from;
        this.to = to;
        this.requestTiming = timing;
    }

    @Override
    public RequestDate getNextLocationRequestDate(Date lastRequestDate) {

        RequestDate date = requestTiming.getNextLocationRequestDate(lastRequestDate);

        if (!date.isDatePresent() || isBeforeTo(date))
            return date;

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastRequestDate);
        cal.add(Calendar.DAY_OF_MONTH, isMidnightIncluded() ? 0 : 1);
        cal.set(Calendar.HOUR_OF_DAY, from.getHour());
        cal.set(Calendar.MINUTE, from.getMinute());
        cal.set(Calendar.SECOND, from.getSecond());
        return new RequestDate(cal.getTime());
    }

    private boolean isBeforeTo(RequestDate date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getDate());
        TimeInDay nextProposedTime = new TimeInDay(cal);
        boolean isMidnightIncluded = isMidnightIncluded();
        TimeInDay sooner = isMidnightIncluded ? to : from;
        TimeInDay later = isMidnightIncluded ? from : to;
        return isMidnightIncluded ^ (nextProposedTime.compareTo(sooner) >= 0 && nextProposedTime.compareTo(later) <= 0);
    }

    private boolean isMidnightIncluded(){
        return from.compareTo(to) >= 0;
    }

    public static RequestTiming betweenHours(TimeInDay from, TimeInDay to, RequestTiming base){
        return new DailyRangeTiming(from, to, base);
    }
}
