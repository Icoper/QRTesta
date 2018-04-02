package com.example.android_dev.qrtest.model;

import java.util.List;

public interface IStory {
    String getPreviewImg();

    String getPreviewText();

    String getResFolderName();

    String getColor();

    String getBackgroundColor();

    String getTabbarColor();

    List<Integer> getHistoryAssetTypesID();

    List<QrInformation> getQrInformationList();

    List<Role> getRoleList();

    List<HistoryScansQRInformationIDs> getHistoryScansQRInformationsIDList();

    List<Integer> getGoalAssetIDList();

    List<AssetTypes> getAssetTypeList();
}
