package com.abrahamyans.gpsbusfeed.time;

import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public interface RequestTiming {
    RequestDate getNextLocationRequestDate(Date lastRequestDate);
}
