package com.example.android_dev.qrtest.db;

import java.util.ArrayList;
import java.util.List;

public class GoalsDataStore implements IGoalsDataStore {
    private List<Integer> resIds;

    @Override
    public void update(List<Integer> ids) {
        List<Integer> filteredNewRes = new ArrayList<>();
        resIds = getAll();
        for (int res : ids) {
            boolean alreadyAdded = false;
            for (int _res : resIds) {
                if (_res == res) {
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                filteredNewRes.add(res);
            }
        }
        resIds.addAll(filteredNewRes);
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

    @Override
    public void cleanAll() {
        resIds = null;
    }
}
