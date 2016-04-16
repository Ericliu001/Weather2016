package com.example.ericliu.weather2016.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericliu.weather2016.R;
import com.example.ericliu.weather2016.application.MyApplication;
import com.example.ericliu.weather2016.framework.mvp.DisplayElement;
import com.example.ericliu.weather2016.framework.mvp.ViewUpdateDispatcher;
import com.example.ericliu.weather2016.ui.base.DisplayViewActivity;
import com.example.ericliu.weather2016.ui.presenter.MainActivityPresenter;
import com.example.ericliu.weather2016.ui.viewmodel.MainActivityViewModel;
import com.example.ericliu.weather2016.util.NetworkUtil;

public class MainActivity extends DisplayViewActivity {
    private static final String VIEW_MODEL_TAG = "main.activity.viewmodel";

    private EditText etCityName;
    private Button btnSearchWeatherCondition;
    private TextView tvCityName, tvWeatherCondition;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getComponent().inject(this);
        ViewUpdateDispatcher.INSTANCE.register(this);


        setupPresenter(savedInstanceState);
        initViews();
        mPresenter.onViewCreated();

        if (savedInstanceState != null) {
            mPresenter.loadInitialData(null, true);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDestroyed();
        super.onDestroy();
    }

    private void initViews() {
        etCityName = (EditText) findViewById(R.id.etCityName);
        btnSearchWeatherCondition = (Button) findViewById(R.id.btnSearch);
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvWeatherCondition = (TextView) findViewById(R.id.tvWeatherCondition);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSearchWeatherCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtil.isOnline(MainActivity.this)) {
                    NetworkUtil.checkNetworkAndShowErrorMsg(MainActivity.this);
                    return;
                }

                String city = etCityName.getText().toString();
                if (!TextUtils.isEmpty(city)) {

                    Bundle args = new Bundle();
                    args.putString(MainActivityPresenter.ARG_CITY_NAME, city);
                    mPresenter.onUserAction(MainActivityPresenter.UserActionEnumMainActivity.BUTTON_CLICKED, args);
                } else {
                    Toast.makeText(MainActivity.this, "city name can't be empty!", Toast.LENGTH_SHORT).show();
                }
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

    @DisplayElement(id = MainActivityPresenter.SHOW_CITY_NAME)
    public void showCityName(String city) {
        Toast.makeText(this, city, Toast.LENGTH_SHORT).show();
        tvCityName.setText(city);
    }

    @DisplayElement(id = MainActivityPresenter.SHOW_WEATHER_CONDITION)
    public void showWeatherCondition(String weatherCondition) {
        tvWeatherCondition.setText(weatherCondition);
    }


    @DisplayElement(id = MainActivityPresenter.SHOW_DIALOG)
    public void showDialog(String message) {
        displayDialog(message);
    }


    @DisplayElement(id = MainActivityPresenter.HIDE_PROGRESS_BAR)
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


    @DisplayElement(id = MainActivityPresenter.SHOW_PROGRESS_BAR)
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    private void displayDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, null);

        builder.create().show();

    }


}
