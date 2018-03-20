package com.example.android_dev.qrtest.db;

import android.util.Log;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.model.StoryMediaPath;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaDataWorker {

    private static final String LOG_TAG = "MediaDataWorker";
    private Story redHoodStory;
    private Story repkaStory;
    private ArrayList<Story> stories;

    public MediaDataWorker() {
    }

    private ArrayList<Story> initializeMedia() {
        Log.d(LOG_TAG, "initializeMedia is called");
        redHoodStory = new Story();
        repkaStory = new Story();
        StoryMediaPath redHoodPath = new StoryMediaPath();

        redHoodStory.setName("Красная Шапочка");
        redHoodStory.setAbout(R.raw.text_redhood);
        redHoodStory.setColor(R.color.colorRed);

        HashMap<String, Integer> redHoodImgStore = new HashMap<>();
        redHoodImgStore.put("preview", R.raw.preview_redhood);
        redHoodImgStore.put("img", R.raw.redhood_img);
        redHoodPath.setImages(redHoodImgStore);

        HashMap<String, Integer> redHoodAudioStore = new HashMap<>();
        redHoodAudioStore.put("audio", R.raw.redhood_audio);
        redHoodPath.setAudio(redHoodAudioStore);

        HashMap<String, Integer> redHoodVideo = new HashMap<>();
        redHoodVideo.put("video", R.raw.redhood_video);
        redHoodPath.setVideo(redHoodVideo);

        Actor actorRedHood = new Actor("Красная Шапочка", "40");
        Actor actorWolf = new Actor("Волк", "45");
        ArrayList<Actor> redHoodActors = new ArrayList<>();
        redHoodActors.add(actorRedHood);
        redHoodActors.add(actorWolf);
        redHoodStory.setActors(redHoodActors);
        redHoodStory.setMedia(redHoodPath);

        // repka setup
        StoryMediaPath repkaPath = new StoryMediaPath();

        repkaStory.setName("Репка");
        repkaStory.setAbout(R.raw.text_repka);
        repkaStory.setColor(R.color.colorYellow);


        HashMap<String, Integer> repkaImgStore = new HashMap<>();
        repkaImgStore.put("preview", R.raw.preview_repka);
        repkaImgStore.put("img", R.raw.repka_img);
        repkaPath.setImages(repkaImgStore);

        HashMap<String, Integer> repkaAudioStore = new HashMap<>();
        repkaAudioStore.put("audio", R.raw.repka_audio);
        repkaPath.setAudio(repkaAudioStore);

        HashMap<String, Integer> repkaVideo = new HashMap<>();
        repkaVideo.put("video", R.raw.repka_video);
        repkaPath.setVideo(repkaVideo);

        Actor actorRepka = new Actor("Репка", "50");
        Actor actorGrMather = new Actor("Бабушка", "55");
        ArrayList<Actor> repkaActors = new ArrayList<>();
        repkaActors.add(actorRepka);
        repkaActors.add(actorGrMather);
        repkaStory.setActors(repkaActors);
        repkaStory.setMedia(repkaPath);

        stories = new ArrayList<>();
        stories.add(repkaStory);
        stories.add(redHoodStory);

        return stories;
    }

    public ArrayList<Story> getStoryData() {
        if (stories == null || stories.isEmpty()) {
            stories = initializeMedia();
        }
        return stories;
    }
}
