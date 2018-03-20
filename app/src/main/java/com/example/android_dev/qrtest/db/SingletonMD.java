package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.Story;

import java.util.ArrayList;


public class SingletonMD {
    private static SingletonMD ourInstance = new SingletonMD();
    private ArrayList<Story> stories;

    public ArrayList<Story> getStories() {
        if (stories == null || stories.isEmpty()) {
            stories = new MediaDataWorker().getStoryData();
        }
        return stories;
    }

    public static SingletonMD getInstance() {
        return ourInstance;
    }

    public SingletonMD() {
    }
}
