package com.example.android_dev.qrtest.model;

import java.util.List;

public class Story {
    public Story() {
    }
    int color;
    String about;
    String name;
    StoryMediaPath media;
    List<Actor> actors;

    public Story(int color, String about, String name, StoryMediaPath media, List<Actor> actors) {
        this.color = color;
        this.about = about;
        this.name = name;
        this.media = media;
        this.actors = actors;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoryMediaPath getMedia() {
        return media;
    }

    public void setMedia(StoryMediaPath media) {
        this.media = media;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
