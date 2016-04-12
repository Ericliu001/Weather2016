package com.example.ericliu.weather2016.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ericliu.weather2016.R;
import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.mvp.Presenter;
import com.example.ericliu.weather2016.ui.base.DisplayViewActivity;
import com.example.ericliu.weather2016.ui.presenter.MainActivityPresenter;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;

public class MainActivity extends DisplayViewActivity {
    private static final String VIEW_MODEL_TAG = "main.activity.viewmodel";

    private EditText etCityName;
    private Button btnSearchWeatherCondition;
    private TextView tvCityName, tvWeatherCondition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getComponent().inject(this);

        setupPresenter(savedInstanceState);

        initViews();

        if (savedInstanceState != null) {
            mPresenter.loadInitialData(null, true);
        }
    }

    private void initViews() {
        etCityName = (EditText) findViewById(R.id.etCityName);
        btnSearchWeatherCondition = (Button) findViewById(R.id.btnSearch);
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvWeatherCondition = (TextView) findViewById(R.id.tvWeatherCondition);

        btnSearchWeatherCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCityName.getText().toString();
                Bundle args = new Bundle();
                args.putString(MainActivityPresenter.ARG_CITY_NAME, city);
                mPresenter.onUserAction(MainActivityPresenter.UserActionEnumMainActivity.BUTTON_CLICKED, args);
            }
        });
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
        if (RefreshDisplayEnumMainActivity.SHOW_CITY_NAME.getId() == refreshDisplay.getId()) {

            String cityName = (String) element;
            tvCityName.setText(cityName);

        } else if (RefreshDisplayEnumMainActivity.SHOW_WEATHER_CONDITION.getId() == refreshDisplay.getId()) {

            String weatherCondition = (String) element;
            tvWeatherCondition.setText(weatherCondition);

        } else if (RefreshDisplayEnumMainActivity.SHOW_PROGRESS_BAR.getId() == refreshDisplay.getId()) {

        } else if (RefreshDisplayEnumMainActivity.HIDE_PROGRESS_BAR.getId() == refreshDisplay.getId()) {

        } else {
            throw new IllegalArgumentException("display not handled here.");
        }

    }


    public enum RefreshDisplayEnumMainActivity implements Presenter.RefreshDisplayEnum {
        SHOW_PROGRESS_BAR, HIDE_PROGRESS_BAR, SHOW_WEATHER_CONDITION, SHOW_CITY_NAME;

        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}
