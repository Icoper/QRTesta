package com.example.android_dev.qrtest.presenter.qr;

import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.db.goals.IGoalsDataStore;
import com.example.android_dev.qrtest.db.historyScan.IHistoryScanDataStore;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.model.HistoryScansQRInformationIDs;
import com.example.android_dev.qrtest.model.IStory;
import com.example.android_dev.qrtest.model.QrInformation;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.ui.fragment.IQRFragment;
import com.example.android_dev.qrtest.util.GlobalNames;

import java.util.List;

public class QRFPresenter implements IQRPresenter {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private IQRFragment qrFragment;
    private AppMediaPlayerPresenter appMediaPlayerPresenter;
    private QrInformation selectedQrInformation;
    private IHistoryScanDataStore historyScanDataStore;
    private IGoalsDataStore goalsDataStore;

    public QRFPresenter(IQRFragment iqrFragment, IHistoryScanDataStore iHistoryScanDataStore, IGoalsDataStore iGoalsDataStore) {
        this.qrFragment = iqrFragment;
        this.goalsDataStore = iGoalsDataStore;
        this.historyScanDataStore = iHistoryScanDataStore;
    }

    private InMemoryStoryRepository getStoryRepo() {
        if (inMemoryStoryRepository == null) {
            inMemoryStoryRepository = new InMemoryStoryRepository();
        }
        return inMemoryStoryRepository;
    }

    @Override
    public void playMediaData(AssetTypes resource) {

        if (appMediaPlayerPresenter == null) {
            appMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                inMemoryStoryRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = appMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            qrFragment.startVideoPlayerActivity(filePath);
            return;
        }
    }

    @Override
    public synchronized void checkCode(String scannedCode) {
        int alertScanMode = 0;
        int qrInformationId = 0;
        selectedQrInformation = null;
        IStory jsonStory = getStoryRepo().getSelectedStory();
        List<Integer> resIds = null;

        for (QrInformation qrInformation : jsonStory.getQrInformationList()) {
            String qrCode = qrInformation.getCode();
            if (qrCode.equals(scannedCode)) {
                qrInformationId = qrInformation.getId();
                selectedQrInformation = qrInformation;
                break;
            }
        }
        if (selectedQrInformation != null) {
            int qrInfoId = selectedQrInformation.getId();
            for (HistoryScansQRInformationIDs historyScansQRInfo : jsonStory.getHistoryScansQRInformationsIDList()) {
                if (historyScansQRInfo.getQrInformationID() == qrInfoId) {
                    if (historyScansQRInfo.isShortInfo()) {
                        alertScanMode = GlobalNames.QR_MODE_FIRST_SCAN;
                        historyScanDataStore.update(qrInformationId);
                        historyScansQRInfo.setShortInfo(false);

                        QrInformation.QrData qrData = null;
                        for (QrInformation.QrData _qrData : selectedQrInformation.getQrDataList()) {
                            if (_qrData.getRoleId() == inMemoryStoryRepository.getSelectedRole().getId()) {
                                qrData = _qrData;
                            }
                        }
                        goalsDataStore.updateNewGoals(qrData.getQrItemList().getGoalDetailAssetIDList());
                    } else {
                        alertScanMode = GlobalNames.QR_MODE_SIMPLE_SCAN;
                    }
                }
            }

            for (QrInformation.QrData qrData : selectedQrInformation.getQrDataList()) {
                if (qrData.getRoleId() == inMemoryStoryRepository.getSelectedRole().getId()) {
                    resIds = qrData.getQrItemList().getShortInfoAssetIDList();
                }
            }
            qrFragment.showAlertDialog(alertScanMode, resIds, GlobalNames.ALERT_MODE_SMALL_INFO);
        } else {
            qrFragment.showMsg(R.string.qr_code_not_found);
        }


    }

    @Override
    public void changeAlertMode(int modeScan, int modeShow) {
        List<Integer> resIds = null;
        for (QrInformation.QrData qrData : selectedQrInformation.getQrDataList()) {
            if (qrData.getRoleId() == inMemoryStoryRepository.getSelectedRole().getId()) {
                if (modeShow == GlobalNames.ALERT_MODE_SMALL_INFO) {
                    resIds = qrData.getQrItemList().getShortInfoAssetIDList();
                } else if (modeShow == GlobalNames.ALERT_MODE_FULL_INFO) {
                    resIds = qrData.getQrItemList().getDetailInfoAssetIDList();
                }
            }
        }

        qrFragment.showAlertDialog(modeScan, resIds, modeShow);
    }
}
