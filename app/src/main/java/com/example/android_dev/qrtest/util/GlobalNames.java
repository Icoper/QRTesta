package com.example.android_dev.qrtest.util;


import android.os.Environment;

public interface GlobalNames {
    String ENVIRONMENT_STORE = Environment.getExternalStorageDirectory() + "/qrmedia/";

    String AUDIO_RES = "audio";
    String VIDEO_RES = "video";
    String IMG_RES = "image";
    String DOC_RES = "document";
    
    int QR_MODE_FIRST_SCAN = 0;
    int QR_MODE_SIMPLE_SCAN = 1;
    int ALERT_MODE_FULL_INFO = 2;
    int ALERT_MODE_SMALL_INFO = 3;

    int NOTIFICATION_LOAD_DATA_ID = 1;
    int NOTIFICATION_NEW_GOAL_ID = 2;

    int DEFAULT_GRID_COLUMN_COUNT = 3;
}
