package com.abrahamyans.gpsbusfeed.time;

import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class SingleRequestTiming implements RequestTiming {

    SingleRequestTiming(){
        super();
    }

    @Override
    public RequestDate getNextLocationRequestDate(Date lastRequestDate) {
        return lastRequestDate == null ? RequestDate.IMMEDIATELY : RequestDate.NEVER;
    }

    public static RequestTiming create(){
        return new SingleRequestTiming();
    }
}
