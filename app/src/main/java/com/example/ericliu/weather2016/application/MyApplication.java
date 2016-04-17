package com.example.ericliu.weather2016.application;

import android.app.Application;

import com.example.ericliu.weather2016.dagger.component.DaggerRepoComponent;
import com.example.ericliu.weather2016.dagger.component.RepoComponent;
import com.example.ericliu.weather2016.dagger.module.AppModule;
import com.example.ericliu.weather2016.dagger.module.NetModule;
import com.example.ericliu.weather2016.dagger.module.RepoModule;

/**
 * Created by ericliu on 12/04/2016.
 */
public class MyApplication extends Application {

    private static RepoComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        RepoComponent repoComponent = DaggerRepoComponent.builder()
                .repoModule(new RepoModule())
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

        component = repoComponent;
    }

    public static  RepoComponent getComponent() {
        return component;
    }


}
