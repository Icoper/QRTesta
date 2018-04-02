package com.example.android_dev.qrtest.ui.fragment;

import com.example.android_dev.qrtest.model.QrInformation;

import java.util.ArrayList;
import java.util.List;

public interface IHistoryScanFragment {
    void showGridView(ArrayList<QrInformation> scannedStoryList);

    void showAlertDialog(List<Integer> storyResId, int modeShow);

    void startVideoPlayerActivity(String filePath);
    void showMsg(String msg);

    void startAudioPlayerActivity(String filePath);
}
