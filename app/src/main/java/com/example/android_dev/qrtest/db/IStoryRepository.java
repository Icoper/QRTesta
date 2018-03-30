package com.example.android_dev.qrtest.db;


import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.List;

public interface IStoryRepository {

    List<IStory> getStoriesList();

    IStory getSelectedStory();

    List<AssetTypes> getResourceById(List<Integer> ids);

    Role getSelectedRole();

    void setSelectedRole(Role selectedRole);

}
