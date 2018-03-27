package com.example.android_dev.qrtest.presenter.character_info;


import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.json.AssertItems;
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
    public void playMediaData(AssertItems.Resource resource) {
        if (iAppMediaPlayerPresenter == null) {
            iAppMediaPlayerPresenter = new AppMediaPlayerPresenter();
        }
        String filePath = GlobalNames.ENVIRONMENT_STORE +
                inMemoryStoryRepository.getSelectedStory().getQrInformations().get(0).getCode() + "/" +
                resource.getName();
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
