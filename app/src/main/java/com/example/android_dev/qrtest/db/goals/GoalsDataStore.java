package com.example.android_dev.qrtest.db.goals;

import java.util.ArrayList;
import java.util.List;

public class GoalsDataStore implements IGoalsDataStore {
    private List<Integer> allGoalsResIds;
    private List<Integer> newGoalsResIds;

    @Override
    public void updateNewGoals(List<Integer> ids) {
        List<Integer> filteredNewRes = new ArrayList<>();
        newGoalsResIds = getNewGoals();
        for (int res : ids) {
            boolean alreadyAdded = false;
            for (int _res : newGoalsResIds) {
                if (_res == res) {
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                filteredNewRes.add(res);
            }
        }
        newGoalsResIds.addAll(filteredNewRes);
        getGoalsDataStore().setNewGoals(newGoalsResIds);
    }

    @Override
    public List<Integer> getAllGoals() {
        return getGoalsDataStore().getAllGoals();
    }


    @Override
    public List<Integer> getNewGoals() {
        return getGoalsDataStore().getNewGoals();
    }

    @Override
    public void cleanNewGoals() {
        newGoalsResIds = getNewGoals();
        allGoalsResIds = getAllGoals();

        for (int id : newGoalsResIds) {
            boolean alreadyAdded = false;
            for (int _id : allGoalsResIds) {
                if (id == _id) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) {
                allGoalsResIds.add(id);
            }
        }

        newGoalsResIds = null;
        getGoalsDataStore().setNewGoals(newGoalsResIds);
        getGoalsDataStore().setAllGoals(allGoalsResIds);
    }

    private SingletonGoalsData getGoalsDataStore() {
        return SingletonGoalsData.getInstance();
    }



}
