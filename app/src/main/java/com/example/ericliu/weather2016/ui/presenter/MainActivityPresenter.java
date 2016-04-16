package com.example.ericliu.weather2016.ui.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.framework.mvp.ViewUpdateDispatcher;
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


    public MainActivityPresenter(int presenterId, DisplayView displayView, ViewModel viewModel) {
        super(presenterId, displayView, viewModel);
    }


    @Override
    public void loadInitialData(Bundle args, boolean isConfigurationChange) {
        if (isConfigurationChange) {
            onUpdateComplete(mModel, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);
        } else {
            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_PROGRESS_BAR);
            mModel.onInitialModelUpdate(0, args);
        }
    }


    @Override
    public void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query) {

        MainActivityViewModel mainActivityViewModel = (MainActivityViewModel) viewModel;

        if (MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER.getId() == query.getId()) {
            RequestStatus requestStatus = mainActivityViewModel.getRequestStatus();
            if (requestStatus == RequestStatus.SUCESS) {
                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, HIDE_PROGRESS_BAR);


                handleWeatherUpdate(mainActivityViewModel);
            } else if (requestStatus == RequestStatus.FAILED) {

                Throwable throwable = mainActivityViewModel.getThrowable();
                String errorMessage = throwable.getMessage();

                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, HIDE_PROGRESS_BAR);
                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_DIALOG, errorMessage);


            } else {
                handleWeatherUpdate(mainActivityViewModel);
            }

        } else {
            throw new IllegalArgumentException("request result not handled here");
        }

    }

    private void handleWeatherUpdate(MainActivityViewModel viewModel) {
        if (viewModel.getRequestStatus() == RequestStatus.LOADING) {

            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_PROGRESS_BAR);


        } else {

            String city = viewModel.getCity();
            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_CITY_NAME, city);

            String weatherCondition = viewModel.getWeatherCondition();

            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_WEATHER_CONDITION, weatherCondition);

        }
    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {
        if (UserActionEnumMainActivity.BUTTON_CLICKED.getId() == action.getId()) {
            String city = args.getString(ARG_CITY_NAME);
            if (!TextUtils.isEmpty(city)) {
                WeatherSpecification specification = new WeatherSpecification();
                specification.setCityName(city);
                Bundle bundle = new Bundle();
                bundle.putSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION, specification);
                mModel.onStartModelUpdate(0, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER, bundle);

                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_PROGRESS_BAR);
            }
        }

    }


    public enum UserActionEnumMainActivity implements UserActionEnum {
        BUTTON_CLICKED;

        @Override
        public int getId() {
            return this.ordinal();
        }
    }


}
