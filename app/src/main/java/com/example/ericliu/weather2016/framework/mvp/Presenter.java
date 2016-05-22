package com.example.ericliu.weather2016.framework.mvp;

import android.os.Bundle;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface Presenter {

    void setViewModel(ViewModel viewModel);

    void setDisplayView(DisplayView view);


    /**
     * this method is used to load the data for the first time when the View is just loaded into the screen.
     *
     * @param args
     * @param isConfigurationChange - a boolean to indicate if this method is called due to a re-creation of Views as a result
     *                              of a configuration change.
     */
    void loadInitialData(Bundle args, boolean isConfigurationChange);

    /**
     * a callback for ViewModel to pass back the data when it's loaded.
     *
     * @param requestResult
     * @param query
     */
    void onUpdateComplete(RequestResult requestResult, ViewModel.QueryEnum query);



    void onViewCreated();

    /**
     * de-reference the DisplayView and ViewModel so they can be garbage collected
     */
    void onViewDestroyed();



}
