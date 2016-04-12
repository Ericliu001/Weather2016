package com.example.ericliu.weather2016.ui.viewmodel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.framework.repository.RepositoryResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.service.RetrieveWeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by ericliu on 12/04/2016.
 */
public class MainActivityViewModel extends Fragment implements ViewModel {


    private RequestStatus mRequestStatus = RequestStatus.NOT_STARTED;
    private String city;
    private String weatherCondition;

    @Inject
    EventBus eventBus;
    private Presenter mPresenter;


    public MainActivityViewModel() {
    }

    @Override
    public void onInitialModelUpdate(int presenterId, @Nullable Bundle args) {

        onStartModelUpdate(presenterId, QueryEnumMainActivity.UPDATE_WEATHER, args);
    }

    @Override
    public void onStartModelUpdate(int presenterId, QueryEnum update, @Nullable Bundle args) {
        mRequestStatus = RequestStatus.LOADING;

        if (QueryEnumMainActivity.UPDATE_WEATHER.getId() == update.getId()) {
            WeatherSpecification specification = (WeatherSpecification) args.getSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION);

            if (specification != null) {
                RetrieveWeatherService.start(getActivity(), specification);
            }

        } else {
            throw new IllegalArgumentException("query not handled here");
        }

    }

    @Override
    public RequestStatus getRequestStatus(QueryEnum update) {
        return null;
    }

    @Override
    public void setPresenter(int presenterId, Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Subscribe
    public void onEvent(RepositoryResult repositoryResult) {
        Specification specification = repositoryResult.getSpecification();

        if (specification instanceof WeatherSpecification) {
            handleWeatherResult((WeatherResult) repositoryResult.getData());
            if (repositoryResult.getError() == null) {
                mRequestStatus = RequestStatus.SUCESS;
            } else {
                mRequestStatus = RequestStatus.FAILED;
            }

            mPresenter.onUpdateComplete(this, QueryEnumMainActivity.UPDATE_WEATHER);
        }

    }

    private void handleWeatherResult(WeatherResult weatherResult) {
        if (weatherResult == null) {
            return;
        }
        city = weatherResult.name;
        if (weatherResult.weather != null && weatherResult.weather.length > 0) {
            weatherCondition = weatherResult.weather[0].getDescription();
        }

    }


    public String getCity() {
        return city;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }


    public RequestStatus getRequestStatus() {
        return mRequestStatus;
    }

    public enum QueryEnumMainActivity implements QueryEnum {
        UPDATE_WEATHER;


        @Override
        public int getId() {
            return this.ordinal();
        }
    }

}
