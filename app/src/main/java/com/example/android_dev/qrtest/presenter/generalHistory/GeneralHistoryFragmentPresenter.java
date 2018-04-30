package com.example.android_dev.qrtest.presenter.generalHistory;


import com.example.android_dev.qrtest.db.IStoryRepository;
import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.presenter.IAppMediaPlayerPresenter;
import com.example.android_dev.qrtest.ui.fragment.IGeneralHistoryFragment;
import com.example.android_dev.qrtest.util.GlobalNames;

public class GeneralHistoryFragmentPresenter implements IGeneralHistoryFragmentPresenter {
    private IGeneralHistoryFragment generalHistoryFragment;
    private IAppMediaPlayerPresenter appMediaPlayerPresenter;
    private IStoryRepository storyRepository;

    public GeneralHistoryFragmentPresenter(IGeneralHistoryFragment iGeneralHistoryFragment) {
        this.generalHistoryFragment = iGeneralHistoryFragment;
        storyRepository = new InMemoryStoryRepository();
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
            generalHistoryFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.IMG_RES)) {
            generalHistoryFragment.startImageViewerActivity(filePath);
            return;
        }
    }
}
