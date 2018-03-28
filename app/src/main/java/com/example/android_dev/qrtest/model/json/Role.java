package com.example.android_dev.qrtest.model.json;

import com.google.gson.annotations.SerializedName;


public class Role {
    @SerializedName("name")
    String name;
    @SerializedName("code")
    String code;
    @SerializedName("informationAssertID")
    String informationAssertID;
    @SerializedName("id")
    int id;

    public Role(String name, String code, String informationAssertID, int id) {
        this.name = name;
        this.code = code;
        this.informationAssertID = informationAssertID;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInformationAssertID() {
        return informationAssertID;
    }

    public void setInformationAssertID(String informationAssertID) {
        this.informationAssertID = informationAssertID;
    }
}
