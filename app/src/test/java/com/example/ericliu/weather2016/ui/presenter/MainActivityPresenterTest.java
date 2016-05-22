package com.example.ericliu.weather2016.ui.presenter;

import android.content.SharedPreferences;

import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ericliu on 22/05/2016.
 */
public class MainActivityPresenterTest {

    private static final String PREF_IS_HAPPY = "pref.is.happy";
    @Mock
    private MainActivityPresenter.HomepageCallbacks mHomepageCallbacks;


    @Mock
    SharedPreferences mSharedPreferences;

    @Mock
    private MainActivityViewModel mViewModel;


    @Mock
    private MainActivityViewModel.WeatherRequestResult mWeatherRequestResult;

    private MainActivityPresenter mPresenter;
    private String city = "Adelaide";


    @Before
    public void setUp() throws Exception {

        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mPresenter = new MainActivityPresenter(0, mHomepageCallbacks, mViewModel);

    }

    @After
    public void tearDown() throws Exception {
        mPresenter = null;

    }

    @Test
    public void testOnUpdateComplete() throws Exception {
        when(mWeatherRequestResult.getRequestStatus()).thenReturn(RequestStatus.SUCESS);
        when(mWeatherRequestResult.getCity()).thenReturn(city);

        mPresenter.onUpdateComplete(mWeatherRequestResult, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);

        verify(mHomepageCallbacks).showCityName(city);

    }


    @Test
    public void testFailCase(){
        when(mWeatherRequestResult.getRequestStatus()).thenReturn(RequestStatus.FAILED);
        when(mWeatherRequestResult.getThrowable()).thenReturn(new Exception("This is a mocked exception!"));

        mPresenter.onUpdateComplete(mWeatherRequestResult, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);

        verify(mHomepageCallbacks).hideProgressBar();
        verify(mHomepageCallbacks).showDialog(anyString());
    }



    @Test
    public void testPreferences(){
        when(mSharedPreferences.getBoolean(PREF_IS_HAPPY, false)).thenReturn(true);

        boolean isHappy = mSharedPreferences.getBoolean(PREF_IS_HAPPY, false);
        assertTrue("The shared preference value should be false.", isHappy);
    }

    @Test
    public void testOnSearchButtonClicked() throws Exception {

        mPresenter.onSearchButtonClicked("Sydney");

        verify(mHomepageCallbacks).showProgressBar();
    }
}