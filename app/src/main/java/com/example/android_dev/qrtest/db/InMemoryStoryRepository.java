package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.Role;

import java.util.ArrayList;
import java.util.List;


public class InMemoryStoryRepository implements IMemoryStoryRepository {
    @Override
    public ArrayList<JsonStory> getStoriesList() {
        return SingletonMD.getInstance().getStories();
    }

    @Override
    public void setActorId(String id) {
        SingletonMD.getInstance().setSelectedActorId(id);
    }

    @Override
    public String getActorId() {
        return SingletonMD.getInstance().getSelectedActorId();
    }

    @Override
    public JsonStory getSelectedStory() {
        return SingletonMD.getInstance().getSelectedStory();
    }

    @Override
    public List<AssertItems.Resource> getResourceById(String id) {
        for (AssertItems assertItems : getSelectedStory().getAssertItems()) {
            if (assertItems.getId().equals(id)) {
                return assertItems.getResources();
            }
        }
        return null;
    }

    @Override
    public String getRoleResId() {
        return SingletonMD.getInstance().getRoleResId();
    }

    @Override
    public void setRolResId(String id) {
        SingletonMD.getInstance().setRoleResId(id);
    }

    @Override
    public Role getSelectedRole() {
        return SingletonMD.getInstance().getSelectedRole();
    }

    @Override
    public void setSelectedRole(Role selectedRole) {
        SingletonMD.getInstance().setSelectedRole(selectedRole);

    }
}
