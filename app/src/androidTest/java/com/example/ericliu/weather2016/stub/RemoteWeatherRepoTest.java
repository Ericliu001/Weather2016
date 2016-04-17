package com.example.ericliu.weather2016.stub;


import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.ericliu.weather2016.model.WeatherResult;
import com.example.ericliu.weather2016.repo.RemoteWeatherRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ericliu on 12/04/2016.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class RemoteWeatherRepoTest {



    RemoteWeatherRepo repo;

    @Before
    public void setUp() throws Exception {
        repo = new RemoteWeatherRepo();
    }

    @After
    public void tearDown() throws Exception {
        repo = null;

    }

    @Test
    public void testGet() throws Exception {
        WeatherResult result = repo.get(null);


        assertThat(result, notNullValue());
    }

}