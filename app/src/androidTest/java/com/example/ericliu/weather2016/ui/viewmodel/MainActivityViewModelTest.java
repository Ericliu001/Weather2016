package com.example.ericliu.weather2016.ui.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;

import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.framework.repository.RepositoryResult;
import com.example.ericliu.weather2016.framework.repository.Specification;
import com.example.ericliu.weather2016.model.Weather;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by ericliu on 12/04/2016.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityViewModelTest implements Presenter{


    MainActivityViewModel mModel;
    private Bundle args;
    private Specification mSpecification;

    @Before
    public void setUp() throws Exception {
        mModel = new MainActivityViewModel();
        mModel.setPresenter(0, this);

        args = new Bundle();
        // Data can be passed to the service via the Intent.
        WeatherSpecification specification = new WeatherSpecification();
        specification.setCityName("Sydney");
        mSpecification = specification;

        args.putSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION, specification);
    }

    @After
    public void tearDown() throws Exception {
        mModel = null;
    }




    @Test
    public void testOnEvent() throws Exception {
        WeatherResult weatherResult = new WeatherResult();
        weatherResult.name = "Beijing";
        Weather[] weather = new Weather[1];
        weather[0] = new Weather();
        weather[0].setDescription("Sunny");
        weatherResult.weather = weather;

        RepositoryResult<WeatherResult> repositoryResult = new RepositoryResult<>();
        repositoryResult.setSpecification(mSpecification);
        repositoryResult.setError(null);
        repositoryResult.setData(weatherResult);

        mModel.onEvent(repositoryResult);
    }


    @Override
    public void setViewModel(ViewModel viewModel) {

    }

    @Override
    public void setDisplayView(DisplayView view) {

    }

    @Override
    public void loadInitialData(Bundle args, boolean isConfigurationChange) {

    }

    @Override
    public void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query) {
        MainActivityViewModel model = (MainActivityViewModel) viewModel;

        assertEquals(model.getCity(), "Beijing");

    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {

    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onViewDestroyed() {

    }
}