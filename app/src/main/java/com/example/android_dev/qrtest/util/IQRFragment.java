package com.example.android_dev.qrtest.util;


import com.example.android_dev.qrtest.model.Story;

public interface IQRFragment {
    void showAlertDialog(int modeScan, Story story, int modeShow);

    void startVideoPlayerActivity(String filePath);
    void showMsg(String msg);

    void startAudioPlayerActivity(String filePath);
}
