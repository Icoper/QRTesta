package com.example.android_dev.qrtest.util;


public interface IQRFragment {
    void showAlertDialog(int modeScan, String storyResId, int modeShow);

    void startVideoPlayerActivity(String filePath);
    void showMsg(String msg);

    void startAudioPlayerActivity(String filePath);
}
