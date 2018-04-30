package com.example.android_dev.qrtest.db.goals;

import com.example.android_dev.qrtest.db.SavedDataStore;
import com.example.android_dev.qrtest.db.SingletonStoryData;

import java.util.ArrayList;
import java.util.List;

public class SingletonGoalsData {
    private static final SingletonGoalsData ourInstance = new SingletonGoalsData();

    public static SingletonGoalsData getInstance() {
        return ourInstance;
    }

    private SingletonGoalsData() {
    }

    private List<Integer> allGoals;
    private List<Integer> newGoals;

    public synchronized List<Integer> getAllGoals() {
        if (allGoals == null) {
            allGoals = getSavedDataStore().loadAllGoals();
        }
        if (allGoals == null) {
            allGoals = new ArrayList<>();
        }
        return allGoals;
    }

    public void setAllGoals(List<Integer> allGoals) {
        this.allGoals = allGoals;
        getSavedDataStore().saveAllGoals(allGoals);
    }

    public List<Integer> getNewGoals() {
        if (newGoals == null) {
            newGoals = getSavedDataStore().loadNewGoals();
        }
        if (newGoals == null) {
            newGoals = new ArrayList<>();
        }
        return newGoals;
    }

    public void setNewGoals(List<Integer> newGoals) {
        this.newGoals = newGoals;
        getSavedDataStore().saveNewGoals(newGoals);
    }

    private synchronized SavedDataStore getSavedDataStore() {
        return SingletonStoryData.getInstance().getSavedDataStore();
    }
}
