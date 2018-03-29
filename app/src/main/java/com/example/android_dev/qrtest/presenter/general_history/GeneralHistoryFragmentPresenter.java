package com.example.android_dev.qrtest.presenter.general_history;


import com.example.android_dev.qrtest.db.IMemoryStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IGeneralHistoryFragment;

public class GeneralHistoryFragmentPresenter implements IGeneralHistoryFragmentPresenter {
    private IGeneralHistoryFragment iGeneralHistoryFragment;
    private IAppMediaPlayerPresenter iAppMediaPlayerPresenter;
    private IMemoryStoryRepository iMemoryStoryRepository;

    public GeneralHistoryFragmentPresenter(IGeneralHistoryFragment iGeneralHistoryFragment) {
        this.iGeneralHistoryFragment = iGeneralHistoryFragment;
        iMemoryStoryRepository = new InMemoryStoryRepository();
    }

    @Override
    public void playMediaData(AssetTypes resource) {
        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                iMemoryStoryRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = iAppMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iGeneralHistoryFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.AUDIO_RES)) {
            iGeneralHistoryFragment.startAudioPlayerActivity(filePath);
        }
        iGeneralHistoryFragment.showMsg(msg);
    }
}
