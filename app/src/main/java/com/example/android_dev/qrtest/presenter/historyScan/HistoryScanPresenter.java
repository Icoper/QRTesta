package com.example.android_dev.qrtest.presenter.historyScan;

import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.historyScan.IHistoryScanDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.QrInformation;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.ui.fragment.IHistoryScanFragment;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.util.ArrayList;
import java.util.List;

public class HistoryScanPresenter implements IHistoryScanPresenter {
    private IStoryRepository storyRepository;
    private IHistoryScanFragment historyScanFragment;
    private IAppMediaPlayerPresenter appMediaPlayerPresenter;
    private IHistoryScanDataStore historyScanDataStore;

    public HistoryScanPresenter(IHistoryScanFragment iHistoryScanFragment, IHistoryScanDataStore iHistoryScanDataStore) {
        this.historyScanFragment = iHistoryScanFragment;
        storyRepository = new InMemoryStoryRepository();
        this.historyScanDataStore = iHistoryScanDataStore;
    }

    @Override
    public void getScannedStoryList() {
        if (historyScanDataStore.getAll().size() == 0) {
            historyScanFragment.showMsg("The list is empty");
        } else {
            historyScanFragment.showGridView(prepareDataToSend(historyScanDataStore.getAll()));
        }
    }

    @Override
    public void showStoryContent(QrInformation qrInformation) {
        List<Integer> resIds = null;

        for (QrInformation.QrData qrData : qrInformation.getQrDataList()) {
            if (qrData.getRoleId() == storyRepository.getSelectedRole().getId()) {
                resIds = qrData.getQrItemList().getShortInfoAssetIDList();
            }
        }
        historyScanFragment.showAlertDialog(resIds, GlobalNames.ALERT_MODE_SMALL_INFO);
    }

    @Override
    public void playMediaData(AssetTypes resource) {
        if (appMediaPlayerPresenter == null) {
            appMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                storyRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = appMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            historyScanFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.IMG_RES)) {
            historyScanFragment.startImageViewerActivity(filePath);
            return;
        }
        historyScanFragment.showMsg(msg);
    }

    @Override
    public void changeAlertMode(QrInformation qrInformation, int modeShow) {
        List<Integer> resIds = null;
        for (QrInformation.QrData qrData : qrInformation.getQrDataList()) {
            if (qrData.getRoleId() == storyRepository.getSelectedRole().getId()) {
                if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {
                    resIds = qrData.getQrItemList().getShortInfoAssetIDList();
                } else if (modeShow == GlobalNames.ALERT_MODE_FULL_INFO) {
                    resIds = qrData.getQrItemList().getDetailInfoAssetIDList();
                }
            }
        }

        historyScanFragment.showAlertDialog(resIds, modeShow);
    }


    private ArrayList<QrInformation> prepareDataToSend(List<Integer> scanData) {
        ArrayList<QrInformation> qrInformationList = new ArrayList<>();
        IStory iStory = storyRepository.getSelectedStory();

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
