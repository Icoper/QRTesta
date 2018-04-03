package com.example.android_dev.qrtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QrInformation {

    @SerializedName("code")
    private String code;
    @SerializedName("previewImage")
    private int previewImage;
    @SerializedName("timer")
    private String timer;
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("id")
    private int id;
    @SerializedName("qrData")
    private List<QrData> qrDataList;

    public QrInformation(String code, int previewImage, String timer, String itemName, int id, List<QrData> qrDataList) {
        this.code = code;
        this.previewImage = previewImage;
        this.timer = timer;
        this.itemName = itemName;
        this.id = id;
        this.qrDataList = qrDataList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(int previewImage) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<QrData> getQrDataList() {
        return qrDataList;
    }

    public void setQrDataList(List<QrData> qrDataList) {
        this.qrDataList = qrDataList;
    }

    public class QrData {
        @SerializedName("roleID")
        int roleId;
        @SerializedName("qrItem")
        QrItem qrItemList;

        public QrData(int roleId, QrItem qrItemList) {
            this.roleId = roleId;
            this.qrItemList = qrItemList;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public QrItem getQrItemList() {
            return qrItemList;
        }

        public void setQrItemList(QrItem qrItemList) {
            this.qrItemList = qrItemList;
        }

        public class QrItem {
            @SerializedName("detailInfoAssetID")
            List<Integer> detailInfoAssetIDList;
            @SerializedName("goalDetailAssetID")
            List<Integer> goalDetailAssetIDList;
            @SerializedName("goalShortAssetID")
            List<Integer> goalShortAssetIDList;
            @SerializedName("shortInfoAssetID")
            List<Integer> shortInfoAssetIDList;
            @SerializedName("id")
            int id;

            public QrItem(List<Integer> detailInfoAssetIDList, List<Integer> goalDetailAssetIDList, List<Integer> goalShortAssetIDList, List<Integer> shortInfoAssetIDList, int id) {
                this.detailInfoAssetIDList = detailInfoAssetIDList;
                this.goalDetailAssetIDList = goalDetailAssetIDList;
                this.goalShortAssetIDList = goalShortAssetIDList;
                this.shortInfoAssetIDList = shortInfoAssetIDList;
                this.id = id;
            }

            public List<Integer> getDetailInfoAssetIDList() {
                return detailInfoAssetIDList;
            }

            public void setDetailInfoAssetIDList(List<Integer> detailInfoAssetIDList) {
                this.detailInfoAssetIDList = detailInfoAssetIDList;
            }

            public List<Integer> getGoalDetailAssetIDList() {
                return goalDetailAssetIDList;
            }

            public void setGoalDetailAssetIDList(List<Integer> goalDetailAssetIDList) {
                this.goalDetailAssetIDList = goalDetailAssetIDList;
            }

            public List<Integer> getGoalShortAssetIDList() {
                return goalShortAssetIDList;
            }

            public void setGoalShortAssetIDList(List<Integer> goalShortAssetIDList) {
                this.goalShortAssetIDList = goalShortAssetIDList;
            }

            public List<Integer> getShortInfoAssetIDList() {
                return shortInfoAssetIDList;
            }

            public void setShortInfoAssetIDList(List<Integer> shortInfoAssetIDList) {
                this.shortInfoAssetIDList = shortInfoAssetIDList;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
