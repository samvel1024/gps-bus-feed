package com.abrahamyans.gpsbusfeed.client.observer;

import android.content.Context;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.SingleInstanceRepository;

import org.apache.commons.lang.SerializationUtils;

import java.io.FileNotFoundException;

/**
 * @author Samvel Abrahamyan
 */

public class ObserverRepository implements SingleInstanceRepository<SerializableBus> {

    private static final String TAG = "ObserverRepository";

    private static final String SERIALIZED_FILE_NAME = "observer_repository.ser";

    private Context context;

    public ObserverRepository(Context context) {
        this.context = context;
    }

    @Override
    public SerializableBus getSerializedInstance() {
        try {
            SerializableBus bus = (SerializableBus) SerializationUtils.deserialize(context.openFileInput(SERIALIZED_FILE_NAME));
            Log.d(TAG, "Found serialized state");
            return bus;
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Serialized state not found, returning null object");
            return null;
        }
    }

    @Override
    public void save(SerializableBus serializableBus) {
        try {
            SerializationUtils.serialize(serializableBus, context.openFileOutput(SERIALIZED_FILE_NAME, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not open file " + SERIALIZED_FILE_NAME, e);
        }
    }

    @Override
    public void delete() {
        boolean success = context.deleteFile(SERIALIZED_FILE_NAME);
        if (!success) {
            throw new IllegalStateException("Could not delete file " + SERIALIZED_FILE_NAME);
        }
    }
}
