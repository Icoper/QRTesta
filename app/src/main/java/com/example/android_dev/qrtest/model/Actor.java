package com.example.android_dev.qrtest.model;

/**
 * Created by icoper on 20.03.2018.
 */

public class Actor {
    String name;
    String id;

    public Actor(String name, String id) {
        this.name = name;
        this.id = id;
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
}
