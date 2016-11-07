package com.abrahamyans.gpsbusfeed.client;

import android.content.Context;

import org.apache.commons.lang.SerializationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public abstract class SerializableSingletonRepository<T extends Serializable> implements SingleInstanceRepository<T>{

    private static final String PARENT_FOLDER = "gps_bus_feed";
    private static final String EXTENSION = ".ser";
    private Class<? extends T> klass;
    private Context context;
    private String filePath;
    private String fileName;

    public SerializableSingletonRepository(Context context, Class<? extends T> klass){
        this.klass = klass;
        this.context = context;
        this.fileName = createFileName(klass);
        this.filePath = PARENT_FOLDER + "/" + this.fileName;
    }

    private String createFileName(Class<?> klass){
        return klass.getName().replaceAll("\\.", "_") + EXTENSION;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getInstance() {
        try {
            return (T) SerializationUtils.deserialize(new FileInputStream(getFile()));
        } catch (FileNotFoundException e) {
            throw new SingletonNotFoundException(klass, e);
        }
    }

    @Override
    public void save(T t) {
        try {
            SerializationUtils.serialize(t, new FileOutputStream(getFile()));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not serialize", e);
        }
    }

    private File getFile(){
        return new File(context.getDir(PARENT_FOLDER, Context.MODE_PRIVATE), fileName);
    }

    @Override
    public void delete() {
        if (!getFile().delete()){
            throw new IllegalStateException("Could not delete file");
        }
    }
}
