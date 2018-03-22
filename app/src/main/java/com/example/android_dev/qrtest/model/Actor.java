package com.example.android_dev.qrtest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Actor implements Parcelable {
    String name;
    String id;
    int imgRes;
    int aboutRes;

    public Actor(String name, String id, int imgRes, int aboutRes) {
        this.name = name;
        this.id = id;
        this.imgRes = imgRes;
        this.aboutRes = aboutRes;
    }

    protected Actor(Parcel in) {
        name = in.readString();
        id = in.readString();
        imgRes = in.readInt();
        aboutRes = in.readInt();
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

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getAboutRes() {
        return aboutRes;
    }

    public void setAboutRes(int aboutRes) {
        this.aboutRes = aboutRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeInt(imgRes);
        parcel.writeInt(aboutRes);
    }
}
