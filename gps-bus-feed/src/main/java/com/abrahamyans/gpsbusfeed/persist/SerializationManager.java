package com.abrahamyans.gpsbusfeed.persist;

import org.apache.commons.lang.SerializationUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public class SerializationManager {

    private static final SerializationManager instance = new SerializationManager();

    public static SerializationManager getInstance(){
        return instance;
    }

    public void serialize(Serializable obj){
        try {
            SerializationUtils.serialize(obj, new FileOutputStream(getFileName(obj.getClass())));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found", e);
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T deserialize(Class<T> klass){
        try {
            return (T) SerializationUtils.deserialize(new FileInputStream(getFileName(klass)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found", e);
        }
    }

    private String getFileName(Class<?> klass){
        return klass.getName().replaceAll("\\.", "_");
    }

}
