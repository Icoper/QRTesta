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
        getSavedDataStore().saveKnowGoals(resIds);
    }

    @Override
    public List<Integer> getAll() {
        return getList();
    }

    @Override
    public void setList(List<Integer> list) {
        this.resIds = list;
        getSavedDataStore().saveKnowGoals(list);
    }

    private List<Integer> getList() {
        if (resIds == null) {
            try {
                if (getSavedDataStore().loadKnowGoals() != null) {
                    resIds = getSavedDataStore().loadKnowGoals();
                } else resIds = new ArrayList<>();
            } catch (NullPointerException e) {
                e.printStackTrace();
                resIds = new ArrayList<>();
            }
        }
        return resIds;
    }

    private SavedDataStore getSavedDataStore() {
        return SingletonStoryData.getInstance().getSavedDataStore();
    }

}
