package com.example.android_dev.qrtest.db;

import android.os.Environment;
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
        redHoodStory.setAbout(getFilePathById("text_redhood.txt"));
        redHoodStory.setColor(R.color.colorRed);

        ArrayList<String> redHoodImgStore = new ArrayList<>();
        redHoodImgStore.add(getFilePathById("preview_redhood.png"));
        redHoodImgStore.add(getFilePathById("redhood_img.png"));
        redHoodPath.setImages(redHoodImgStore);

        ArrayList<String> redHoodAudioStore = new ArrayList<>();
        redHoodAudioStore.add(getFilePathById("redhood_audio.mp3"));
        redHoodPath.setAudio(redHoodAudioStore);

        ArrayList<String> redHoodVideo = new ArrayList<>();
        redHoodVideo.add(getFilePathById("redhood_video.mp4"));
        redHoodPath.setVideo(redHoodVideo);

        Actor actorRedHood = new Actor("Красная Шапочка",
                "40",
                getFilePathById("about_redhood_img.png"),
                getFilePathById("about_redhood_text.txt"));

        Actor actorWolf = new Actor("Волк",
                "45",
                getFilePathById("about_wolf_img.png"),
                getFilePathById("about_wolf_text.txt"));

        ArrayList<Actor> redHoodActors = new ArrayList<>();
        redHoodActors.add(actorRedHood);
        redHoodActors.add(actorWolf);
        redHoodStory.setActors(redHoodActors);
        redHoodStory.setMedia(redHoodPath);

        // repka setup
        StoryMediaPath repkaPath = new StoryMediaPath();

        repkaStory.setName("Репка");
        repkaStory.setAbout(getFilePathById("text_repka.txt"));
        repkaStory.setColor(R.color.colorYellow);


        ArrayList<String> repkaImgStore = new ArrayList<>();
        repkaImgStore.add(getFilePathById("preview_repka.png"));
        repkaImgStore.add(getFilePathById("repka_img.png"));
        repkaPath.setImages(repkaImgStore);

        ArrayList<String> repkaAudioStore = new ArrayList<>();
        repkaAudioStore.add(getFilePathById("repka_audio.mp3"));
        repkaPath.setAudio(repkaAudioStore);

        ArrayList<String> repkaVideo = new ArrayList<>();
        repkaVideo.add(getFilePathById("repka_video.mp4"));
        repkaPath.setVideo(repkaVideo);

        Actor actorRepka = new Actor("Репка",
                "50",
                getFilePathById("about_repka_img.png"),
                getFilePathById("about_repka_text.txt"));
        Actor actorGrMather = new Actor("Бабушка",
                "55",
                getFilePathById("about_gr_mather_img.png"),
                getFilePathById("about_gr_mather_text.txt"));

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

    private String getFilePathById(String id) {
        return Environment.getExternalStorageDirectory() + "/qrmedia/" + id;
    }
}
