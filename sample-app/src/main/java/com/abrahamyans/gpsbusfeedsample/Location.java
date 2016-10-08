package com.abrahamyans.gpsbusfeedsample;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class Location {
    private Date date;
    private Double lat;
    private Double lon;

    public Location(Date date, Double lat, Double lon) {
        this.date = date;
        this.lat = lat;
        this.lon = lon;
    }

    public Date getDate() {
        return date;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "date=" + date +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
