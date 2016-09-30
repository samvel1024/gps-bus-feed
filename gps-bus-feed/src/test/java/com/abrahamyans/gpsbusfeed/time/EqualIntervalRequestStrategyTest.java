package com.abrahamyans.gpsbusfeed.time;

import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class EqualIntervalRequestStrategyTest {

    @Test
    public void testNextMidnightExcluded() {
        EqualIntervalTiming strategy = new EqualIntervalTiming(
                new TimeInDay(10, 0),
                new TimeInDay(21, 0),
                3000
        );

        Date curr = composeDate(2016, 9, 26, 16, 0, 0);

        RequestDate next = strategy.getNextLocationRequestDate(curr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.MILLISECOND, 3000);
        Assert.assertEquals(next.getDate(), cal.getTime());
    }

    @Test
    public void testNextMidnightExcludedWithOverflow(){
        EqualIntervalTiming strategy = new EqualIntervalTiming(
                new TimeInDay(10, 0),
                new TimeInDay(21, 0),
                3000
        );

        Date curr = composeDate(2016, 9, 26, 22, 0, 0);

        RequestDate next = strategy.getNextLocationRequestDate(curr);

        Calendar nextCal = fromDate(next.getDate());
        Assert.assertEquals(2016, nextCal.get(Calendar.YEAR));
        Assert.assertEquals(9, nextCal.get(Calendar.MONTH));
        Assert.assertEquals(27, nextCal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(10, nextCal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, nextCal.get(Calendar.MINUTE));
    }

    @Test
    public void testNextMidnightIncluded() {
        EqualIntervalTiming strategy = new EqualIntervalTiming(
                new TimeInDay(6, 0),
                new TimeInDay(1, 0),
                4000
        );

        Date curr = composeDate(2016, 9, 26, 16, 0, 0);

        RequestDate next = strategy.getNextLocationRequestDate(curr);

        Calendar nextCal = fromDate(next.getDate());
        Assert.assertEquals(2016, nextCal.get(Calendar.YEAR));
        Assert.assertEquals(9, nextCal.get(Calendar.MONTH));
        Assert.assertEquals(26, nextCal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(16, nextCal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, nextCal.get(Calendar.MINUTE));
        Assert.assertEquals(4, nextCal.get(Calendar.SECOND));

    }

    @Test
    public void testNextMidnightIncludedWithOverflow() {
        EqualIntervalTiming strategy = new EqualIntervalTiming(
                new TimeInDay(6, 0),
                new TimeInDay(1, 0),
                4000
        );

        Date curr = composeDate(2016, 9, 26, 2, 0, 0);

        RequestDate next = strategy.getNextLocationRequestDate(curr);

        Calendar nextCal = fromDate(next.getDate());
        Assert.assertEquals(2016, nextCal.get(Calendar.YEAR));
        Assert.assertEquals(9, nextCal.get(Calendar.MONTH));
        Assert.assertEquals(26, nextCal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(6, nextCal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, nextCal.get(Calendar.MINUTE));
        Assert.assertEquals(0, nextCal.get(Calendar.SECOND));

    }

    private static Calendar fromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    private static Date composeDate(int year, int mon, int day, int h, int m, int s){
        Calendar cal = Calendar.getInstance();
        cal.set(year, mon, day, h, m, s);
        return cal.getTime();
    }

}
