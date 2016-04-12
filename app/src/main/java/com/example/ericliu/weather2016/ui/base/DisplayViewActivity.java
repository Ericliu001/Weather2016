package com.example.ericliu.weather2016.ui.base;

import android.app.Activity;

import com.example.ericliu.weather2016.framework.mvp.DisplayView;
import com.example.ericliu.weather2016.framework.mvp.Presenter;

/**
 * Created by ericliu on 12/04/2016.
 */
public abstract class DisplayViewActivity extends Activity implements DisplayView {
    protected Presenter mPresenter;



    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewCreated();
    }

    @Override
    protected void onPause() {
        mPresenter.onViewDestroyed();
        super.onPause();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
