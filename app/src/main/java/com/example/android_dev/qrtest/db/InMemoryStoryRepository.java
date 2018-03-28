package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.Role;

import java.util.ArrayList;
import java.util.List;


public class InMemoryStoryRepository implements IMemoryStoryRepository {
    @Override
    public synchronized ArrayList<JsonStory> getStoriesList() {
        return SingletonMD.getInstance().getStories();
    }

    @Override
    public synchronized void setActorId(String id) {
        SingletonMD.getInstance().setSelectedActorId(id);
    }

    @Override
    public synchronized String getActorId() {
        return SingletonMD.getInstance().getSelectedActorId();
    }

    @Override
    public synchronized JsonStory getSelectedStory() {
        return SingletonMD.getInstance().getSelectedStory();
    }

    @Override
    public synchronized List<AssertItems.Resource> getResourceById(List<String> id) {
        List<AssertItems.Resource> resources = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            for (AssertItems assertItems : getSelectedStory().getAssertItems()) {
                if (assertItems.getId().equals(id.get(i))) {
                    resources.addAll(assertItems.getResources());
                }
            }
        }

        return resources;
    }

    @Override
    public synchronized String getRoleResId() {
        return SingletonMD.getInstance().getRoleResId();
    }

    @Override
    public synchronized void setRolResId(String id) {
        SingletonMD.getInstance().setRoleResId(id);
    }

    @Override
    public synchronized Role getSelectedRole() {
        return SingletonMD.getInstance().getSelectedRole();
    }

    @Override
    public synchronized void setSelectedRole(Role selectedRole) {
        SingletonMD.getInstance().setSelectedRole(selectedRole);

    }

    @Override
    public synchronized void addNewGoalID(String id) {
        List<String> goals = SingletonMD.getInstance().getGoalsID();
        goals.add(id);
        SingletonMD.getInstance().setGoalsID(goals);
    }


    @Override
    public synchronized List<String> getAllGoalsIds() {
        return SingletonMD.getInstance().getGoalsID();
    }

    @Override
    public void cleanGoalsStory() {
        SingletonMD.getInstance().setGoalsID(null);
    }
}
