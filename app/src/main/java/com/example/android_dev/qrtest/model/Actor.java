package com.example.android_dev.qrtest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by icoper on 20.03.2018.
 */

public class Actor implements Parcelable {
    String name;
    String id;

    public Actor(String name, String id) {
        this.name = name;
        this.id = id;
    }

    protected Actor(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
