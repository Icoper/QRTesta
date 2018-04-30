package com.example.android_dev.qrtest.db.goals;

import java.util.List;


public interface IGoalsDataStore {
    void updateNewGoals(List<Integer> ids);

    List<Integer> getAllGoals();

    List<Integer> getNewGoals();

    void cleanNewGoals();

}
