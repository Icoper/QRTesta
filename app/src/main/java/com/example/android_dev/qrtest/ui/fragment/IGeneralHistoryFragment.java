package com.example.android_dev.qrtest.ui.fragment;


public interface IGeneralHistoryFragment {
    void showMsg(String msg);

    void startVideoPlayerActivity(String filepath);

    void startAudioPlayerActivity(String filepath);

    void startImageViewerActivity(String filepath);
}
