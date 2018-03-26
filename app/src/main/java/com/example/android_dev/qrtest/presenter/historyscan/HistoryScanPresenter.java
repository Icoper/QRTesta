package com.example.android_dev.qrtest.presenter.historyscan;

import android.content.SharedPreferences;

import com.example.android_dev.qrtest.db.IMemoryStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SPScanStoryRepository;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
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

    private ArrayList<Story> prepareDataToSend(List<String> spData) {
        iStoryRepository = new InMemoryStoryRepository();
        ArrayList<Story> storyList = iStoryRepository.getStoriesList();
        ArrayList<Story> scanStoryList = new ArrayList<>();

        for (String actorId : spData) {
            for (Story story : storyList) {
                List<Actor> actors = story.getActors();
                for (Actor actor : actors) {
                    if (actor.getId().equals(actorId)) {
                        scanStoryList.add(story);
                    }
                }
            }
        }
        return scanStoryList;
    }

}
