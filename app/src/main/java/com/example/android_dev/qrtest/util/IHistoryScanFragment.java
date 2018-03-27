package com.example.android_dev.qrtest.util;

import com.example.android_dev.qrtest.model.json.JsonStory;

import java.util.ArrayList;

public interface IHistoryScanFragment {
    void showGridView(ArrayList<JsonStory> scannedStoryList);

    void showMsg(String msg);
}
