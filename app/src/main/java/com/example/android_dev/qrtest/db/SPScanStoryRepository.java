package com.example.android_dev.qrtest.db;

import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

public class SPScanStoryRepository implements ISPScanStoryRepository {
    private SharedPreferences sharedPreferences;

    public SPScanStoryRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void addNewId(String id) {
        String history = getScanHistoryString();
        history += "/" + id;
        sharedPreferences.edit().putString("scan_history", history).apply();
    }

    @Override
    public List<String> getAllScannedId() {
        String scan = getScanHistoryString();
        String[] scanStoryArray = scan.split("/");
        List<String> scanStoryId = Arrays.asList(scanStoryArray);
        return scanStoryId;
    }

    private String getScanHistoryString() {
        return sharedPreferences.getString("scan_history", "");
    }
}
