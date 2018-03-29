package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.model.Role;

import java.util.ArrayList;
import java.util.List;


public class InMemoryStoryRepository implements IMemoryStoryRepository {
    @Override
    public synchronized ArrayList<JsonStory> getStoriesList() {
        return SingletonMD.getInstance().getStories();
    }

    @Override
    public synchronized JsonStory getSelectedStory() {
        return SingletonMD.getInstance().getSelectedStory();
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
        return SingletonMD.getInstance().getSelectedRole();
    }

    @Override
    public synchronized void setSelectedRole(Role selectedRole) {
        SingletonMD.getInstance().setSelectedRole(selectedRole);

    }

    @Override
    public void addQrInformationId(int id) {
        SingletonMD.getInstance().getScannedQrInformationId().add(id);
    }

    @Override
    public List<Integer> getQrInformationId() {
        return SingletonMD.getInstance().getScannedQrInformationId();
    }

    @Override
    public void cleanQrInformation() {
        SingletonMD.getInstance().cleanScannedQrInformationId();
        JsonStory jsonStory = getSelectedStory();
        for (int i = 0; i < jsonStory.getHistoryScansQRInformationsIDList().size(); i++) {
            jsonStory.getHistoryScansQRInformationsIDList().get(i).setShortInfo(true);
        }
    }

    @Override
    public void addNewGoalToList(List<Integer> newRes) {
        List<Integer> filteredNewRes = new ArrayList<>();
        List<Integer> goalList = SingletonMD.getInstance().getAddedByStoryGoals();
        for (int res : newRes) {
            boolean alreadyAdded = false;
            for (int _res : goalList) {
                if (_res == res) {
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                filteredNewRes.add(res);
            }
        }
        SingletonMD.getInstance().getAddedByStoryGoals().addAll(filteredNewRes);

    }

    @Override
    public List<Integer> getAddedByStoryGoals() {
        return SingletonMD.getInstance().getAddedByStoryGoals();
    }

    @Override
    public void cleanAddedByStoryGoals() {
        SingletonMD.getInstance().setAddedByStoryGoals(null);
    }
}
