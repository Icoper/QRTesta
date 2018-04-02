package com.example.android_dev.qrtest.db;

import java.util.ArrayList;
import java.util.List;


public class HistoryScanDataStore implements IHistoryScanDataStore {
    private List<Integer> resIds;

    @Override
    public void update(int id) {
        resIds = getList();
        resIds.add(id);
    }

    @Override
    public List<Integer> getAll() {
        return getList();
    }


    private List<Integer> getList() {
        if (resIds == null) {
            resIds = new ArrayList<>();
        }
        return resIds;
    }
}
