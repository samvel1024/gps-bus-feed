package com.abrahamyans.gpsbusfeed.client.observer;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Samvel Abrahamyan
 */
@Module
public class ObserverModule {

    private Context context;

    public ObserverModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ObserverRepository provideRepository() {
        return new ObserverRepository(context);
    }

    @Provides
    @Singleton
    public SerializableBus provideEventBus(ObserverRepository repository) {
        SerializableBus deserialized = repository.getInstance();
        if (deserialized != null) {
            return deserialized;
        }
        return new SerializableBus();
    }

}
