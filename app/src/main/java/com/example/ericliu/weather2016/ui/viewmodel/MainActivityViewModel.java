package com.example.ericliu.weather2016.ui.viewmodel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.framework.mvp.RequestResult;
import com.example.ericliu.weather2016.framework.mvp.RequestStatus;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;
import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.model.WeatherSpecification;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;
import com.example.ericliu.weather2016.util.EspressoIdlingResource;

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
    private WeatherRequestResult mWeatherRequestResult = new WeatherRequestResult();
    private Subscription mWeatherResultSubscripton;

    public static class WeatherRequestResult implements RequestResult {

        public RequestStatus getRequestStatus() {
            return mRequestStatus;
        }

        public String getCity() {
            return city;
        }

        public String getWeatherCondition() {
            return weatherCondition;
        }

        public Throwable getThrowable() {
            return mThrowable;
        }

        private RequestStatus mRequestStatus = RequestStatus.NOT_STARTED;
        private String city;
        private String weatherCondition;
        private Throwable mThrowable;

    }


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
        mWeatherRequestResult = new WeatherRequestResult();
    }


    @Override
    public void onStartModelUpdate(int presenterId, QueryEnum update, @Nullable Bundle args) {
        mWeatherRequestResult = new WeatherRequestResult();
        mWeatherRequestResult.mRequestStatus = RequestStatus.LOADING;

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

    @Override
    public RequestResult getRequestResult(QueryEnum queryEnum) {
        if (queryEnum instanceof QueryEnumMainActivity) {

            if (queryEnum == QueryEnumMainActivity.UPDATE_WEATHER) {

                return mWeatherRequestResult;
            }
                return null;

        } else {
            throw new IllegalArgumentException("queryEnum must be an instance of QueryEnumMainActivity.");
        }
    }


    private void retrieveWeather(final WeatherSpecification specification) {
        Single<WeatherResult> weatherResultSingle = Single.fromCallable(new Callable<WeatherResult>() {
            @Override
            public WeatherResult call() throws Exception {
                return remoteWeatherRepo.get(specification);
            }
        });

        EspressoIdlingResource.increment();
        mWeatherResultSubscripton = weatherResultSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<WeatherResult>() {
                    @Override
                    public void onSuccess(WeatherResult value) {
                        mWeatherRequestResult.mRequestStatus = RequestStatus.SUCESS;
                        onResultEvent(value);
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onError(Throwable error) {
                        mWeatherRequestResult.mRequestStatus = RequestStatus.FAILED;
                        // TODO: 15/05/2016  display error message
                        mWeatherRequestResult.mThrowable = error;
                        Log.e(TAG, error.getMessage());
                        mPresenter.onUpdateComplete(mWeatherRequestResult, QueryEnumMainActivity.UPDATE_WEATHER);
                        EspressoIdlingResource.decrement();

                    }
                });
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
        mPresenter.onUpdateComplete(mWeatherRequestResult, QueryEnumMainActivity.UPDATE_WEATHER);

    }

    private void handleWeatherResult(WeatherResult weatherResult) {
        if (weatherResult == null) {
            return;
        }
        mWeatherRequestResult.city = weatherResult.name;
        if (weatherResult.weather != null && weatherResult.weather.length > 0) {
            mWeatherRequestResult.weatherCondition = weatherResult.weather[0].getDescription();
        }

    }


    public enum QueryEnumMainActivity implements QueryEnum {
        UPDATE_WEATHER;
    }

}
