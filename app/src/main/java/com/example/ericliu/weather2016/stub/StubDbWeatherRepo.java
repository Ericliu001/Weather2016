package com.example.ericliu.weather2016.stub;

import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.repo.DbWeatherRepo;

import java.util.List;

/**
 * Created by ericliu on 12/04/2016.
 */
public class StubDbWeatherRepo extends DbWeatherRepo {

    @Override
    public WeatherResult get(Specification specification) {
        return super.get(specification);
    }

    @Override
    public void add(WeatherResult item) {
        super.add(item);
    }

    @Override
    public void add(Iterable<WeatherResult> items) {
        super.add(items);
    }

    @Override
    public void update(WeatherResult item) {
        super.update(item);
    }

    @Override
    public void remove(WeatherResult item) {
        super.remove(item);
    }

    @Override
    public void remove(Specification specification) {
        super.remove(specification);
    }

    @Override
    public List<WeatherResult> query(Specification specification) {
        return super.query(specification);
    }
}
