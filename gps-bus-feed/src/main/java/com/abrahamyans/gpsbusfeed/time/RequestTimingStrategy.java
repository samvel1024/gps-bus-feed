package com.abrahamyans.gpsbusfeed.time;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public interface RequestTimingStrategy {
    Date getNextLocationRequestDate(Date lastUpdateDate);
}
