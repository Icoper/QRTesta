package com.example.android_dev.qrtest.db;


import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.List;

public interface IMemoryStoryRepository {

    List<IStory> getStoriesList();

    IStory getSelectedStory();

    List<AssetTypes> getResourceById(List<Integer> ids);

    Role getSelectedRole();

    void setSelectedRole(Role selectedRole);

    void addQrInformationId(int id);

    List<Integer> getQrInformationId();

    void cleanQrInformation();

    void addNewGoalToList(List<Integer> res);

    List<Integer> getAddedByStoryGoals();

    void cleanAddedByStoryGoals();
}
