package com.abrahamyans.gpsbusfeed.event;

import android.content.Context;

/**
 * @author Samvel Abrahamyan
 */
public class ContextAwareEvent {

    private Context context;

    public ContextAwareEvent(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
