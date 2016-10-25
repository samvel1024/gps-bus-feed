package com.abrahamyans.gpsbusfeed.client.tracker.time;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public interface RequestTiming extends Serializable{
    /**
     * @param lastRequestDate Previous location request date, null for the first time
     * @return The next location request date
     */
    RequestDate nextRequestDate(Date lastRequestDate);
}
