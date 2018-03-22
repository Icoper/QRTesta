package com.example.android_dev.qrtest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPHelper {
    public static String getScanHistory(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("scan_history", "");
    }

    public static void setScanHistory(Context context, String history) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("scan_history", history).apply();
    }
}
