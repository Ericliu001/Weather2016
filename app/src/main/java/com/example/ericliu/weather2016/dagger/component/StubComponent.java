package com.example.ericliu.weather2016.dagger.component;


import com.example.ericliu.weather2016.dagger.module.AppModule;
import com.example.ericliu.weather2016.dagger.module.StubModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ericliu on 11/04/2016.
 */

@Singleton
@Component(modules = {AppModule.class, StubModule.class})
public interface StubComponent extends RepoComponent {
}
