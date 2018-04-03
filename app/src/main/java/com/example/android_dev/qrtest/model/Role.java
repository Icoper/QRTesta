package com.example.android_dev.qrtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Role {
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;
    @SerializedName("informationAssetID")
    private List<Integer> informationAssertIDList;
    @SerializedName("id")
    private int id;

    public Role(String name, String code, List<Integer> informationAssertIDList, int id) {
        this.name = name;
        this.code = code;
        this.informationAssertIDList = informationAssertIDList;
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

    public List<Integer> getInformationAssertIDList() {
        return informationAssertIDList;
    }

    public void setInformationAssertIDList(List<Integer> informationAssertIDList) {
        this.informationAssertIDList = informationAssertIDList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
