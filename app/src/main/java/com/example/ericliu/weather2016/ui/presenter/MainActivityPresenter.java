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
//            mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.SHOW_PROGRESS_BAR);

            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_PROGRESS_BAR, null);
            mModel.onInitialModelUpdate(0, args);
        }
    }


    @Override
    public void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query) {

        MainActivityViewModel mainActivityViewModel = (MainActivityViewModel) viewModel;

        if (MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER.getId() == query.getId()) {
            RequestStatus requestStatus = mainActivityViewModel.getRequestStatus();
            if (requestStatus == RequestStatus.SUCESS) {
//                mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.HIDE_PROGRESS_BAR);
                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, HIDE_PROGRESS_BAR, null);


                handleWeatherUpdate(mainActivityViewModel);
            } else if (requestStatus == RequestStatus.FAILED) {

                Throwable throwable = mainActivityViewModel.getThrowable();
                String errorMessage = throwable.getMessage();

//                mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.HIDE_PROGRESS_BAR);
//                mDisplayView.displayData(errorMessage, MainActivity.RefreshDisplayEnumMainActivity.SHOW_DIALOG);

                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, HIDE_PROGRESS_BAR, null);
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
//            mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.SHOW_PROGRESS_BAR);

            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_PROGRESS_BAR, null);


        } else {

            String city = viewModel.getCity();
//            mDisplayView.displayData(city, MainActivity.RefreshDisplayEnumMainActivity.SHOW_CITY_NAME);
            ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_CITY_NAME, city);

            String weatherCondition = viewModel.getWeatherCondition();
//            mDisplayView.displayData(weatherCondition, MainActivity.RefreshDisplayEnumMainActivity.SHOW_WEATHER_CONDITION);

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
//                mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.SHOW_PROGRESS_BAR);

                ViewUpdateDispatcher.INSTANCE.refreshDisplayElement(mDisplayView, SHOW_PROGRESS_BAR, null);
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
