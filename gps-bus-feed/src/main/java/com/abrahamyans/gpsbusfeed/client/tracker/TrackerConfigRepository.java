package com.abrahamyans.gpsbusfeed.client.tracker;

import android.content.Context;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.SingleInstanceRepository;

import org.apache.commons.lang.SerializationUtils;

import java.io.FileNotFoundException;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerConfigRepository implements SingleInstanceRepository<TrackerConfig> {

    private static final String TAG = "TrackerConfigRepository";

    private static final String SERIALIZED_FILE_NAME = "tracker_config.ser";

    private Context context;

    public TrackerConfigRepository(Context context) {
        this.context = context;
    }

    @Override
    public TrackerConfig getSerializedInstance() {
        try {
            TrackerConfig config = (TrackerConfig) SerializationUtils.deserialize(context.openFileInput(SERIALIZED_FILE_NAME));
            Log.d(TAG, "Found serialized state");
            return config;
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Serialized state not found, returning null object");
            return null;
        }
    }

    @Override
    public void save(TrackerConfig trackerConfig) {
        try {
            SerializationUtils.serialize(trackerConfig, context.openFileOutput(SERIALIZED_FILE_NAME, Context.MODE_PRIVATE));
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
