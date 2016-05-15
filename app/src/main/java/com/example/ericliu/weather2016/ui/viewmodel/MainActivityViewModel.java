package com.example.ericliu.weather2016.ui.viewmodel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ericliu on 12/04/2016.
 * <p/>
 * The ViewModel is a Fragment with on UI. The main reason to choose a Fragment as the implementation is that a Fragment
 * Can survive configuration changes by setting setRetainInstance(true);
 * And a Fragment gets access to Context for free.
 */
public class MainActivityViewModel extends Fragment implements ViewModel {


    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private RequestStatus mRequestStatus = RequestStatus.NOT_STARTED;
    private String city;
    private String weatherCondition;
    private Subscription mWeatherResultSubscripton;

    public Throwable getThrowable() {
        return mThrowable;
    }

    private Throwable mThrowable;

    @Inject
    RemoteWeatherRepo remoteWeatherRepo;

    private Presenter mPresenter;


    public MainActivityViewModel() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);
        setRetainInstance(true);
        resetFields();

    }

    private void resetFields() {
        mRequestStatus = RequestStatus.NOT_STARTED;
        city = null;
        weatherCondition = null;
        mThrowable = null;
    }

    @Override
    public void onInitialModelUpdate(int presenterId, @Nullable Bundle args) {

        onStartModelUpdate(presenterId, QueryEnumMainActivity.UPDATE_WEATHER, args);
    }

    @Override
    public void onStartModelUpdate(int presenterId, QueryEnum update, @Nullable Bundle args) {
        mRequestStatus = RequestStatus.LOADING;

        if (update instanceof QueryEnumMainActivity) {
            if (QueryEnumMainActivity.UPDATE_WEATHER == update) {
                WeatherSpecification specification = (WeatherSpecification) args.getSerializable(WeatherSpecification.ARG_WEATHER_SPECIFICATION);

                if (specification != null) {
//                    RetrieveWeatherService.start(getActivity(), specification);

                    retrieveWeather(specification);
                }

            }

        } else {
            throw new IllegalArgumentException("query not handled here");
        }


    }

    private void retrieveWeather(final WeatherSpecification specification) {
        Single<WeatherResult> weatherResultSingle = Single.fromCallable(new Callable<WeatherResult>() {
            @Override
            public WeatherResult call() throws Exception {
                return remoteWeatherRepo.get(specification);
            }
        });

        mWeatherResultSubscripton = weatherResultSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<WeatherResult>() {
                    @Override
                    public void onSuccess(WeatherResult value) {
                        mRequestStatus = RequestStatus.SUCESS;
                        onResultEvent(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        mRequestStatus = RequestStatus.FAILED;
                        // TODO: 15/05/2016  display error message
                        Log.e(TAG, error.getMessage());
                    }
                });
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
    public void onDestroy() {
        super.onDestroy();
        if (mWeatherResultSubscripton != null && !mWeatherResultSubscripton.isUnsubscribed()) {
            mWeatherResultSubscripton.unsubscribe();
        }
    }

    public void onResultEvent(WeatherResult repositoryResult) {

        handleWeatherResult(repositoryResult);
        mPresenter.onUpdateComplete(this, QueryEnumMainActivity.UPDATE_WEATHER);

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
    }

}
