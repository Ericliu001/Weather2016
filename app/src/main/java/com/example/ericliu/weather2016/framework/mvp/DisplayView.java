package com.example.ericliu.weather2016.framework.mvp;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface DisplayView {

    void displayData(Object element, Presenter.RefreshDisplayEnum refreshDisplay);

    void setPresenter(Presenter presenter);



}
