package com.abrahamyans.gpsbusfeed.client.tracker.time;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class RequestDate {

    public static final RequestDate IMMEDIATELY = new RequestDate();
    public static final RequestDate NEVER = new RequestDate();

    private Date date;

    public RequestDate(Date date) {
        if (date == null)
            throw new IllegalArgumentException("date cannot be null");
        this.date = date;
    }

    private RequestDate() {
    }

    public boolean isDatePresent() {
        return date != null;
    }

    public Date getDate() {
        if (date == null)
            throw new IllegalStateException("Cannot access null date");
        return date;
    }
}
