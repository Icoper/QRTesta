package com.example.android_dev.qrtest.presenter.historyscan;

import android.content.SharedPreferences;

import com.example.android_dev.qrtest.db.IMemoryStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SPScanStoryRepository;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.util.IHistoryScanFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryScanPresenter implements IHistoryScanPresenter {
    private IMemoryStoryRepository iStoryRepository;
    private SPScanStoryRepository scanStoryRepository;
    private IHistoryScanFragment iHistoryScanFragment;

    public HistoryScanPresenter(IHistoryScanFragment iHistoryScanFragment, SharedPreferences sharedPreferences) {
        this.iHistoryScanFragment = iHistoryScanFragment;
        scanStoryRepository = new SPScanStoryRepository(sharedPreferences);
    }

    @Override
    public void getScannedStoryList() {
        List<String> scanSPData = scanStoryRepository.getAllScannedId();
        if (scanSPData.isEmpty()) {
            iHistoryScanFragment.showMsg("The list is empty");
        } else {
            iHistoryScanFragment.showGridView(prepareDataToSend(scanSPData));
        }
    }

    private ArrayList<JsonStory> prepareDataToSend(List<String> spData) {
        iStoryRepository = new InMemoryStoryRepository();
        ArrayList<JsonStory> storyList = iStoryRepository.getStoriesList();
        ArrayList<JsonStory> scanStoryList = new ArrayList<>();

        for (String storyId : spData) {
            for (JsonStory story : storyList) {
                if (story.getQrInformations().get(0).getCode().equals(storyId)) {
                    scanStoryList.add(story);
                }
            }
        }
        return scanStoryList;
    }

}
