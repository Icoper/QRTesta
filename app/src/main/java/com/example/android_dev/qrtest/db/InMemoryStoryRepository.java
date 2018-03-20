package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.Story;

import java.util.ArrayList;


public class InMemoryStoryRepository implements IStoryRepository {
    @Override
    public ArrayList<Story> getStoriesList() {
        return SingletonMD.getInstance().getStories();
    }
}
