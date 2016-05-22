package com.example.ericliu.weather2016.ui.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.RequestResult;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.framework.mvp.base.BasePresenter;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

/**
 * Created by ericliu on 12/04/2016.
 */
public class MainActivityPresenter extends BasePresenter {

    protected HomepageCallbacks mDisplayView;


    public interface HomepageCallbacks extends DisplayView {
        void showProgressBar();

        void hideProgressBar();

        void showDialog(String message);

        void showCityName(String city);

        void showWeatherCondition(String weatherCondition);
    }


    public MainActivityPresenter(int presenterId, DisplayView displayView, ViewModel viewModel) {
        super(presenterId, displayView, viewModel);
        mDisplayView = (HomepageCallbacks) displayView;
    }


    @Override
    public void loadInitialData(Bundle args, boolean isConfigurationChange) {
        if (isConfigurationChange) {
            onUpdateComplete(mModel.getRequestResult(MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER), MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);
        } else {
            mDisplayView.showProgressBar();
            mModel.onStartModelUpdate(0, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER, args);
        }
    }


    @Override
    public void onUpdateComplete(RequestResult requestResult, ViewModel.QueryEnum query) {

        if (query instanceof MainActivityViewModel.QueryEnumMainActivity) {

            if (requestResult == null) {
                return;
            }
            MainActivityViewModel.WeatherRequestResult weatherRequestResult = (MainActivityViewModel.WeatherRequestResult) requestResult;

            if (MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER == query) {
                RequestStatus requestStatus = weatherRequestResult.getRequestStatus();
                if (requestStatus == RequestStatus.SUCESS) {
                    mDisplayView.hideProgressBar();

                    handleWeatherUpdate(weatherRequestResult);
                } else if (requestStatus == RequestStatus.FAILED) {

                    Throwable throwable = weatherRequestResult.getThrowable();
                    String errorMessage = throwable.getMessage();

                    mDisplayView.hideProgressBar();
                    mDisplayView.showDialog(errorMessage);

                } else {
                    handleWeatherUpdate(weatherRequestResult);
                }

            }

        } else {
            throw new IllegalArgumentException("request result not handled here");
        }


    }

    private void handleWeatherUpdate(MainActivityViewModel.WeatherRequestResult weatherRequestResult) {
        if (weatherRequestResult.getRequestStatus() == RequestStatus.LOADING) {

            mDisplayView.showProgressBar();

        } else {

            String city = weatherRequestResult.getCity();
            mDisplayView.showCityName(city);

            String weatherCondition = weatherRequestResult.getWeatherCondition();
            mDisplayView.showWeatherCondition(weatherCondition);
        }
    }


    public void onSearchButtonClicked(String city) {
        if (!TextUtils.isEmpty(city)) {
            WeatherSpecification specification = new WeatherSpecification();
            specification.setCityName(city);
            Bundle bundle = new Bundle();
            bundle.putSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION, specification);
            mModel.onStartModelUpdate(0, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER, bundle);

            mDisplayView.showProgressBar();
        }
    }


}
