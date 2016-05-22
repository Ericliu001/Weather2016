package com.example.ericliu.weather2016.framework.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface ViewModel {


    /**
     * * Updates this ViewModel according to a user {@code action} and {@code args}.
     * <p/>
     * Add the constants used to store values in the bundle to the ViewModel implementation class as
     * final static protected strings.
     *
     * @param presenterId - the id of the Presenter which is calling this method
     * @param args
     */
    void onStartModelUpdate(int presenterId, QueryEnum queryEnum, @Nullable Bundle args);



    RequestResult getRequestResult(QueryEnum queryEnum);


    void setPresenter(int presenterId, Presenter presenter);

    /**
     * Represents a update request to update the display
     */
    interface QueryEnum {
    }
}
