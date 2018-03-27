package com.example.android_dev.qrtest.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QrInformation {

    @SerializedName("code")
    String code;
    @SerializedName("previewImage")
    String previewImage;
    @SerializedName("timer")
    String timer;
    @SerializedName("itemName")
    String itemName;
    @SerializedName("id")
    String id;
    @SerializedName("qrData")
    List<QrData> qrData;


    public QrInformation(String code, String previewImage, String timer, String itemName, String id, List<QrData> qrData) {
        this.code = code;
        this.previewImage = previewImage;
        this.timer = timer;
        this.itemName = itemName;
        this.id = id;
        this.qrData = qrData;
    }

    public class QrData {
        @SerializedName("role")
        String role;
        @SerializedName("qrItem")
        QrItem qrItems;

        public QrData(String role, QrItem qrItems) {
            this.role = role;
            this.qrItems = qrItems;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public QrItem getQrItems() {
            return qrItems;
        }

        public void setQrItems(QrItem qrItems) {
            this.qrItems = qrItems;
        }

        public class QrItem {
            @SerializedName("detailInfoAssertID")
            String detailInfoAssertID;
            @SerializedName("shortInfoAssertID")
            String shortInfoAssertID;
            @SerializedName("id")
            String id;

            public QrItem(String detailInfoAssertID, String shortInfoAssertID, String id) {
                this.detailInfoAssertID = detailInfoAssertID;
                this.shortInfoAssertID = shortInfoAssertID;
                this.id = id;
            }

            public String getDetailInfoAssertID() {
                return detailInfoAssertID;
            }

            public void setDetailInfoAssertID(String detailInfoAssertID) {
                this.detailInfoAssertID = detailInfoAssertID;
            }

            public String getShortInfoAssertID() {
                return shortInfoAssertID;
            }

            public void setShortInfoAssertID(String shortInfoAssertID) {
                this.shortInfoAssertID = shortInfoAssertID;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<QrData> getQrData() {
        return qrData;
    }

    public void setQrData(List<QrData> qrData) {
        this.qrData = qrData;
    }
}
