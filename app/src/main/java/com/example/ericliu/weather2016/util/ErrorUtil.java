package com.example.ericliu.weather2016.util;

import com.example.ericliu.weather2016.framework.repository.RepositoryResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ericliu on 12/04/2016.
 */
public class ErrorUtil {

    public static void postException(Exception e) {
        WeatherSpecification specification = new WeatherSpecification();


        RepositoryResult repositoryResult = new RepositoryResult();
        repositoryResult.setSpecification(specification);
        repositoryResult.setThrowable(e);
        EventBus.getDefault().post(repositoryResult);
    }
}
