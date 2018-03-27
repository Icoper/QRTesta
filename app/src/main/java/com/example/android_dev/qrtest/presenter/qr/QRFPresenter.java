package com.example.android_dev.qrtest.presenter.qr;

import android.content.SharedPreferences;

import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SPScanStoryRepository;
import com.example.android_dev.qrtest.model.json.AssertItems;
import com.example.android_dev.qrtest.model.json.JsonStory;
import com.example.android_dev.qrtest.model.json.QrInformation;
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
    public void checkCode(String code) {
        scanStoryRepository = new SPScanStoryRepository(sharedPreferences);
        int alertMode = GlobalNames.QR_MODE_FIRST_SCAN;
        ArrayList<JsonStory> stories = getStoryRepo().getStoriesList();

        for (JsonStory jsonStory : stories) {
            for (QrInformation qrInformation : jsonStory.getQrInformations()) {
                if (qrInformation.getCode().equals(code)) {
                    List<String> scannStoryArray = scanStoryRepository.getAllScannedId();
                    for (String _code : scannStoryArray) {
                        if (_code.equals(code)) {
                            alertMode = GlobalNames.QR_MODE_SIMPLE_SCAN;
                        }
                    }
                    scanStoryRepository.addNewId(code);
                    iqrFragment.showMsg("Found");
                    changeAlertMode(alertMode, GlobalNames.ALERT_MODE_SMALL_INFO, jsonStory);
                    return;
                }
            }

        }
        iqrFragment.showMsg("Not Found");
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
