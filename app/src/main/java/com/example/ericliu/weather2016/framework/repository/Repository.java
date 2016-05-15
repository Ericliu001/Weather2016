package com.example.ericliu.weather2016.framework.repository;

import java.io.IOException;
import java.util.List;

/**
 * Created by ericliu on 12/04/2016.
 */

public interface Repository<T> {
    T get(Specification specification) throws IOException;

    void add(T item);

    void add(Iterable<T> items);

    void update(T item);

    void remove(T item);

    void remove(Specification specification);

    List<T> query(Specification specification);
}