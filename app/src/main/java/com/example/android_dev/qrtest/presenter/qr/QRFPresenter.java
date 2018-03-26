package com.example.android_dev.qrtest.presenter.qr;

import android.content.SharedPreferences;

import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.SPScanStoryRepository;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.model.StoryMediaPath;
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
    public void playMediaData(String fileType, String filePath) {
        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String msg = iAppMediaPlayerPresenter.processMediaData(fileType, filePath);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iqrFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.VIDEO_RES)) {
            iqrFragment.startAudioPlayerActivity(filePath);
            return;
        }
        iqrFragment.showMsg(msg);
    }

    @Override
    public void checkCode(String code) {
        scanStoryRepository = new SPScanStoryRepository(sharedPreferences);
        int alertMode = GlobalNames.QR_MODE_FIRST_SCAN;
        Story story = getStoryRepo().getSelectedStory();
        for (Actor a : story.getActors()) {
            if (a.getId().equals(code)) {
                List<String> scannStoryArray = scanStoryRepository.getAllScannedId();
                for (String _code : scannStoryArray) {
                    if (_code.equals(code)) {
                        alertMode = GlobalNames.QR_MODE_SIMPLE_SCAN;
                    }
                }
                scanStoryRepository.addNewId(code);
                iqrFragment.showMsg("Found");
                changeAlertMode(alertMode, GlobalNames.ALERT_MODE_SMALL_INFO);
                return;
            }
        }
        iqrFragment.showMsg("Not Found");
    }

    @Override
    public void changeAlertMode(int modeScan, int modeShow) {
        Story sendedStory = new Story();
        if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {
            Story fullStory = inMemoryStoryRepository.getSelectedStory();
            StoryMediaPath storyMediaPath = new StoryMediaPath();
            storyMediaPath.setAudio(new ArrayList<String>());
            storyMediaPath.setVideo(new ArrayList<String>());
            storyMediaPath.setImages(fullStory.getMedia().getImages());
            sendedStory.setMedia(storyMediaPath);
            sendedStory.setAbout(fullStory.getAbout());

        } else if (modeShow == GlobalNames.ALERT_MODE_FULL_INFO) {
            sendedStory = inMemoryStoryRepository.getSelectedStory();
        }

        iqrFragment.showAlertDialog(modeScan, sendedStory, modeShow);
    }
}
