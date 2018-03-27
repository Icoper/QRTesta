package com.example.android_dev.qrtest.presenter.qr;

import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;

public interface IQRPresenter {
    void playMediaData(AssertItems.Resource resource);
    void checkCode(String code);

    void changeAlertMode(int modeScan, int modeShow, JsonStory jsonStory);

}
