package com.example.android_dev.qrtest.db;

import java.util.ArrayList;
import java.util.List;

public class GoalsDataStore implements IGoalsDataStore {
    private List<Integer> allGoalsResIds;
    private List<Integer> newGoalsResIds;

    @Override
    public void update(List<Integer> ids) {
        List<Integer> filteredNewRes = new ArrayList<>();
        allGoalsResIds = getAll();
        for (int res : ids) {
            boolean alreadyAdded = false;
            for (int _res : allGoalsResIds) {
                if (_res == res) {
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                filteredNewRes.add(res);
            }
        }
        allGoalsResIds.addAll(filteredNewRes);
        getSavedDataStore().saveKnowGoals(allGoalsResIds);
    }

    @Override
    public List<Integer> getAll() {
        return getList();
    }

    @Override
    public void setList(List<Integer> list) {
        this.allGoalsResIds = list;
        getSavedDataStore().saveKnowGoals(list);
    }

    @Override
    public void saveNewGoals(List<Integer> list) {
        if (newGoalsResIds == null) {
            newGoalsResIds = new ArrayList<>();
        }
        newGoalsResIds.addAll(list);
    }

    @Override
    public List<Integer> loadNewGoals() {
        if (newGoalsResIds == null) {
            newGoalsResIds = new ArrayList<>();
        }
        return newGoalsResIds;
    }

    @Override
    public void cleanNewGoals() {
        newGoalsResIds = null;
    }

    private List<Integer> getList() {
        if (allGoalsResIds == null) {
            try {
                if (getSavedDataStore().loadKnowGoals() != null) {
                    allGoalsResIds = getSavedDataStore().loadKnowGoals();
                } else allGoalsResIds = new ArrayList<>();
            } catch (NullPointerException e) {
                e.printStackTrace();
                allGoalsResIds = new ArrayList<>();
            }
        }
        return allGoalsResIds;
    }


    private SavedDataStore getSavedDataStore() {
        return SingletonStoryData.getInstance().getSavedDataStore();
    }

    @Override
    public void setNotificationCount(int count) {
        SingletonStoryData.getInstance().setNotificationCount(count);
    }

    @Override
    public int getNotificationCount() {
        return SingletonStoryData.getInstance().getNotificationCount();
    }
}
