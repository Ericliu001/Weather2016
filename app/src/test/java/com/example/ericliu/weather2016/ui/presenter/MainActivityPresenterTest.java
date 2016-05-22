package com.example.ericliu.weather2016.ui.presenter;

import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ericliu on 22/05/2016.
 */
public class MainActivityPresenterTest {

    @Mock
    private MainActivityPresenter.HomepageCallbacks mHomepageCallbacks;


    @Mock
    private MainActivityViewModel mViewModel;

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
        when(mViewModel.getRequestStatus(MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER)).thenReturn(RequestStatus.SUCESS);
        when(mViewModel.getCity()).thenReturn(city);

        mPresenter.onUpdateComplete(mViewModel, MainActivityViewModel.QueryEnumMainActivity.UPDATE_WEATHER);

        verify(mHomepageCallbacks).showCityName(city);

    }

    @Test
    public void testOnSearchButtonClicked() throws Exception {

        mPresenter.onSearchButtonClicked("Sydney");

        verify(mHomepageCallbacks).showProgressBar();
    }
}