package com.example.android_dev.qrtest.db;

import java.util.List;


public interface IGoalsDataStore {
    void update(List<Integer> ids);

    List<Integer> getAll();

    void setList(List<Integer> list);

    void saveNewGoals(List<Integer> list);

    List<Integer> loadNewGoals();

    void cleanNewGoals();

    void setNotificationCount(int count);

    int getNotificationCount();
}
