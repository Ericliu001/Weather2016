package com.example.ericliu.weather2016.dagger.module;

import com.example.ericliu.weather2016.repo.DbWeatherRepo;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;
import com.example.ericliu.weather2016.mock.StubDbWeatherRepo;
import com.example.ericliu.weather2016.mock.StubRemoteWeatherRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ericliu on 11/04/2016.
 */

@Module
public class StubModule {


    @Provides
    @Singleton
    DbWeatherRepo provideDbWeatherRepository(){
        return new StubDbWeatherRepo();
    }

    @Provides
    @Singleton
    RemoteWeatherRepo provideRemoteWeatherRepository(){
        return new StubRemoteWeatherRepo();
    }


}
