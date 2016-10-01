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
    public void testEqualIntervalTiming(){
        EqualIntervalTiming timing = new EqualIntervalTiming(4000);
        Date currentDate = TimeTestHelper.composeDate(2016, 10, 1, 9, 57, 1);
        RequestDate requestDate = timing.getNextLocationRequestDate(currentDate);

        Assert.assertTrue(requestDate.isDatePresent());
        Date ansDate = requestDate.getDate();

        Calendar expectedCal = Calendar.getInstance();
        expectedCal.setTime(currentDate);
        expectedCal.add(Calendar.MILLISECOND, 4000);

        Calendar ansCal = Calendar.getInstance();
        ansCal.setTime(ansDate);

        Assert.assertEquals(expectedCal, ansCal);
    }


}
