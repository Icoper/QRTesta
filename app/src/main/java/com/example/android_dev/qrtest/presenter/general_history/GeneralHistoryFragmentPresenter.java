package com.example.android_dev.qrtest.presenter.general_history;


import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IGeneralHistoryFragment;

public class GeneralHistoryFragmentPresenter implements IGeneralHistoryFragmentPresenter {
    private IGeneralHistoryFragment iGeneralHistoryFragment;
    private IAppMediaPlayerPresenter iAppMediaPlayerPresenter;

    public GeneralHistoryFragmentPresenter(IGeneralHistoryFragment iGeneralHistoryFragment) {
        this.iGeneralHistoryFragment = iGeneralHistoryFragment;
    }

    @Override
    public void playMediaData(String fileType, String filePath) {
        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String msg = iAppMediaPlayerPresenter.processMediaData(fileType, filePath);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iGeneralHistoryFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.AUDIO_RES)) {
            iGeneralHistoryFragment.startAudioPlayerActivity(filePath);
        }
        iGeneralHistoryFragment.showMsg(msg);
    }
}
