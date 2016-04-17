package com.example.ericliu.weather2016.dagger.component;


import com.example.ericliu.weather2016.dagger.module.AppModule;
import com.example.ericliu.weather2016.dagger.module.NetModule;
import com.example.ericliu.weather2016.dagger.module.RepoModule;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ericliu on 11/04/2016.
 */

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, NetModule.class})
public interface StubComponent extends RepoComponent{
    void inject(RemoteWeatherRepo repo);
}
