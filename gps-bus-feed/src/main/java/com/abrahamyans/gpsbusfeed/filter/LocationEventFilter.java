package com.abrahamyans.gpsbusfeed.filter;

import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationEventFilter {
    boolean shouldBroadcastLocation(LocationAvailableEvent location);
}
