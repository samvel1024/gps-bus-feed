package com.abrahamyans.gpsbusfeed.client.tracker;

import android.content.Context;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.SingleInstanceRepository;

import org.apache.commons.lang.SerializationUtils;

import java.io.FileNotFoundException;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerRepository implements SingleInstanceRepository<LocationTracker> {

    private static final String TAG = "TrackerRepository";

    private static final String SERIALIZED_FILE_NAME = "location_tracker.ser";

    private Context context;

    public TrackerRepository(Context context){
        this.context = context;
    }

    @Override
    public LocationTracker getSerializedInstance() {
        try {
            return (LocationTracker) SerializationUtils.deserialize(context.openFileInput(SERIALIZED_FILE_NAME));
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Serialized state not found, returning null object" + e );
            return LocationTracker.NO_TRACKER;
        }
    }

    @Override
    public void save(LocationTracker locationTracker) {
        try {
            SerializationUtils.serialize(locationTracker, context.openFileOutput(SERIALIZED_FILE_NAME, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not open file " + SERIALIZED_FILE_NAME, e);
        }
    }

    @Override
    public void delete() {
        boolean success = context.deleteFile(SERIALIZED_FILE_NAME);
        if (!success){
            throw new IllegalStateException("Could not delete file " + SERIALIZED_FILE_NAME);
        }
    }

}
