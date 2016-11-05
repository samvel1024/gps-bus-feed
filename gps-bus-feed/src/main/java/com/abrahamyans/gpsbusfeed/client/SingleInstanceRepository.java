package com.abrahamyans.gpsbusfeed.client;

/**
 * @author Samvel Abrahamyan
 */

public interface SingleInstanceRepository<T> {
    T getInstance();

    void save(T t);

    void delete();
}
