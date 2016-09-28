package com.abrahamyans.gpsbusfeed;

import android.location.Location;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public class LocationAvailableEvent {
    private Date date;
    private Location location;

    public LocationAvailableEvent(Location location, Date date) {
        this.date = date;
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }
}
