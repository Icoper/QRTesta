package com.example.android_dev.qrtest.db;


import com.example.android_dev.qrtest.model.Story;

import java.util.ArrayList;

public interface IMemoryStoryRepository {

    ArrayList<Story> getStoriesList();

    void setActorId(String id);

    String getActorId();

    Story getSelectedStory();
}
