package com.example.android_dev.qrtest.presenter.historyscan;

import android.content.Context;

import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.util.IHistoryScanFragment;
import com.example.android_dev.qrtest.util.SPHelper;

import java.util.ArrayList;

public class HistoryScanPresenter implements IHistoryScanPresenter {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private Context context;
    private IHistoryScanFragment iHistoryScanFragment;

    public HistoryScanPresenter(Context context, IHistoryScanFragment iHistoryScanFragment) {
        this.context = context;
        this.iHistoryScanFragment = iHistoryScanFragment;
    }

    @Override
    public void getScannedStoryList() {
        String scanSPData = SPHelper.getScanHistory(context);
        if (scanSPData.isEmpty()) {
            iHistoryScanFragment.showMsg("The list is empty");
        } else {
            iHistoryScanFragment.showGridView(prepareDataToSend(scanSPData));
        }
    }

    private ArrayList<Story> prepareDataToSend(String spData) {
        String[] spDataToArray = spData.split("/");
        inMemoryStoryRepository = new InMemoryStoryRepository();
        ArrayList<Story> storyList = inMemoryStoryRepository.getStoriesList();
        ArrayList<Story> scanStoryList = new ArrayList<>();

        for (String actorId : spDataToArray) {
            for (Story story : storyList) {
                ArrayList<Actor> actors = story.getActors();
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
