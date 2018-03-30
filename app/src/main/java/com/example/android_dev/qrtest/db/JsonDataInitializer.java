package com.example.android_dev.qrtest.db;

import com.example.android_dev.qrtest.model.JsonStory;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonDataInitializer {
    private List<JsonStory> stories;

    public JsonDataInitializer() {
    }

    private List<JsonStory> getJsonStories() {
        Gson gson = new Gson();
        JsonStory repkaJson = null;
        JsonStory redhoodJson = null;
        // 1. JSON to Java object, read it from a file.
        try {
            repkaJson = gson.fromJson(new FileReader(GlobalNames.ENVIRONMENT_STORE
                    + "repka/data.json"), JsonStory.class);

            redhoodJson = gson.fromJson(new FileReader(GlobalNames.ENVIRONMENT_STORE
                    + "redhood/data.json"), JsonStory.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<JsonStory> stories = new ArrayList<>();
        stories.add(redhoodJson);
        stories.add(repkaJson);
        return stories;
    }


    public List<JsonStory> getStoryData() {
        if (stories == null || stories.isEmpty()) {
            stories = getJsonStories();
        }
        return stories;
    }
}
