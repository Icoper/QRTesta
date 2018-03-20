package com.example.android_dev.qrtest.model;

import java.util.HashMap;


public class StoryMediaPath {
    /*
       key - file name
       value - file id
        */
    private HashMap<String, Integer> images;
    private HashMap<String, Integer> video;
    private HashMap<String, Integer> audio;

    public StoryMediaPath() {
    }

    public HashMap<String, Integer> getImages() {
        return images;
    }

    public void setImages(HashMap<String, Integer> images) {
        this.images = images;
    }

    public HashMap<String, Integer> getVideo() {
        return video;
    }

    public void setVideo(HashMap<String, Integer> video) {
        this.video = video;
    }

    public HashMap<String, Integer> getAudio() {
        return audio;
    }

    public void setAudio(HashMap<String, Integer> audio) {
        this.audio = audio;
    }
}
