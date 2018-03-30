package com.example.android_dev.qrtest.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30-Mar-18.
 */

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

    @Override
    public void cleanAll() {
        resIds = null;
    }

    private List<Integer> getList() {
        if (resIds == null) {
            resIds = new ArrayList<Integer>();
        }
        return resIds;
    }
}
