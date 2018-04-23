package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.List;

public interface ISavedDataStore {
    void saveSelectedStory(IStory jsonStory);

    IStory loadSelectedStory();

    void saveSelectedRole(Role role);

    Role loadSelectedRole();

    void saveKnowGoals(List<Integer> list);

    List<Integer> loadKnowGoals();

    void saveNewGoals(List<Integer> list);

    List<Integer> loadNewGoals();

    void saveScanQrCodes(List<Integer> list);

    List<Integer> loadScanQrCodes();

    void cleanUserData();
}
