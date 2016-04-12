package com.example.ericliu.weather2016.framework.mvp.base;


import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.framework.mvp.StubPresenter;
import com.example.ericliu.weather2016.framework.mvp.ViewModel;

/**
 * Created by ericliu on 27/01/2016.
 */
public abstract class BasePresenter implements Presenter {
    protected ViewModel mModel;
    protected DisplayView mDisplayView;

    protected int mPresenterId = -1;

    public BasePresenter(int presenterId, DisplayView displayView, ViewModel viewModel) {
        this.mPresenterId = presenterId;
        mDisplayView = displayView;
        mModel = viewModel;
        mDisplayView.setPresenter(this);
        mModel.setPresenter(0, new StubPresenter()); // to prevent null pointer when the data comes back before the view is created.
    }


    @Override
    public void setViewModel(ViewModel model) {
        mModel = model;
    }

    @Override
    public void setDisplayView(DisplayView view) {
        mDisplayView = view;
    }



    @Override
    public void onViewCreated() {
        mModel.setPresenter(mPresenterId, this);
    }

    @Override
    public void onViewDestroyed() {
        mModel.setPresenter(mPresenterId, new StubPresenter()); // use a StubPresenter to de-reference the View and prevent null pointer.
    }

}
