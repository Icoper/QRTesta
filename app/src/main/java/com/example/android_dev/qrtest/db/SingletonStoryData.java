package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.ArrayList;
import java.util.List;


public class SingletonStoryData {
    private static SingletonStoryData ourInstance = new SingletonStoryData();
    private SavedDataStore savedDataStore;
    private List<IStory> stories;
    private IStory selectedStory;
    private Role selectedRole;
    private int notificationCount;

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public SavedDataStore getSavedDataStore() {
        if (savedDataStore == null) {
            savedDataStore = new SavedDataStore();
            savedDataStore.readDataFromFile();
        }
        return savedDataStore;
    }

    Role getSelectedRole() {
        if (selectedRole == null) {
            try {
                if (getSavedDataStore().loadSelectedRole() != null) {
                    selectedRole = getSavedDataStore().loadSelectedRole();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                selectedRole = new Role();
            }

        }

        return selectedRole;
    }

    void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
        getSavedDataStore().saveSelectedRole(selectedRole);
    }


    List<IStory> getStories() {
        if (stories == null || stories.isEmpty()) {
            stories = new ArrayList<>();
            stories.addAll(new JsonDataInitializer().getStoryData());
        }
        return stories;
    }

    public IStory getSelectedStory() {
        if (selectedStory == null) {
            try {
                if (getSavedDataStore().loadSelectedStory().getResFolderName() != null) {
                    selectedStory = getSavedDataStore().loadSelectedStory();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                selectedStory = new JsonStory();
            }
        }
        return selectedStory;
    }

    public void setSelectedStory(IStory selectedStory) {
        this.selectedStory = selectedStory;

        getSavedDataStore().saveSelectedStory(selectedStory);
    }

    public static SingletonStoryData getInstance() {
        return ourInstance;
    }

    public SingletonStoryData() {
    }
}
