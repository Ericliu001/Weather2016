package com.example.ericliu.weather2016.application;

import android.app.Application;

import com.example.ericliu.weather2016.dagger.component.DaggerRepoComponent;
import com.example.ericliu.weather2016.dagger.component.DaggerStubComponent;
import com.example.ericliu.weather2016.dagger.component.RepoComponent;
import com.example.ericliu.weather2016.dagger.component.StubComponent;
import com.example.ericliu.weather2016.dagger.module.AppModule;
import com.example.ericliu.weather2016.dagger.module.NetModule;
import com.example.ericliu.weather2016.dagger.module.RepoModule;
import com.example.ericliu.weather2016.dagger.module.StubModule;

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

        StubComponent stubComponent = DaggerStubComponent.builder()
                .stubModule(new StubModule())
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

        // TODO: 12/04/2016 change it to use real data when development is done
        component = repoComponent;
    }

    public static RepoComponent getComponent() {
        return component;
    }
}
