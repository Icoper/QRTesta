package com.example.android_dev.qrtest.presenter;

import android.util.Log;

import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.util.GlobalNames;

public class AppMediaPlayerPresenter implements IAppMediaPlayerPresenter {
    private final String LOG_TAG = "AppMediaPlayerPresenter";


    public AppMediaPlayerPresenter() {
    }

    @Override
    public String processMediaData(AssetTypes resource) {
        Log.d(LOG_TAG, "processMediaData is call");
        String msg = "";
        if (resource.getFileType().equals(GlobalNames.AUDIO_RES)) {
            msg = GlobalNames.AUDIO_RES;
        } else if (resource.getFileType().equals(GlobalNames.VIDEO_RES)) {
            msg = GlobalNames.VIDEO_RES;
        }
        return msg;
    }

}
