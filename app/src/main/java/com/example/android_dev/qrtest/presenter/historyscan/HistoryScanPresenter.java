package com.example.android_dev.qrtest.presenter.historyscan;

import com.example.android_dev.qrtest.db.HistoryScanDataStore;
import com.example.android_dev.qrtest.db.IHistoryScanDataStore;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.QrInformation;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IHistoryScanFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryScanPresenter implements IHistoryScanPresenter {
    private IStoryRepository iStoryRepository;
    private IHistoryScanFragment iHistoryScanFragment;
    private IAppMediaPlayerPresenter iAppMediaPlayerPresenter;
    private IHistoryScanDataStore iHistoryScanDataStore;

    public HistoryScanPresenter(IHistoryScanFragment iHistoryScanFragment,IHistoryScanDataStore iHistoryScanDataStore) {
        this.iHistoryScanFragment = iHistoryScanFragment;
        iStoryRepository = new InMemoryStoryRepository();
        this.iHistoryScanDataStore = iHistoryScanDataStore;
    }

    @Override
    public void getScannedStoryList() {
        if (iHistoryScanDataStore.getAll().size() == 0) {
            iHistoryScanFragment.showMsg("The list is empty");
        } else {
            iHistoryScanFragment.showGridView(prepareDataToSend(iHistoryScanDataStore.getAll()));
        }
    }

    @Override
    public void showStoryContent(QrInformation qrInformation) {
        List<Integer> resIds = null;

        for (QrInformation.QrData qrData : qrInformation.getQrDataList()) {
            if (qrData.getRoleId() == iStoryRepository.getSelectedRole().getId()) {
                resIds = qrData.getQrItemList().getShortInfoAssetIDList();

            }
        }
        iHistoryScanFragment.showAlertDialog(resIds, GlobalNames.ALERT_MODE_SMALL_INFO);
    }

    @Override
    public void playMediaData(AssetTypes resource) {

        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                iStoryRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = iAppMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iHistoryScanFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.AUDIO_RES)) {
            iHistoryScanFragment.startAudioPlayerActivity(filePath);
        }
        iHistoryScanFragment.showMsg(msg);
    }

    @Override
    public void changeAlertMode(QrInformation qrInformation, int modeShow) {
        List<Integer> resIds = null;
        for (QrInformation.QrData qrData : qrInformation.getQrDataList()) {
            if (qrData.getRoleId() == iStoryRepository.getSelectedRole().getId()) {
                if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {
                    resIds = qrData.getQrItemList().getShortInfoAssetIDList();
                } else if (modeShow == GlobalNames.ALERT_MODE_FULL_INFO) {
                    resIds = qrData.getQrItemList().getDetailInfoAssetIDList();
                }
            }
        }

        iHistoryScanFragment.showAlertDialog(resIds, modeShow);
    }


    private ArrayList<QrInformation> prepareDataToSend(List<Integer> scanData) {
        ArrayList<QrInformation> qrInformationList = new ArrayList<>();
        IStory iStory = iStoryRepository.getSelectedStory();

        for (int searchId : scanData) {
            for (QrInformation qrInformation : iStory.getQrInformationList()) {
                if (qrInformation.getId() == searchId) {
                    qrInformationList.add(qrInformation);
                }
            }
        }

        return qrInformationList;
    }

}
