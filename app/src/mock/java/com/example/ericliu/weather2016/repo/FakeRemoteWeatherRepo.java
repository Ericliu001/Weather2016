package com.example.ericliu.weather2016.repo;

import android.app.Application;
import android.util.Log;

import com.example.ericliu.weather2016.R;
import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.repository.RepositoryResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.model.JSONHandler;
import com.example.ericliu.weather2016.model.Weather;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.util.ThreadUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ericliu on 12/04/2016.
 */
public class FakeRemoteWeatherRepo extends RemoteWeatherRepo {

    private static final String TAG = FakeRemoteWeatherRepo.class.getSimpleName();
    @Inject
    Application mApplication;


    @Inject
    Gson mGson;


    public FakeRemoteWeatherRepo() {
        MyApplication.getComponent().inject(this);
    }


    @Override
    public WeatherResult get(Specification spec) throws IOException {


        String jsonStr = JSONHandler
                .parseResource(mApplication, R.raw.weather_by_city);

        WeatherResult result = mGson.fromJson(jsonStr, WeatherResult.class);
        ThreadUtil.sleepRandomLength();

        if (result != null) {


            WeatherSpecification specification = new WeatherSpecification();
            specification.setCityName("Sydney");
            WeatherResult weatherResult = new WeatherResult();
            weatherResult.name = "Beijing";
            Weather[] weather = new Weather[1];
            weather[0] = new Weather();
            weather[0].setDescription("Sunny");
            weatherResult.weather = weather;

            RepositoryResult<WeatherResult> repositoryResult = new RepositoryResult<>();
            repositoryResult.setSpecification(specification);
            repositoryResult.setThrowable(null);
            repositoryResult.setData(weatherResult);
            repositoryResult.setThrowable(null);

//            throw new IOException("Ha ha ha, Exception mocked!");

            return result;
        }

        Log.d(TAG, jsonStr);

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
