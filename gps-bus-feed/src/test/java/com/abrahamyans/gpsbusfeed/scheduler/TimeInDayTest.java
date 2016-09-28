package com.abrahamyans.gpsbusfeed.scheduler;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Samvel Abrahamyan
 */

public class TimeInDayTest {

    @Test(expected = IllegalArgumentException.class)
    public void testValidation(){
        new TimeInDay(10, 20, 60);
    }


    @Test
    public void testComparison(){
        TimeInDay sooner = new TimeInDay(9, 10, 40);
        TimeInDay later = new TimeInDay(19, 29, 30);

        int differenceMillis = (19 - 9)*3600*1000 + (29-10)*60*1000 + (30-40)*1000;

        Assert.assertEquals(-differenceMillis, sooner.compareTo(later));
        Assert.assertEquals(differenceMillis, later.compareTo(sooner));
    }
}
