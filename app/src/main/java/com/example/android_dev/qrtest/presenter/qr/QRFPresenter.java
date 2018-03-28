package com.example.android_dev.qrtest.presenter.qr;

import android.content.SharedPreferences;

import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SPScanStoryRepository;
import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.QrInformation;
import com.example.android_dev.qrtest.model.json.Role;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IQRFragment;

import java.util.ArrayList;
import java.util.List;

public class QRFPresenter implements IQRPresenter {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private SPScanStoryRepository scanStoryRepository;
    private IQRFragment iqrFragment;
    private SharedPreferences sharedPreferences;
    private AppMediaPlayerPresenter iAppMediaPlayerPresenter;

    public QRFPresenter(SharedPreferences sharedPreferences, IQRFragment iqrFragment) {
        this.iqrFragment = iqrFragment;
        this.sharedPreferences = sharedPreferences;
    }

    private InMemoryStoryRepository getStoryRepo() {
        if (inMemoryStoryRepository == null) {
            inMemoryStoryRepository = new InMemoryStoryRepository();
        }
        return inMemoryStoryRepository;
    }

    @Override
    public void playMediaData(AssertItems.Resource resource) {

        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                getStoryRepo().getSelectedStory().getQrInformations().get(0).getCode() + "/" +
                resource.getName();
        String msg = iAppMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iqrFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.AUDIO_RES)) {
            iqrFragment.startAudioPlayerActivity(filePath);
        }
        iqrFragment.showMsg(msg);
    }

    @Override
    public synchronized void checkCode(String code) {
        scanStoryRepository = new SPScanStoryRepository(sharedPreferences);
        int alertScanMode = GlobalNames.QR_MODE_FIRST_SCAN;

        boolean codeIsValid = false;
        boolean goalIdIsAlreadyUse = false;
        String detailGoalId = "";
        JsonStory jsonStory = getStoryRepo().getSelectedStory();
        Role scannedRole = null;
        List<String> scanStoryArray = scanStoryRepository.getAllScannedId();
        List<String> addedGoals = inMemoryStoryRepository.getAllGoalsIds();

        for (Role role : jsonStory.getRoles()) {
            if (role.getCode().equals(code)) {
                codeIsValid = true;
                scannedRole = role;
                for (String _code : scanStoryArray) {
                    if (_code.equals(code)) {
                        alertScanMode = GlobalNames.QR_MODE_SIMPLE_SCAN;
                        iqrFragment.showMsg("Found");
                        break;
                    }
                }
            }
            if (codeIsValid) {
                break;
            }
        }
        if (!codeIsValid) {
            iqrFragment.showMsg("Not Found");
        } else {
            scanStoryRepository.addNewId(code);

            QrInformation.QrData.QrItem qrItem = null;
            for (QrInformation.QrData qrData : jsonStory.getQrInformations()
                    .get(0).getQrData()) {

                if (qrData.getQrItems().getId().equals(String.valueOf(scannedRole.getId()))) {
                    qrItem = qrData.getQrItems();
                }
            }

            detailGoalId = qrItem.getGoalDetailAssertID();

            for (String id : addedGoals) {
                if (detailGoalId.equals(id)) {
                    goalIdIsAlreadyUse = true;
                }
            }
            changeAlertMode(alertScanMode, GlobalNames.ALERT_MODE_SMALL_INFO, jsonStory);
            if (!goalIdIsAlreadyUse) {
                inMemoryStoryRepository.addNewGoalID(detailGoalId);
                notifyAboutNewGoal();
            }
        }


    }

    private void notifyAboutNewGoal() {
        iqrFragment.sendNotificationMsg("Add new Goal");
    }

    @Override
    public void changeAlertMode(int modeScan, int modeShow, JsonStory jsonStory) {
        String storyResIdFull = "";
        String storyResIdSmall = "";
        String storyResId = "";
        String roleId = jsonStory.getQrInformations().get(0).getId();
        for (QrInformation.QrData qrData : jsonStory.getQrInformations().get(0).getQrData()) {
            if (qrData.getRole().equals(roleId)) {
                storyResIdFull = qrData.getQrItems().getDetailInfoAssertID();
                storyResIdSmall = qrData.getQrItems().getShortInfoAssertID();
            }
        }
        if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {
            storyResId = storyResIdSmall;
        } else if (modeShow == GlobalNames.ALERT_MODE_FULL_INFO) {
            storyResId = storyResIdFull;
        }

        iqrFragment.showAlertDialog(modeScan, storyResId, modeShow);
    }
}
