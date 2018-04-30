package com.example.android_dev.qrtest.ui.fragment;

import java.util.List;

public interface IQRFragment {
    void showAlertDialog(int modeScan, List<Integer> storyResId, int modeShow);

    void startVideoPlayerActivity(String filePath);

    void showMsg(int msg);

    void startImageViewerActivity(String filepath);
}
