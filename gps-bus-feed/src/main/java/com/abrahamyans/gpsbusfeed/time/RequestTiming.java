package com.abrahamyans.gpsbusfeed.time;

import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public interface RequestTiming {
    /**
     * @param lastRequestDate Previous location request date, null for the first time
     * @return The next location request date
     */
    RequestDate getNextLocationRequestDate(Date lastRequestDate);
}
