package com.abrahamyans.gpsbusfeed.scheduler;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class RequestDate {

    public static RequestDate IMMEDIATELY = new RequestDate();
    public static RequestDate NEVER = new RequestDate();

    private Date date;

    public RequestDate(Date date){
        if (date == null)
            throw new IllegalArgumentException("date cannot be null");
        this.date = date;
    }

    private RequestDate(){}

    public boolean isDatePresent(){
        return date != null;
    }

    public Date getDate(){
        if (date == null)
            throw new IllegalStateException("cannot access null date");
        return date;
    }
}
