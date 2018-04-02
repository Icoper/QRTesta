package com.example.android_dev.qrtest.db;

import java.util.List;


public interface IGoalsDataStore {
    void update(List<Integer> ids);

    List<Integer> getAll();

}
