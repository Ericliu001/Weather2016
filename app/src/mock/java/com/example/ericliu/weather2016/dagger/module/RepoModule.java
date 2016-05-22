package com.example.ericliu.weather2016.dagger.module;

import com.example.ericliu.weather2016.repo.DbWeatherRepo;
import com.example.ericliu.weather2016.repo.FakeDbWeatherRepo;
import com.example.ericliu.weather2016.repo.FakeRemoteWeatherRepo;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ericliu on 11/04/2016.
 */

@Module
public class RepoModule {

    @Provides
    @Singleton
    DbWeatherRepo provideDbWeatherRepository(){
        return new FakeDbWeatherRepo();
    }

    @Provides
    @Singleton
    RemoteWeatherRepo provideRemoteWeatherRepository(){
        return new FakeRemoteWeatherRepo();
    }

}
