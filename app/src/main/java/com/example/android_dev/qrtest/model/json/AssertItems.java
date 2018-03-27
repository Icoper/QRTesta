package com.example.android_dev.qrtest.model.json;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssertItems {
    @SerializedName("id")
    String id;
    @SerializedName("text")
    String text;
    @SerializedName("resource")
    List<Resource> resources;

    public AssertItems(String id, String text, List<Resource> resources) {
        this.id = id;
        this.text = text;
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public class Resource {
        @SerializedName("name")
        String name;
        @SerializedName("type")
        String type;

        public Resource(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
