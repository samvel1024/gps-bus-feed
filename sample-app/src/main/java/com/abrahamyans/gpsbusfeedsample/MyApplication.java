package com.abrahamyans.gpsbusfeedsample;

import com.abrahamyans.gpsbusfeed.LocationTracker;
import com.abrahamyans.gpsbusfeed.TrackerApplication;

/**
 * @author Samvel Abrahamyan
 */

public class MyApplication extends TrackerApplication {

    public LocationTracker getTracker(){
        return super.getGpsBusFeedComponent().getTracker();
    }

}
