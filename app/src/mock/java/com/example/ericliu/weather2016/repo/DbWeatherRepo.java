package com.example.ericliu.weather2016.repo;

import com.example.ericliu.weather2016.framework.repository.Repository;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.model.WeatherResult;

import java.util.List;

/**
 * Created by ericliu on 12/04/2016.
 */
public class DbWeatherRepo implements Repository<WeatherResult> {

    @Override
    public WeatherResult get(Specification specification) {
        return null;
    }

    @Override
    public void add(WeatherResult item) {

    }

    @Override
    public void add(Iterable<WeatherResult> items) {
    }

    @Override
    public void update(WeatherResult item) {
    }

    @Override
    public void remove(WeatherResult item) {
    }

    @Override
    public void remove(Specification specification) {
    }

    @Override
    public List<WeatherResult> query(Specification specification) {
        return null;
    }
}
