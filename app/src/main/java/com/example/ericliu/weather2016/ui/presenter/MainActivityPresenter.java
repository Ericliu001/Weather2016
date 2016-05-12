package com.example.ericliu.weather2016.ui.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.framework.mvp.base.BasePresenter;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

/**
 * Created by ericliu on 12/04/2016.
 */
public class MainActivityPresenter extends BasePresenter {
    public static final String ARG_CITY_NAME = "arg.city.name";
    public static final int
            SHOW_PROGRESS_BAR = 2, HIDE_PROGRESS_BAR = 3, SHOW_WEATHER_CONDITION = 4, SHOW_CITY_NAME = 5, SHOW_DIALOG = 6;

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
            onUpdateComplete(mModel, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);
        } else {
            mDisplayView.showProgressBar();
            mModel.onInitialModelUpdate(0, args);
        }
    }


    @Override
    public void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query) {

        if (query instanceof MainActivityViewModel.QueryEnumMainActivity) {
            MainActivityViewModel.QueryEnumMainActivity queryEnum = (MainActivityViewModel.QueryEnumMainActivity) query;
            MainActivityViewModel mainActivityViewModel = (MainActivityViewModel) viewModel;

            if (MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER == query) {
                RequestStatus requestStatus = mainActivityViewModel.getRequestStatus();
                if (requestStatus == RequestStatus.SUCESS) {
                    mDisplayView.hideProgressBar();

                    handleWeatherUpdate(mainActivityViewModel);
                } else if (requestStatus == RequestStatus.FAILED) {

                    Throwable throwable = mainActivityViewModel.getThrowable();
                    String errorMessage = throwable.getMessage();

                    mDisplayView.hideProgressBar();
                    mDisplayView.showDialog(errorMessage);

                } else {
                    handleWeatherUpdate(mainActivityViewModel);
                }

            }

        } else {
            throw new IllegalArgumentException("request result not handled here");
        }


    }

    private void handleWeatherUpdate(MainActivityViewModel viewModel) {
        if (viewModel.getRequestStatus() == RequestStatus.LOADING) {

            mDisplayView.showProgressBar();

        } else {

            String city = viewModel.getCity();
            mDisplayView.showCityName(city);

            String weatherCondition = viewModel.getWeatherCondition();
            mDisplayView.showWeatherCondition(weatherCondition);
        }
    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {
        if (action instanceof UserActionEnumMainActivity) {
            UserActionEnumMainActivity userAction = (UserActionEnumMainActivity) action;

            if (UserActionEnumMainActivity.BUTTON_CLICKED == userAction) {
                String city = args.getString(ARG_CITY_NAME);
                if (!TextUtils.isEmpty(city)) {
                    WeatherSpecification specification = new WeatherSpecification();
                    specification.setCityName(city);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION, specification);
                    mModel.onStartModelUpdate(0, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER, bundle);

                    mDisplayView.showProgressBar();
                }
            }


        } else {
            throw new IllegalArgumentException("This action " + action + " is not handled here.");
        }


    }


    public enum UserActionEnumMainActivity implements UserActionEnum {
        BUTTON_CLICKED;
    }


}
