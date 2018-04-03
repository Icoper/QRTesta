package com.example.android_dev.qrtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class JsonStory implements IStory {
    @SerializedName("previewImageName")
    private String previewImg;
    @SerializedName("previewText")
    private String previewText;
    @SerializedName("resFolderName")
    private String resFolderName;
    @SerializedName("color")
    private String color;
    @SerializedName("backgoundColor")
    private String backgroundColor;
    @SerializedName("tabbarColor")
    private String tabbarColor;
    @SerializedName("historyAssetTypesID")
    private List<Integer> historyAssetTypesID;
    @SerializedName("qrInformations")
    private List<QrInformation> qrInformationList;
    @SerializedName("roles")
    private List<Role> roleList;
    @SerializedName("historyScansQRInformationsIDs")
    List<HistoryScansQRInformationIDs> historyScansQRInformationsIDList;
    @SerializedName("goalAssetIDs")
    List<Integer> goalAssetIDList;
    @SerializedName("assetTypes")
    List<AssetTypes> assetTypeList;

    public JsonStory() {
    }

    public JsonStory(String previewImg, String previewText, String resFolderName, String color, String backgroundColor, String tabbarColor, List<Integer> historyAssetTypesID, List<QrInformation> qrInformationList, List<Role> roleList, List<HistoryScansQRInformationIDs> historyScansQRInformationsIDList, List<Integer> goalAssetIDList, List<AssetTypes> assetTypeList) {
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

    @Override
    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    @Override
    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    @Override
    public String getResFolderName() {
        return resFolderName;
    }

    public void setResFolderName(String resFolderName) {
        this.resFolderName = resFolderName;
    }

    @Override
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String getTabbarColor() {
        return tabbarColor;
    }

    public void setTabbarColor(String tabbarColor) {
        this.tabbarColor = tabbarColor;
    }

    @Override
    public List<Integer> getHistoryAssetTypesID() {
        return historyAssetTypesID;
    }

    public void setHistoryAssetTypesID(List<Integer> historyAssetTypesID) {
        this.historyAssetTypesID = historyAssetTypesID;
    }

    @Override
    public List<QrInformation> getQrInformationList() {
        return qrInformationList;
    }

    public void setQrInformationList(List<QrInformation> qrInformationList) {
        this.qrInformationList = qrInformationList;
    }

    @Override
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public List<HistoryScansQRInformationIDs> getHistoryScansQRInformationsIDList() {
        return historyScansQRInformationsIDList;
    }

    public void setHistoryScansQRInformationsIDList(List<HistoryScansQRInformationIDs> historyScansQRInformationsIDList) {
        this.historyScansQRInformationsIDList = historyScansQRInformationsIDList;
    }

    @Override
    public List<Integer> getGoalAssetIDList() {
        return goalAssetIDList;
    }

    public void setGoalAssetIDList(List<Integer> goalAssetIDList) {
        this.goalAssetIDList = goalAssetIDList;
    }

    @Override
    public List<AssetTypes> getAssetTypeList() {
        return assetTypeList;
    }

    public void setAssetTypeList(List<AssetTypes> assetTypeList) {
        this.assetTypeList = assetTypeList;
    }
}
