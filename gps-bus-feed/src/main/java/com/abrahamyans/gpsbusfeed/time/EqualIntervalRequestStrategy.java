package com.abrahamyans.gpsbusfeed.time;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class EqualIntervalRequestStrategy implements RequestTimingStrategy {

    private final TimeInDay from;
    private final TimeInDay to;
    private final int deltaMillis;
    private final int direction;

    public EqualIntervalRequestStrategy(TimeInDay from, TimeInDay to, int deltaMillis) {
        if (deltaMillis <= 1000)
            throw new IllegalStateException("Illegal value for deltaMillis " + deltaMillis);

        int diff = from.millisTo(to);

        if (Math.abs(diff) <= deltaMillis)
            throw new IllegalArgumentException("Difference between from and to should be by at least " + deltaMillis + " milliseconds");

        this.from = from;
        this.to = to;
        this.deltaMillis = deltaMillis;
        this.direction = diff > 0 ? 1 : -1;
    }

    public EqualIntervalRequestStrategy(int deltaMillis){
        this.from = TimeInDay.startOfDay();
        this.to = TimeInDay.endOfDay();
        this.deltaMillis = deltaMillis;
        this.direction = 1;
    }

    @Override
    public Date getNextLocationRequestDate(Date currentDate) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MILLISECOND, deltaMillis);

        TimeInDay nextProposedTime = new TimeInDay(cal);

        if (nextProposedTime.compareTo(from) >= 0 && nextProposedTime.compareTo(to) <= 0)
            return cal.getTime();

        // Set to from
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, from.getHour());
        cal.set(Calendar.MINUTE, from.getMinute());
        cal.set(Calendar.SECOND, from.getSecond());
        return cal.getTime();
    }
}
