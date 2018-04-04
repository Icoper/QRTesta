package com.example.android_dev.qrtest.model;

import java.io.Serializable;
import java.util.List;


public class SavedData implements Serializable {
    private IStory jsonStory;
    private Role role;
    private List<Integer> goalsList;
    private List<Integer> scanQRStoreList;

    public SavedData() {
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

    public List<Integer> getGoalsList() {
        return goalsList;
    }

    public void setGoalsList(List<Integer> goalsList) {
        this.goalsList = goalsList;
    }

    public List<Integer> getScanQRStoreList() {
        return scanQRStoreList;
    }

    public void setScanQRStoreList(List<Integer> scanQRStoreList) {
        this.scanQRStoreList = scanQRStoreList;
    }
}
