package com.example.android_dev.qrtest.db;

import android.content.SharedPreferences;

import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SPScanStoryRepository implements ISPScanStoryRepository {
    private SharedPreferences sharedPreferences;
    private IMemoryStoryRepository memoryStoryRepository;

    public SPScanStoryRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        memoryStoryRepository = new InMemoryStoryRepository();
    }

    @Override
    public void addNewId(String id) {
        String history = getScanHistoryString();
        String[] historyArray = history.split("/");
        boolean isFound = false;

        for (int i = 0; i < historyArray.length; i++) {
            if (historyArray[i].equals(id)) {
                isFound = true;
            }
        }
        if (!isFound) {
            history += "/" + id;
            sharedPreferences.edit().putString("scan_history", history).apply();
        }
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
