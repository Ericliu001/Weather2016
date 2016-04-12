package com.example.ericliu.weather2016.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;

import javax.inject.Inject;

/**
 * Created by ericliu on 12/04/2016.
 */
public class RetrieveWeatherService extends IntentService {
    private static final String SERVICE_NAME = "com.ericliu.retrieve.weather.service";
    private static final String TAG = RetrieveWeatherService.class.getSimpleName();


    @Inject
    RemoteWeatherRepo remoteWeatherRepo;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RetrieveWeatherService() {
        super(SERVICE_NAME);
        MyApplication.getComponent().inject(this);
    }

    public static void start(Context context, WeatherSpecification specification) {
        Intent intent = new Intent(context, RetrieveWeatherService.class);
        intent.putExtra(WeatherSpecification.ARG_WEATHER_SPECIFICATION, specification);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        WeatherSpecification specification = (WeatherSpecification) intent.getSerializableExtra(WeatherSpecification.ARG_WEATHER_SPECIFICATION);

        WeatherResult result = remoteWeatherRepo.get(specification);

        Log.d(TAG, result.toString());

    }
}
