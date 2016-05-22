package com.example.ericliu.weather2016;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.ericliu.weather2016.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by ericliu on 22/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchWeatherTest {

    private  String cityName = "Adelaide";


    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);



    @Test
    public void inputCityName(){

        onView(withId(R.id.etCityName)).perform(typeText(cityName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnSearch)).perform(click());

        // Check if the add note screen is displayed
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.tvCityName)).check(matches(withText("Sydney")));
    }
}
