package com.example.ericliu.weather2016.service;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ericliu.weather2016.model.WeatherSpecification;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ericliu on 12/04/2016.
 */
@RunWith(AndroidJUnit4.class)
public class RetrieveWeatherServiceTest {

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStart() throws Exception {

    }

    @Test
    public void testOnHandleIntent() throws Exception {

        // Create the service Intent.
        Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(), RetrieveWeatherService.class);

        // Data can be passed to the service via the Intent.
        WeatherSpecification specification = new WeatherSpecification();
        specification.setCityName("Sydney");
        serviceIntent.putExtra(WeatherSpecification.ARG_WEATHER_SPECIFICATION, specification);

        mServiceRule.startService(serviceIntent);

    }
}