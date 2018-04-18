package com.example.android_dev.qrtest.db.tempData;

import com.example.android_dev.qrtest.model.AppMenuItem;

/**
 * Created by user on 12-Apr-18.
 */

public class SingletonTempData {
    private static final SingletonTempData ourInstance = new SingletonTempData();
    private AppMenuItem appMenuItem;


    public AppMenuItem getAppMenuItem() {
        return appMenuItem;
    }

    public void setAppMenuItem(AppMenuItem appMenuItem) {
        this.appMenuItem = appMenuItem;
    }

    public static SingletonTempData getInstance() {
        return ourInstance;
    }

    private SingletonTempData() {
    }
}
