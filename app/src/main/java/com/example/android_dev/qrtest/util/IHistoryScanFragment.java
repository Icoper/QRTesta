package com.example.android_dev.qrtest.util;

import com.example.android_dev.qrtest.model.Story;

import java.util.ArrayList;

public interface IHistoryScanFragment {
    void showGridView(ArrayList<Story> scannedStoryList);

    void showMsg(String msg);
}
