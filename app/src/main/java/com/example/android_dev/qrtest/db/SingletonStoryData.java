package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.ArrayList;
import java.util.List;


public class SingletonStoryData {
    private static SingletonStoryData ourInstance = new SingletonStoryData();
    private List<IStory> stories;

    private IStory selectedStory;
    private Role selectedRole;

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }


    public List<IStory> getStories() {
        if (stories == null || stories.isEmpty()) {
            stories = new ArrayList<>();
            stories.addAll(new JsonDataInitializer().getStoryData());
        }
        return stories;
    }

    public IStory getSelectedStory() {
        if (selectedStory == null) {
            selectedStory = new JsonStory();
        }
        return selectedStory;
    }

    public void setSelectedStory(IStory selectedStory) {
        this.selectedStory = selectedStory;
    }

    public static SingletonStoryData getInstance() {
        return ourInstance;
    }

    public SingletonStoryData() {
    }
}
