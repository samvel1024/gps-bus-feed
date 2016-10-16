package com.abrahamyans.gpsbusfeed.persist;

import android.content.Context;

import org.apache.commons.lang.SerializationUtils;

import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public class SerializationManager {

    private static final SerializationManager instance = new SerializationManager();

    public static SerializationManager getInstance(){
        return instance;
    }

    public void serialize(Context ctx, Serializable obj){
        try {
            SerializationUtils.serialize(obj, ctx.openFileOutput(getFileName(obj.getClass()), Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found", e);
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T deserialize(Context ctx, Class<T> klass){
        try {
            return (T) SerializationUtils.deserialize(ctx.openFileInput(getFileName(klass)));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private String getFileName(Class<?> klass){
        return klass.getName().replaceAll("\\.", "_");
    }

}
