package com.example.ericliu.weather2016.ui.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.framework.mvp.base.BasePresenter;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.ui.MainActivity;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

/**
 * Created by ericliu on 12/04/2016.
 */
public class MainActivityPresenter extends BasePresenter {
    public MainActivityPresenter(int presenterId, DisplayView displayView, ViewModel viewModel) {
        super(presenterId, displayView, viewModel);
    }

    @Override
    public void loadInitialData(Bundle args, boolean isConfigurationChange) {
        if (isConfigurationChange) {
            onUpdateComplete(mModel, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);
        } else {
            mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.SHOW_PROGRESS_BAR);
            mModel.onInitialModelUpdate(0, args);
        }
    }

    @Override
    public void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query) {


        if (MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER.getId() == query.getId()) {
            MainActivityViewModel mainActivityViewModel = (MainActivityViewModel) viewModel;
            RequestStatus requestStatus = mainActivityViewModel.getRequestStatus();
            if (requestStatus == RequestStatus.SUCESS || requestStatus == RequestStatus.FAILED) {
                mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.HIDE_PROGRESS_BAR);

                handleWeatherUpdate(mainActivityViewModel);
            }

        } else {
            throw new IllegalArgumentException("request result not handled here");
        }

    }

    private void handleWeatherUpdate(MainActivityViewModel viewModel) {
        if (viewModel.getRequestStatus() == RequestStatus.LOADING) {
            mDisplayView.displayData(null, MainActivity.RefreshDisplayEnumMainActivity.SHOW_PROGRESS_BAR);
        } else {

        }
    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {
        if (UserActionEnumMainActivity.BUTTON_CLICKED.getId() == action.getId()) {
            WeatherSpecification specification = (WeatherSpecification) args.getSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION);
            if (specification != null) {
                mModel.onStartModelUpdate(0, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER, args);
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
