package com.example.android_dev.qrtest.presenter;

import android.util.Log;

import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.util.GlobalNames;

public class AppMediaPlayerPresenter implements IAppMediaPlayerPresenter {
    private final String LOG_TAG = "AppMediaPlayerPresenter";


    public AppMediaPlayerPresenter() {
    }

    @Override
    public String processMediaData(AssertItems.Resource resource) {
        Log.d(LOG_TAG, "processMediaData is call");
        String msg = "";
        if (resource.getType().equals(GlobalNames.AUDIO_RES)) {
            msg = GlobalNames.AUDIO_RES;
        } else if (resource.getType().equals(GlobalNames.VIDEO_RES)) {
            msg = GlobalNames.VIDEO_RES;
        }
        return msg;
    }

}
