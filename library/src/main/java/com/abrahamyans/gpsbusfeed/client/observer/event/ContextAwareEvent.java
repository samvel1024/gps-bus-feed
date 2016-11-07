package com.abrahamyans.gpsbusfeed.client.observer.event;

import android.content.Context;

/**
 * @author Samvel Abrahamyan
 */
public abstract class ContextAwareEvent {

    private Context context;

    public ContextAwareEvent(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
