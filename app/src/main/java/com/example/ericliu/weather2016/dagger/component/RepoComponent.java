package com.example.ericliu.weather2016.dagger.component;

import com.example.ericliu.weather2016.dagger.module.AppModule;
import com.example.ericliu.weather2016.dagger.module.NetModule;
import com.example.ericliu.weather2016.dagger.module.RepoModule;
import com.example.ericliu.weather2016.service.RetrieveWeatherService;
import com.example.ericliu.weather2016.stub.StubRemoteWeatherRepo;
import com.example.ericliu.weather2016.ui.MainActivity;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ericliu on 11/04/2016.
 */

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, NetModule.class} )
public interface RepoComponent {
    void inject(MainActivity mainActivity);
    void inject(StubRemoteWeatherRepo repo);
    void inject(RetrieveWeatherService service);
    void inject(MainActivityViewModel mainActivityViewModel);
}
