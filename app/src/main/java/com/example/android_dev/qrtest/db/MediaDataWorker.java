package com.example.android_dev.qrtest.db;

import android.util.Log;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.model.StoryMediaPath;

import java.util.ArrayList;

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

        ArrayList<Integer> redHoodImgStore = new ArrayList<>();
        redHoodImgStore.add(R.raw.preview_redhood);
        redHoodImgStore.add(R.raw.redhood_img);
        redHoodPath.setImages(redHoodImgStore);

        ArrayList<Integer> redHoodAudioStore = new ArrayList<>();
        redHoodAudioStore.add(R.raw.redhood_audio);
        redHoodPath.setAudio(redHoodAudioStore);

        ArrayList<Integer> redHoodVideo = new ArrayList<>();
        redHoodVideo.add(R.raw.redhood_video);
        redHoodPath.setVideo(redHoodVideo);

        Actor actorRedHood = new Actor("Красная Шапочка", "40", R.raw.about_redhood_img, R.raw.about_redhood_text);
        Actor actorWolf = new Actor("Волк", "45", R.raw.about_wolf_img, R.raw.about_wolf_text);
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


        ArrayList<Integer> repkaImgStore = new ArrayList<>();
        repkaImgStore.add(R.raw.preview_repka);
        repkaImgStore.add(R.raw.repka_img);
        repkaPath.setImages(repkaImgStore);

        ArrayList<Integer> repkaAudioStore = new ArrayList<>();
        repkaAudioStore.add(R.raw.repka_audio);
        repkaPath.setAudio(repkaAudioStore);

        ArrayList<Integer> repkaVideo = new ArrayList<>();
        repkaVideo.add(R.raw.repka_video);
        repkaPath.setVideo(repkaVideo);

        Actor actorRepka = new Actor("Репка", "50", R.raw.about_repka_img, R.raw.about_repka_text);
        Actor actorGrMather = new Actor("Бабушка", "55", R.raw.about_gr_mather_img, R.raw.about_gr_mather_text);
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
