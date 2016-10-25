package com.abrahamyans.gpsbusfeed.client.observer.event;

import android.content.Context;
import android.location.Location;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public class LocationChangedEvent extends ContextAwareEvent{
    private Date date;
    private Location location;

    public LocationChangedEvent(Context ctx, Location location, Date date) {
        super(ctx);
        this.date = date;
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "LocationChangedEvent{" +
                "date=" + date +
                ", location=" + location +
                '}';
    }
}
