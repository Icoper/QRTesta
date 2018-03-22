package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.Story;

import java.util.ArrayList;


public class InMemoryStoryRepository implements IStoryRepository {
    @Override
    public ArrayList<Story> getStoriesList() {
        return SingletonMD.getInstance().getStories();
    }

    @Override
    public void setActorId(String id) {
        SingletonMD.getInstance().setSelectedActorId(id);
    }

    @Override
    public String getActorId() {
        return SingletonMD.getInstance().getSelectedActorId();
    }

    @Override
    public Story getSelectedStory() {
        return SingletonMD.getInstance().getSelectedStory();
    }
}
