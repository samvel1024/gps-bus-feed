package com.abrahamyans.gpsbusfeed.filter;

import com.abrahamyans.gpsbusfeed.event.LocationChangedEvent;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationEventFilter extends Serializable{
    boolean shouldBroadcastLocation(LocationChangedEvent location);
}
