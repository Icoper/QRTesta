package com.example.android_dev.qrtest.presenter.historyscan;


import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.QrInformation;

public interface IHistoryScanPresenter {
    void getScannedStoryList();

    void showStoryContent(QrInformation qrInformation);

    void playMediaData(AssetTypes resource);

    void changeAlertMode(QrInformation qrInformation, int modeShow);
}
