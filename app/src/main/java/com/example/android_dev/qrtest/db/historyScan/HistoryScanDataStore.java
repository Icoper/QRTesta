package com.example.android_dev.qrtest.db.historyScan;

import com.example.android_dev.qrtest.db.SavedDataStore;
import com.example.android_dev.qrtest.db.SingletonStoryData;

import java.util.List;


public class HistoryScanDataStore implements IHistoryScanDataStore {
    private List<Integer> resIds;

    @Override
    public void update(int id) {
        resIds = getList();
        resIds.add(id);
        getSavedDataStore().saveScanQrCodes(resIds);
    }

    @Override
    public List<Integer> getAll() {
        return getList();
    }

    private SavedDataStore getSavedDataStore() {
        return SingletonStoryData.getInstance().getSavedDataStore();
    }

    @Override
    public void setList(List<Integer> list) {
        this.resIds = list;
        getSavedDataStore().saveScanQrCodes(resIds);
    }

    private List<Integer> getList() {
        if (resIds == null) {
            resIds = getSavedDataStore().loadScanQrCodes();
        }
        return resIds;
    }
}
