package com.example.ericliu.weather2016.application;

import com.example.ericliu.weather2016.dagger.component.DaggerStubComponent;
import com.example.ericliu.weather2016.dagger.component.StubComponent;
import com.example.ericliu.weather2016.dagger.module.AppModule;
import com.example.ericliu.weather2016.dagger.module.NetModule;
import com.example.ericliu.weather2016.dagger.module.RepoModule;

/**
 * Created by ericliu on 12/04/2016.
 */
public class MyApplication extends BaseApplication {


    private static StubComponent component;

    @Override
    public void onCreate() {
        super.onCreate();



        StubComponent stubComponent = DaggerStubComponent.builder()
                .repoModule(new RepoModule())
                .appModule(new AppModule(MyApplication.this))
                .netModule(new NetModule())
                .build();

        // TODO: 12/04/2016 change it to use real data when development is done
        component = stubComponent;
    }

    public static  StubComponent getComponent() {
        return component;
    }
}
