package com.example.android_dev.qrtest.presenter.character;


import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.ui.fragment.ICharacterInfoFragment;
import com.example.android_dev.qrtest.util.GlobalNames;

public class CharacterInfoPresenter implements ICharacterInfoPresenter {
    private InMemoryStoryRepository inMemoryStoryRepository;
    private ICharacterInfoFragment characterInfoFragment;
    private AppMediaPlayerPresenter appMediaPlayerPresenter;

    public CharacterInfoPresenter(ICharacterInfoFragment iCharacterInfoFragment) {
        this.characterInfoFragment = iCharacterInfoFragment;
        inMemoryStoryRepository = new InMemoryStoryRepository();

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
            characterInfoFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.IMG_RES)) {
            characterInfoFragment.startImageViewerActivity(filePath);
            return;
        }
        characterInfoFragment.showMsg(msg);
    }
}
