package com.abrahamyans.gpsbusfeed.client;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */
public class SingletonNotFoundException extends RuntimeException {

    private Class<? extends Serializable> klass;

    public SingletonNotFoundException(Class<? extends Serializable> klass, Throwable cause){
        super("Could not find serialized singleton state for " + klass.getName(), cause);
    }

    public Class<? extends Serializable> getNotFoundClass() {
        return klass;
    }
}
