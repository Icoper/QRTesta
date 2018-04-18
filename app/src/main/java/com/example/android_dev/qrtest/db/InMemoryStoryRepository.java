package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.ArrayList;
import java.util.List;


public class InMemoryStoryRepository implements IStoryRepository {
    @Override
    public synchronized List<IStory> getStoriesList() {
        return SingletonStoryData.getInstance().getStories();
    }

    @Override
    public synchronized IStory getSelectedStory() {
        return SingletonStoryData.getInstance().getSelectedStory();
    }

    @Override
    public synchronized List<AssetTypes> getResourceById(List<Integer> ids) {
        List<AssetTypes> resources = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            for (AssetTypes assertItems : getSelectedStory().getAssetTypeList()) {
                if (assertItems.getId() == ids.get(i)) {
                    resources.add(assertItems);
                }
            }
        }

        return resources;
    }


    @Override
    public synchronized Role getSelectedRole() {
        return SingletonStoryData.getInstance().getSelectedRole();
    }

    @Override
    public synchronized void setSelectedRole(Role selectedRole) {
        SingletonStoryData.getInstance().setSelectedRole(selectedRole);

    }

    @Override
    public void setSelectedStory(IStory story) {
        SingletonStoryData.getInstance().setSelectedStory(story);
    }


}
