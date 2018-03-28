package com.example.android_dev.qrtest.model.json;

import com.google.gson.annotations.SerializedName;


public class HistoryScansQRInformationsIDs {
    @SerializedName("qrInformationID")
    String qrInformationID;

    @SerializedName("isShortInfo")
    String isShortInfo;

    public HistoryScansQRInformationsIDs(String qrInformationID, String isShortInfo) {
        this.qrInformationID = qrInformationID;
        this.isShortInfo = isShortInfo;
    }

    public String getQrInformationID() {
        return qrInformationID;
    }

    public void setQrInformationID(String qrInformationID) {
        this.qrInformationID = qrInformationID;
    }

    public String getIsShortInfo() {
        return isShortInfo;
    }

    public void setIsShortInfo(String isShortInfo) {
        this.isShortInfo = isShortInfo;
    }
}
