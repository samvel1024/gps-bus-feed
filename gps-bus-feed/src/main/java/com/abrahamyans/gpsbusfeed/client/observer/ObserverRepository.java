package com.abrahamyans.gpsbusfeed.client.observer;

import android.content.Context;

import com.abrahamyans.gpsbusfeed.client.SingletonNotFoundException;
import com.abrahamyans.gpsbusfeed.client.SerializableSingletonRepository;

/**
 * @author Samvel Abrahamyan
 */

public class ObserverRepository extends SerializableSingletonRepository<SerializableBus> {

    public ObserverRepository(Context context) {
        super(context, SerializableBus.class);
    }

    @Override
    public SerializableBus getInstance() {
        try{
            return super.getInstance();
        }catch (SingletonNotFoundException e){
            return null;
        }
    }

}
