package com.example.ericliu.weather2016.dagger.component;

import com.example.ericliu.weather2016.MainActivity;
import com.example.ericliu.weather2016.dagger.module.RepoModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ericliu on 11/04/2016.
 */

@Singleton
@Component(modules = RepoModule.class )
public interface RepoComponent {
    void inject(MainActivity mainActivity);
}
