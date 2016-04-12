package com.example.ericliu.weather2016.ui;

import android.os.Bundle;

import com.example.ericliu.weather2016.R;
import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.ui.base.DisplayViewActivity;
import com.example.ericliu.weather2016.ui.presenter.MainActivityPresenter;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

public class MainActivity extends DisplayViewActivity {
    private static final String VIEW_MODEL_TAG = "main.activity.viewmodel";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getComponent().inject(this);

        setupPresenter(savedInstanceState);
    }

    private void setupPresenter(Bundle savedInstanceState) {
        MainActivityViewModel viewModelFragment;
        if (savedInstanceState == null) {
            viewModelFragment = new MainActivityViewModel();
            getFragmentManager().beginTransaction().add(viewModelFragment, VIEW_MODEL_TAG).commit();
        } else {
            viewModelFragment = (MainActivityViewModel) getFragmentManager().findFragmentByTag(VIEW_MODEL_TAG);
        }

        mPresenter = new MainActivityPresenter(0, this, viewModelFragment);
    }




    @Override
    public void displayData(Object element, Presenter.RefreshDisplayEnum refreshDisplay) {

    }



    public enum RefreshDisplayEnumMainActivity implements Presenter.RefreshDisplayEnum{
        SHOW_PROGRESS_BAR, HIDE_PROGRESS_BAR;

        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}
