package com.example.android_dev.qrtest.db;

import android.os.Build;

import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;
import com.example.android_dev.qrtest.model.SavedData;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class SavedDataStore implements ISavedDataStore {
    private final String FILE_NAME = "user_store.txt";
    private final String FILE_PATH = GlobalNames.ENVIRONMENT_STORE;
    private SavedData savedData;

    @Override
    public void saveSelectedStory(IStory jsonStory) {
        savedData = getSavedData();
        savedData.setJsonStory(jsonStory);
        saveDataToFile();
    }

    @Override
    public IStory loadSelectedStory() {
        savedData = getSavedData();
        return savedData.getJsonStory();
    }

    @Override
    public void saveSelectedRole(Role role) {
        savedData = getSavedData();
        savedData.setRole(role);
        saveDataToFile();
    }

    @Override
    public Role loadSelectedRole() {
        savedData = getSavedData();
        return savedData.getRole();
    }

    @Override
    public void saveKnowGoals(List<Integer> list) {
        savedData = getSavedData();
        savedData.setGoalsList(list);
        saveDataToFile();
    }

    @Override
    public List<Integer> loadKnowGoals() {
        savedData = getSavedData();
        return savedData.getGoalsList();
    }

    @Override
    public void saveScanQrCodes(List<Integer> list) {
        savedData = getSavedData();
        savedData.setScanQRStoreList(list);
        saveDataToFile();
    }

    @Override
    public List<Integer> loadScanQrCodes() {
        savedData = getSavedData();
        if (savedData.getScanQRStoreList() == null) {
            savedData.setScanQRStoreList(new ArrayList<Integer>());
            saveScanQrCodes(new ArrayList<Integer>());
        }

        return savedData.getScanQRStoreList();
    }


    private SavedData getSavedData() {
        if (savedData == null) {
            savedData = readDataFromFile();
        }
        return savedData;
    }

    @Override
    public void cleanUserData() {
        File file = new File(FILE_PATH + FILE_NAME);
        file.delete();
    }

    private synchronized void saveDataToFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Write objects to file
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(FILE_PATH + FILE_NAME)))) {
                outputStream.writeObject(savedData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // TODO implement from older API
        }


    }

    public synchronized SavedData readDataFromFile() {
        SavedData savedData = new SavedData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(FILE_PATH + FILE_NAME)))) {
                savedData = (SavedData) inputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // TODO implement from older API

        }
        return savedData;
    }
}
