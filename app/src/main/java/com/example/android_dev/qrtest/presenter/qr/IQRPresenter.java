package com.example.android_dev.qrtest.presenter.qr;

public interface IQRPresenter {
    void playMediaData(String fileType, String filePath);
    void checkCode(String code);
    void changeAlertMode(int modeScan, int modeShow);

}
