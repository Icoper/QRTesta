package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.Role;

import java.util.ArrayList;


public class SingletonMD {
    private static SingletonMD ourInstance = new SingletonMD();
    private ArrayList<JsonStory> stories;
    private JsonStory selectedStory;
    private Role selectedRole;
    private String selectedActorId;
    private String roleResId;

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public String getRoleResId() {
        return roleResId;
    }

    public void setRoleResId(String roleResId) {
        this.roleResId = roleResId;
    }

    public String getSelectedActorId() {
        return selectedActorId;
    }

    public void setSelectedActorId(String selectedActorId) {
        this.selectedActorId = selectedActorId;
    }

    public ArrayList<JsonStory> getStories() {
        if (stories == null || stories.isEmpty()) {
            stories = new ArrayList<>(new MediaDataWorker().getStoryData());
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
