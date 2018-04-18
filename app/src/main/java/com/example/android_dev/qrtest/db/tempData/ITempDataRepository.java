package com.example.android_dev.qrtest.db.tempData;

import com.example.android_dev.qrtest.model.AppMenuItem;

/**
 * Created by user on 12-Apr-18.
 */

public interface ITempDataRepository {
    void setAppMenuItem(AppMenuItem appMenuItem);

    AppMenuItem getAppMenuItem();
}
