package com.example.android_dev.qrtest.db;


import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.Role;

import java.util.ArrayList;
import java.util.List;

public interface IMemoryStoryRepository {

    ArrayList<JsonStory> getStoriesList();

    void setActorId(String id);

    String getActorId();

    JsonStory getSelectedStory();

    List<AssertItems.Resource> getResourceById(List<String> id);

    String getRoleResId();

    void setRolResId(String id);

    Role getSelectedRole();

    void setSelectedRole(Role selectedRole);

    void addNewGoalID(String id);

    List<String> getAllGoalsIds();

    void cleanGoalsStory();
}
