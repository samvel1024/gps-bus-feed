package com.abrahamyans.gpsbusfeed.time;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class EqualIntervalRequestStrategyTest {

    @Test
    public void testNextIntervalBetweenFromAndTo() {
        EqualIntervalRequestStrategy strategy = new EqualIntervalRequestStrategy(
                new TimeInDay(10, 0),
                new TimeInDay(21, 0),
                3000
        );

        Calendar cal = Calendar.getInstance();
        cal.set(2016, 9, 26, 16, 0, 0);
        Date curr = cal.getTime();

        Date next = strategy.getNextLocationRequestDate(curr);
        cal.add(Calendar.MILLISECOND, 3000);

        Assert.assertEquals(next, cal.getTime());
    }
}
