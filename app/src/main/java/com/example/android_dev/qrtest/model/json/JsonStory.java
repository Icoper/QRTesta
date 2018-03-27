package com.example.android_dev.qrtest.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class JsonStory {
    @SerializedName("previewImageName")
    String previewImg;
    @SerializedName("color")
    String color;
    @SerializedName("backgoundColor")
    String backgroundColor;
    @SerializedName("tabbarColor")
    String tabbarColor;
    @SerializedName("previewText")
    String name;
    @SerializedName("historyAssertItemsID")
    List<String> assertItemsId;
    @SerializedName("qrInformations")
    List<QrInformation> qrInformations;
    @SerializedName("roles")
    List<Role> roles;
    @SerializedName("historyScansQRInformationsIDs")
    List<HistoryScansQRInformationsIDs> historyScansQRInformationsIDs;
    @SerializedName("assertItems")
    List<AssertItems> assertItems;

    public JsonStory() {
    }

    public JsonStory(String previewImg, String color, String backgroundColor, String tabbarColor, String name,
                     List<String> assertItemsId,
                     List<QrInformation> qrInformations,
                     List<Role> roles,
                     List<HistoryScansQRInformationsIDs> historyScansQRInformationsIDs,
                     List<AssertItems> assertItems) {
        this.previewImg = previewImg;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.tabbarColor = tabbarColor;
        this.name = name;
        this.assertItemsId = assertItemsId;
        this.qrInformations = qrInformations;
        this.roles = roles;
        this.historyScansQRInformationsIDs = historyScansQRInformationsIDs;
        this.assertItems = assertItems;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTabbarColor() {
        return tabbarColor;
    }

    public void setTabbarColor(String tabbarColor) {
        this.tabbarColor = tabbarColor;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAssertItemsId() {
        return assertItemsId;
    }

    public void setAssertItemsId(List<String> assertItemsId) {
        this.assertItemsId = assertItemsId;
    }

    public List<QrInformation> getQrInformations() {
        return qrInformations;
    }

    public void setQrInformations(List<QrInformation> qrInformations) {
        this.qrInformations = qrInformations;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<HistoryScansQRInformationsIDs> getHistoryScansQRInformationsIDs() {
        return historyScansQRInformationsIDs;
    }

    public void setHistoryScansQRInformationsIDs(List<HistoryScansQRInformationsIDs> historyScansQRInformationsIDs) {
        this.historyScansQRInformationsIDs = historyScansQRInformationsIDs;
    }

    public List<AssertItems> getAssertItems() {
        return assertItems;
    }

    public void setAssertItems(List<AssertItems> assertItems) {
        this.assertItems = assertItems;
    }
}
