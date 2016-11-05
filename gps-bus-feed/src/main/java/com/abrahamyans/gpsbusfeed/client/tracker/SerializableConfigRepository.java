package com.abrahamyans.gpsbusfeed.client.tracker;

import android.content.Context;

import com.abrahamyans.gpsbusfeed.client.SerializableSingletonRepository;
import com.abrahamyans.gpsbusfeed.client.SingletonNotFoundException;

/**
 * @author Samvel Abrahamyan
 */

public class SerializableConfigRepository extends SerializableSingletonRepository<TrackerConfig> {

    private static final String TAG = "SerializableConfigRepository";

    public SerializableConfigRepository(Context context) {
        super(context, TrackerConfig.class);
    }

    @Override
    public TrackerConfig getInstance() {
        try{
            return super.getInstance();
        }catch (SingletonNotFoundException e){
            return null;
        }
    }
}
