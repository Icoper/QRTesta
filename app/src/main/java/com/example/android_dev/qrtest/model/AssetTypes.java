package com.example.android_dev.qrtest.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssetTypes implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String fileName;
    @SerializedName("type")
    private String fileType;

    public AssetTypes(int id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
