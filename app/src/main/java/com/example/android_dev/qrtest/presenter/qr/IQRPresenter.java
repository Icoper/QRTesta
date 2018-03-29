package com.example.android_dev.qrtest.presenter.qr;

import com.example.android_dev.qrtest.model.AssetTypes;

public interface IQRPresenter {
    void playMediaData(AssetTypes resource);
    void checkCode(String code);

    void changeAlertMode(int modeScan, int modeShow);

}
