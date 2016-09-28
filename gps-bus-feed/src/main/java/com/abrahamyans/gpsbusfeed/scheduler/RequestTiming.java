package com.abrahamyans.gpsbusfeed.scheduler;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public interface RequestTiming {
    RequestDate getNextLocationRequestDate(Date lastRequestDate);
}
