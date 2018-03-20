package com.example.android_dev.qrtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by android_dev on 19.03.2018.
 */

public class Story implements Parcelable {
    public Story() {
    }

    int color;
    int about;
    String name;
    StoryMediaPath media;
    ArrayList<Actor> actors;

    protected Story(Parcel in) {
        color = in.readInt();
        about = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(color);
        dest.writeInt(about);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAbout() {
        return about;
    }

    public void setAbout(int about) {
        this.about = about;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
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
}
