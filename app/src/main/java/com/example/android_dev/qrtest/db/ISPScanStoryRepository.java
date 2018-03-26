package com.example.android_dev.qrtest.db;


import java.util.List;

public interface ISPScanStoryRepository {
    void addNewId(String id);

    List<String> getAllScannedId();
}
