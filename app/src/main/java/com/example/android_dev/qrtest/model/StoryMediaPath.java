package com.example.android_dev.qrtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;


public class StoryMediaPath implements Parcelable {
    /*
       key - file name
       value - file id
        */
    private HashMap<String, Integer> images;
    private HashMap<String, Integer> video;
    private HashMap<String, Integer> audio;

    public StoryMediaPath() {
    }

    protected StoryMediaPath(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoryMediaPath> CREATOR = new Creator<StoryMediaPath>() {
        @Override
        public StoryMediaPath createFromParcel(Parcel in) {
            return new StoryMediaPath(in);
        }

        @Override
        public StoryMediaPath[] newArray(int size) {
            return new StoryMediaPath[size];
        }
    };

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
