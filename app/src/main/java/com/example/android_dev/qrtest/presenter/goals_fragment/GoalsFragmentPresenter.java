package com.example.android_dev.qrtest.presenter.goals_fragment;

import com.example.android_dev.qrtest.db.IGoalsDataStore;
import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.IGoalsFragment;

public class GoalsFragmentPresenter implements IGoalsFragmentPresenter {
    private IGoalsFragment iGoalsFragment;
    private IStoryRepository memoryStoryRepository;
    private IAppMediaPlayerPresenter iAppMediaPlayerPresenter;

    public GoalsFragmentPresenter(IGoalsFragment iGoalsFragment, IGoalsDataStore iGoalsDataStore) {
        this.iGoalsFragment = iGoalsFragment;
        memoryStoryRepository = new InMemoryStoryRepository();
    }

    @Override
    public void playMediaData(AssetTypes resource) {
        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                memoryStoryRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = iAppMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iGoalsFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.AUDIO_RES)) {
            iGoalsFragment.startAudioPlayerActivity(filePath);
        }
        iGoalsFragment.showMsg(msg);
    }
}
