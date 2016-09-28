package com.abrahamyans.gpsbusfeed.scheduler;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class SingleRequestTiming implements RequestTiming {

    @Override
    public RequestDate getNextLocationRequestDate(Date lastUpdateDate) {
        return lastUpdateDate == null ? RequestDate.IMMEDIATELY : RequestDate.NEVER;
    }

}
