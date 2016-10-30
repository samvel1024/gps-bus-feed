package com.abrahamyans.gpsbusfeed.client;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public interface SingleInstanceRepository<T extends Serializable> {
    T getSerializedInstance();
    void save(T t);
    void delete();
}
