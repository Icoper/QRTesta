package com.example.android_dev.qrtest.db.historyScan;

import java.util.List;

public interface IHistoryScanDataStore {
    void update(int ids);

    List<Integer> getAll();

    void setList(List<Integer> list);

}