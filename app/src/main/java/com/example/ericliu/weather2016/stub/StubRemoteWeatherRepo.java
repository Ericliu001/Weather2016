package com.example.ericliu.weather2016.stub;

import android.app.Application;
import android.util.Log;

import com.example.ericliu.weather2016.R;
import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.entity.JSONHandler;
import com.example.ericliu.weather2016.entity.WeatherResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ericliu on 12/04/2016.
 */
public class StubRemoteWeatherRepo extends RemoteWeatherRepo {

    private static final String TAG = StubRemoteWeatherRepo.class.getSimpleName();
    @Inject
    Application mApplication;


    public StubRemoteWeatherRepo() {
        MyApplication.getComponent().inject(this);
    }


    @Override
    public WeatherResult get(Specification specification) {

        String data;

        // Load stub data from raw resource.
        try {
            String bootstrapJson = JSONHandler
                    .parseResource(mApplication, R.raw.weather_by_city);

            Log.d(TAG, bootstrapJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
