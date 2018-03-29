package com.example.android_dev.qrtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class JsonStory {
    @SerializedName("previewImageName")
    String previewImg;
    @SerializedName("previewText")
    String previewText;
    @SerializedName("resFolderName")
    String resFolderName;
    @SerializedName("color")
    String color;
    @SerializedName("backgoundColor")
    String backgroundColor;
    @SerializedName("tabbarColor")
    String tabbarColor;
    @SerializedName("historyAssetTypesID")
    List<Integer> historyAssetTypesID;
    @SerializedName("qrInformations")
    List<QrInformation> qrInformationList;
    @SerializedName("roles")
    List<Role> roleList;
    @SerializedName("historyScansQRInformationsIDs")
    List<HistoryScansQRInformationsIDs> historyScansQRInformationsIDList;
    @SerializedName("goalAssetIDs")
    List<Integer> goalAssetIDList;
    @SerializedName("assetTypes")
    List<AssetTypes> assetTypeList;

    public JsonStory() {
    }

    public JsonStory(String previewImg, String previewText, String resFolderName, String color, String backgroundColor, String tabbarColor, List<Integer> historyAssetTypesID, List<QrInformation> qrInformationList, List<Role> roleList, List<HistoryScansQRInformationsIDs> historyScansQRInformationsIDList, List<Integer> goalAssetIDList, List<AssetTypes> assetTypeList) {
        this.previewImg = previewImg;
        this.previewText = previewText;
        this.resFolderName = resFolderName;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.tabbarColor = tabbarColor;
        this.historyAssetTypesID = historyAssetTypesID;
        this.qrInformationList = qrInformationList;
        this.roleList = roleList;
        this.historyScansQRInformationsIDList = historyScansQRInformationsIDList;
        this.goalAssetIDList = goalAssetIDList;
        this.assetTypeList = assetTypeList;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getResFolderName() {
        return resFolderName;
    }

    public void setResFolderName(String resFolderName) {
        this.resFolderName = resFolderName;
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

    public List<Integer> getHistoryAssetTypesID() {
        return historyAssetTypesID;
    }

    public void setHistoryAssetTypesID(List<Integer> historyAssetTypesID) {
        this.historyAssetTypesID = historyAssetTypesID;
    }

    public List<QrInformation> getQrInformationList() {
        return qrInformationList;
    }

    public void setQrInformationList(List<QrInformation> qrInformationList) {
        this.qrInformationList = qrInformationList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<HistoryScansQRInformationsIDs> getHistoryScansQRInformationsIDList() {
        return historyScansQRInformationsIDList;
    }

    public void setHistoryScansQRInformationsIDList(List<HistoryScansQRInformationsIDs> historyScansQRInformationsIDList) {
        this.historyScansQRInformationsIDList = historyScansQRInformationsIDList;
    }

    public List<Integer> getGoalAssetIDList() {
        return goalAssetIDList;
    }

    public void setGoalAssetIDList(List<Integer> goalAssetIDList) {
        this.goalAssetIDList = goalAssetIDList;
    }

    public List<AssetTypes> getAssetTypeList() {
        return assetTypeList;
    }

    public void setAssetTypeList(List<AssetTypes> assetTypeList) {
        this.assetTypeList = assetTypeList;
    }
}
