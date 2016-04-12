package com.example.ericliu.weather2016.dagger.component;


import com.example.ericliu.weather2016.dagger.module.RepoModule;
import com.example.ericliu.weather2016.dagger.module.StubModule;

/**
 * Created by ericliu on 11/04/2016.
 */
public enum AppComponents {
    INSTANCE;

    private RepoComponent repoComponent = DaggerRepoComponent.builder().repoModule(new RepoModule()).build();

    private StubComponent stubComponent = DaggerStubComponent.builder().stubModule(new StubModule()).build();

    public RepoComponent getComponent() {
        return stubComponent;
    }

}
