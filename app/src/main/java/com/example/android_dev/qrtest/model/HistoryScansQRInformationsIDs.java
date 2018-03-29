package com.example.android_dev.qrtest.model;

import com.google.gson.annotations.SerializedName;


public class HistoryScansQRInformationsIDs {
    @SerializedName("qrInformationID")
    int qrInformationID;

    @SerializedName("isShortInfo")
    boolean isShortInfo;

    public HistoryScansQRInformationsIDs(int qrInformationID, boolean isShortInfo) {
        this.qrInformationID = qrInformationID;
        this.isShortInfo = isShortInfo;
    }

    public int getQrInformationID() {
        return qrInformationID;
    }

    public void setQrInformationID(int qrInformationID) {
        this.qrInformationID = qrInformationID;
    }

    public boolean isShortInfo() {
        return isShortInfo;
    }

    public void setShortInfo(boolean shortInfo) {
        isShortInfo = shortInfo;
    }
}
