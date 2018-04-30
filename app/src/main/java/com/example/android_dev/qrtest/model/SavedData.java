package com.example.android_dev.qrtest.model;

import java.io.Serializable;
import java.util.List;


public class SavedData implements Serializable {
    private IStory jsonStory;
    private Role role;
    private List<Integer> allGoalsList;
    private List<Integer> newGoalsList;
    private List<Integer> scanQRStoreList;

    public SavedData() {
    }

    public List<Integer> getNewGoalsList() {
        return newGoalsList;
    }

    public void setNewGoalsList(List<Integer> newGoalsList) {
        this.newGoalsList = newGoalsList;
    }

    public IStory getJsonStory() {
        return jsonStory;
    }

    public void setJsonStory(IStory jsonStory) {
        this.jsonStory = jsonStory;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Integer> getAllGoalsList() {
        return allGoalsList;
    }

    public void setAllGoalsList(List<Integer> allGoalsList) {
        this.allGoalsList = allGoalsList;
    }

    public List<Integer> getScanQRStoreList() {
        return scanQRStoreList;
    }

    public void setScanQRStoreList(List<Integer> scanQRStoreList) {
        this.scanQRStoreList = scanQRStoreList;
    }
}
