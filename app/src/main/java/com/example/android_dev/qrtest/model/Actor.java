package com.example.android_dev.qrtest.model;


public class Actor {
    String name;
    String id;
    String imgRes;
    String aboutRes;

    public Actor(String name, String id, String imgRes, String aboutRes) {
        this.name = name;
        this.id = id;
        this.imgRes = imgRes;
        this.aboutRes = aboutRes;
    }

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

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public String getAboutRes() {
        return aboutRes;
    }

    public void setAboutRes(String aboutRes) {
        this.aboutRes = aboutRes;
    }
}
