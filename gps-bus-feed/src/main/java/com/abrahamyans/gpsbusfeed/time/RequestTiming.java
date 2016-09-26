package com.abrahamyans.gpsbusfeed.time;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public interface RequestTiming {
    Date getNextLocationRequestDate(Date lastUpdateDate);
}
