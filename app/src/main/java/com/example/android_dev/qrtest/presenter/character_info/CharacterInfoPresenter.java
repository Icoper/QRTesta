package com.example.android_dev.qrtest.presenter.character_info;


import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.AssetTypes;
import com.example.android_dev.qrtest.presenter.AppMediaPlayerPresenter;
import com.example.android_dev.qrtest.util.GlobalNames;
import com.example.android_dev.qrtest.util.ICharacterInfoFragment;

public class CharacterInfoPresenter implements ICharacterInfoPresenter {
    InMemoryStoryRepository inMemoryStoryRepository;
    ICharacterInfoFragment iCharacterInfoFragment;
    private AppMediaPlayerPresenter iAppMediaPlayerPresenter;

    public CharacterInfoPresenter(ICharacterInfoFragment iCharacterInfoFragment) {
        this.iCharacterInfoFragment = iCharacterInfoFragment;
        inMemoryStoryRepository = new InMemoryStoryRepository();

    }

    @Override
    public void playMediaData(AssetTypes resource) {
        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                inMemoryStoryRepository.getSelectedStory().getResFolderName() + "/Resource1/" +
                resource.getFileName();
        String msg = iAppMediaPlayerPresenter.processMediaData(resource);
        if (msg.equals(GlobalNames.VIDEO_RES)) {
            iCharacterInfoFragment.startVideoPlayerActivity(filePath);
            return;
        } else if (msg.equals(GlobalNames.AUDIO_RES)) {
            iCharacterInfoFragment.startAudioPlayerActivity(filePath);
        }
        iCharacterInfoFragment.showMsg(msg);
    }
}
