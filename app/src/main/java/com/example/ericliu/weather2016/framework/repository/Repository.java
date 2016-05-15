package com.example.ericliu.weather2016.framework.repository;

import java.util.List;

/**
 * Created by ericliu on 12/04/2016.
 */

public interface Repository<T> {
    T get(Specification specification) throws Exception;

    void add(T item) throws Exception;

    void add(Iterable<T> items) throws Exception;

    void update(T item) throws Exception;

    void remove(T item) throws Exception;

    void remove(Specification specification) throws Exception;

    List<T> query(Specification specification) throws Exception;
}