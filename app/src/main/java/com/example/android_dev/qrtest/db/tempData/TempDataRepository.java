package com.example.android_dev.qrtest.db.tempData;

import com.example.android_dev.qrtest.model.AppMenuItem;

/**
 * Created by user on 12-Apr-18.
 */

public class TempDataRepository implements ITempDataRepository {
    @Override
    public void setAppMenuItem(AppMenuItem appMenuItem) {
        SingletonTempData.getInstance().setAppMenuItem(appMenuItem);
    }

    @Override
    public AppMenuItem getAppMenuItem() {
        return SingletonTempData.getInstance().getAppMenuItem();
    }
}
