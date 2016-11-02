package com.abrahamyans.gpsbusfeed.client.tracker.time;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.abrahamyans.gpsbusfeed.client.tracker.time.TimeTestHelper.composeDate;
import static com.abrahamyans.gpsbusfeed.client.tracker.time.TimeTestHelper.fromDate;

/**
 * @author Samvel Abrahamyan
 */

public class HoursWindowTimingTest {

    @Test
    public void testNextMidnightExcludedWithOverflow() {
        HoursWindowTiming strategy = new HoursWindowTiming(
                new TimeInDay(10, 0),
                new TimeInDay(21, 0),
                new EqualIntervalTiming(3000)
        );

        Date curr = composeDate(2016, 9, 26, 22, 0, 0);

        RequestDate next = strategy.nextRequestDate(curr);

        Calendar nextCal = fromDate(next.getDate());
        Assert.assertEquals(2016, nextCal.get(Calendar.YEAR));
        Assert.assertEquals(9, nextCal.get(Calendar.MONTH));
        Assert.assertEquals(27, nextCal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(10, nextCal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, nextCal.get(Calendar.MINUTE));
    }

    @Test
    public void testNextMidnightExcluded() {
        HoursWindowTiming strategy = new HoursWindowTiming(
                new TimeInDay(10, 0),
                new TimeInDay(21, 0),
                new EqualIntervalTiming(3000)
        );

        Date curr = composeDate(2016, 9, 26, 16, 0, 0);

        RequestDate next = strategy.nextRequestDate(curr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.MILLISECOND, 3000);
        Assert.assertEquals(next.getDate(), cal.getTime());
    }


    @Test
    public void testNextMidnightIncluded() {
        HoursWindowTiming strategy = new HoursWindowTiming(
                new TimeInDay(6, 0),
                new TimeInDay(1, 0),
                new EqualIntervalTiming(4000)
        );

        Date curr = composeDate(2016, 9, 26, 16, 0, 0);

        RequestDate next = strategy.nextRequestDate(curr);

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
        HoursWindowTiming strategy = new HoursWindowTiming(
                new TimeInDay(6, 0),
                new TimeInDay(1, 0),
                new EqualIntervalTiming(3000)
        );

        Date curr = composeDate(2016, 9, 26, 2, 0, 0);

        RequestDate next = strategy.nextRequestDate(curr);

        Calendar nextCal = fromDate(next.getDate());
        Assert.assertEquals(2016, nextCal.get(Calendar.YEAR));
        Assert.assertEquals(9, nextCal.get(Calendar.MONTH));
        Assert.assertEquals(26, nextCal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(6, nextCal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, nextCal.get(Calendar.MINUTE));
        Assert.assertEquals(0, nextCal.get(Calendar.SECOND));

    }
}
