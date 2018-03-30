package com.example.android_dev.qrtest.db;

import java.util.List;

/**
 * Created by user on 30-Mar-18.
 */

public interface IGoalsDataStore {
    void update(List<Integer> ids);

    List<Integer> getAll();

    void cleanAll();
}
