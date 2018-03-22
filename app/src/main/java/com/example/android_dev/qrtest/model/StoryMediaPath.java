package com.example.android_dev.qrtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class StoryMediaPath implements Parcelable {

    private ArrayList<Integer> images;
    private ArrayList<Integer> video;
    private ArrayList<Integer> audio;

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

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setImages(ArrayList<Integer> images) {
        this.images = images;
    }

    public ArrayList<Integer> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<Integer> video) {
        this.video = video;
    }

    public ArrayList<Integer> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<Integer> audio) {
        this.audio = audio;
    }
}
