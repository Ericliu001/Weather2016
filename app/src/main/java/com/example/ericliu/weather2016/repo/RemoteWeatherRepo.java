package com.example.ericliu.weather2016.repo;

import android.app.Application;

import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.repository.Repository;
import com.example.ericliu.weather2016.framework.repository.RepositoryResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ericliu on 12/04/2016.
 */
public class RemoteWeatherRepo implements Repository<WeatherResult> {
    private static final String TAG = RemoteWeatherRepo.class.getSimpleName();
    @Inject
    Application mApplication;

    @Inject
    DbWeatherRepo mDBWeatherRepo;

    @Inject
    Gson mGson;

    @Inject
    EventBus eventBus;

    @Inject
    OkHttpClient mOkHttpClient;


    public RemoteWeatherRepo() {
        MyApplication.getComponent().inject(this);
    }


    @Override
    public WeatherResult get(Specification specification) {
        WeatherSpecification weatherSpecification = (WeatherSpecification) specification;
        String cityName = weatherSpecification.getCityName();

        try {
            StringBuilder stringBuilder = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?q=");
            stringBuilder.append(cityName)
                    .append("&appid=a1cf06c52e1cc8cd586382594c71ea0a");

//            URL url = new URL(stringBuilder.toString());


            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/weather").newBuilder();
            urlBuilder.addQueryParameter("q", cityName);
            urlBuilder.addQueryParameter("appid", "a1cf06c52e1cc8cd586382594c71ea0a");
            String url = urlBuilder.build().toString();


            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            response = mOkHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {

                WeatherResult weatherResult
                        = mGson.fromJson(response.body().string(), WeatherResult.class);

                // notify the world
                RepositoryResult<WeatherResult> repositoryResult = new RepositoryResult<>();
                repositoryResult.setSpecification(specification);
                repositoryResult.setData(weatherResult);
                repositoryResult.setThrowable(null);
                eventBus.post(repositoryResult);

                // sync with local db
                mDBWeatherRepo.add(weatherResult);
                return weatherResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
