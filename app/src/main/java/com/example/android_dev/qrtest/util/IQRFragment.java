package com.example.android_dev.qrtest.util;

import java.util.List;

public interface IQRFragment {
    void showAlertDialog(int modeScan, List<Integer> storyResId, int modeShow);
    void startVideoPlayerActivity(String filePath);
    void showMsg(String msg);
    void startAudioPlayerActivity(String filePath);
}
