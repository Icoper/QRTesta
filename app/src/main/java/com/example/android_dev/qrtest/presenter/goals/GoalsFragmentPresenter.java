package com.example.android_dev.qrtest.presenter.goals;

import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.ui.fragment.IGoalsFragment;
import com.example.android_dev.qrtest.util.GlobalNames;

public class GoalsFragmentPresenter implements IGoalsFragmentPresenter {
    private IGoalsFragment goalsFragment;
    private IStoryRepository memoryStoryRepository;
    private IAppMediaPlayerPresenter appMediaPlayerPresenter;

    public GoalsFragmentPresenter(IGoalsFragment iGoalsFragment) {
        this.goalsFragment = iGoalsFragment;
        memoryStoryRepository = new InMemoryStoryRepository();
    }

    @Override
    public void playMediaData(AssetTypes resource) {
        if (appMediaPlayerPresenter == null) {
            appMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                memoryStoryRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = appMediaPlayerPresenter.processMediaData(resource);

        if (msg.equals(GlobalNames.VIDEO_RES)) {
            goalsFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.IMG_RES)) {
            goalsFragment.startImageViewerActivity(filePath);
            return;
        }
        goalsFragment.showMsg(msg);
    }
}
