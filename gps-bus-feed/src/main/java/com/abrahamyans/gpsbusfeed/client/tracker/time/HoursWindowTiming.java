package com.abrahamyans.gpsbusfeed.client.tracker.time;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class HoursWindowTiming implements RequestTiming {

    private static final long serialVersionUID = -6979182531465786973L;
    private final TimeInDay from;
    private final TimeInDay to;
    private final boolean isMidnightIncluded;
    private RequestTiming requestTiming;

    /**
     * Timing strategy between certain times in day (i.e. from 10:30 to 22:45)
     *
     * @param from   starting time
     * @param to     end time
     * @param timing the base timing strategy on top of which the bounds will be applied
     */
    public HoursWindowTiming(TimeInDay from, TimeInDay to, RequestTiming timing) {
        if (from == null || to == null || timing == null)
            throw new IllegalArgumentException("None of the arguments can be null");
        this.from = from;
        this.to = to;
        this.requestTiming = timing;
        this.isMidnightIncluded = from.compareTo(to) >= 0;
    }

    @Override
    public RequestDate nextRequestDate(Date lastRequestDate) {

        RequestDate date = requestTiming.nextRequestDate(lastRequestDate);

        if (!date.isDatePresent() || isBeforeTo(date))
            return date;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getDate());
        cal.add(Calendar.DAY_OF_MONTH, isMidnightIncluded ? 0 : 1);
        cal.set(Calendar.HOUR_OF_DAY, from.getHour());
        cal.set(Calendar.MINUTE, from.getMinute());
        cal.set(Calendar.SECOND, from.getSecond());
        return new RequestDate(cal.getTime());
    }

    private boolean isBeforeTo(RequestDate date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getDate());
        TimeInDay nextProposedTime = new TimeInDay(cal);
        TimeInDay sooner = isMidnightIncluded ? to : from;
        TimeInDay later = isMidnightIncluded ? from : to;
        return isMidnightIncluded ^ (nextProposedTime.compareTo(sooner) >= 0 && nextProposedTime.compareTo(later) <= 0);
    }


    public static Builder from(int h, int m){
        return new Builder(new TimeInDay(h, m));
    }

    public static class Builder{

        TimeInDay from;
        TimeInDay to;
        RequestTiming timing;

        private Builder(TimeInDay from){
            this.from = from;
        }

        public Builder to(int h, int m){
            this.to = new TimeInDay(h, m);
            return this;
        }

        public Builder timing(RequestTiming timing){
            this.timing = timing;
            return this;
        }

        public HoursWindowTiming build(){
            return new HoursWindowTiming(from, to, timing);
        }


    }

}
