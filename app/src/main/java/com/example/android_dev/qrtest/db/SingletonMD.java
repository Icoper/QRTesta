package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.ArrayList;
import java.util.List;


public class SingletonMD {
    private static SingletonMD ourInstance = new SingletonMD();
    private List<IStory> stories;
    private JsonStory selectedStory;
    private Role selectedRole;
    private List<Integer> scannedQrInformationId;
    private List<Integer> addedByStoryGoals;

    public List<Integer> getAddedByStoryGoals() {
        if (addedByStoryGoals == null) {
            addedByStoryGoals = new ArrayList<>();
        }
        return addedByStoryGoals;
    }

    public void setAddedByStoryGoals(List<Integer> addedByStoryGoals) {
        this.addedByStoryGoals = addedByStoryGoals;
    }

    public List<Integer> getScannedQrInformationId() {
        if (scannedQrInformationId == null) {
            scannedQrInformationId = new ArrayList<>();
        }
        return scannedQrInformationId;
    }

    public void cleanScannedQrInformationId() {
        scannedQrInformationId = null;
    }

    public void setScannedQrInformationId(List<Integer> scannedQrInformationId) {
        this.scannedQrInformationId = scannedQrInformationId;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }


    public List<IStory> getStories() {
        if (stories == null || stories.isEmpty()) {
            stories = new ArrayList<>(new JsonDataInitializer().getStoryData());
        }
        return stories;
    }

    public JsonStory getSelectedStory() {
        if (selectedStory == null) {
            selectedStory = new JsonStory();
        }
        return selectedStory;
    }

    public void setSelectedStory(JsonStory selectedStory) {
        this.selectedStory = selectedStory;
    }

    public static SingletonMD getInstance() {
        return ourInstance;
    }

    public SingletonMD() {
    }
}
