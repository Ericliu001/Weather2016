package com.example.ericliu.weather2016.model;

import com.example.ericliu.weather2016.framework.repository.Specification;

import java.io.Serializable;

/**
 * Created by ericliu on 12/04/2016.
 */
public class WeatherSpecification implements Specification, Serializable {
    public static final String ARG_WEATHER_SPECIFICATION = "com.ericliu.arg.weather.specification";

    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
