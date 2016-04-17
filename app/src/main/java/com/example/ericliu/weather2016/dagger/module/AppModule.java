package com.example.ericliu.weather2016.dagger.module;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ericliu on 12/04/2016.
 */
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    EventBus providesEventBus(){
        return EventBus.getDefault();
    }
}