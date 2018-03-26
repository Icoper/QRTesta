package com.example.android_dev.qrtest.model;

import java.util.List;


public class StoryMediaPath {

    private List<String> images;
    private List<String> video;
    private List<String> audio;

    public StoryMediaPath() {
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideo() {
        return video;
    }

    public void setVideo(List<String> video) {
        this.video = video;
    }

    public List<String> getAudio() {
        return audio;
    }

    public void setAudio(List<String> audio) {
        this.audio = audio;
    }
}
