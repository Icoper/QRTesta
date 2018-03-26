package com.example.android_dev.qrtest.presenter;

import android.util.Log;

import com.example.android_dev.qrtest.util.GlobalNames;

public class AppMediaPlayerPresenter implements IAppMediaPlayerPresenter {
    private final String LOG_TAG = "AppMediaPlayerPresenter";


    public AppMediaPlayerPresenter() {
    }

    @Override
    public String processMediaData(String type, String path) {
        Log.d(LOG_TAG, "processMediaData is call");
        String msg = "";
        if (type.equals(GlobalNames.AUDIO_RES)) {
            msg = GlobalNames.AUDIO_RES;
        } else if (type.equals(GlobalNames.VIDEO_RES)) {
            msg = GlobalNames.VIDEO_RES;
        }
        return msg;
    }

}
