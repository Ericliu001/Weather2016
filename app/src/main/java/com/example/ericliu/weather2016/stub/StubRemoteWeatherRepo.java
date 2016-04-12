package com.example.ericliu.weather2016.stub;

import android.app.Application;
import android.util.Log;

import com.example.ericliu.weather2016.R;
import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.repository.RepositoryResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.model.JSONHandler;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.repo.DbWeatherRepo;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;
import com.example.ericliu.weather2016.util.ThreadUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

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

    @Inject
    DbWeatherRepo mDBWeatherRepo;

    @Inject
    Gson mGson;

    @Inject
    EventBus eventBus;


    public StubRemoteWeatherRepo() {
        MyApplication.getComponent().inject(this);
    }


    @Override
    public WeatherResult get(Specification specification) {


        // Load stub data from raw resource.
        try {
            String jsonStr = JSONHandler
                    .parseResource(mApplication, R.raw.weather_by_city);

            WeatherResult result = mGson.fromJson(jsonStr, WeatherResult.class);
            ThreadUtil.sleepRandomLength();

            if (result != null) {


                // broadcast the result
                RepositoryResult<WeatherResult> repositoryResult = new RepositoryResult<>();
                repositoryResult.setData(result);
                repositoryResult.setSpecification(specification);
                repositoryResult.setError(null);
                eventBus.post(repositoryResult);

                // sync with local DB
                mDBWeatherRepo.add(result);
                return result;
            }

            Log.d(TAG, jsonStr);
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
