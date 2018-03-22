package com.example.android_dev.qrtest.presenter.character_info;


import com.example.android_dev.qrtest.db.InMemoryStoryRepository;
import com.example.android_dev.qrtest.model.Actor;
import com.example.android_dev.qrtest.model.Story;
import com.example.android_dev.qrtest.util.ICharacterInfoFragment;

public class CharacterInfoPresenter implements ICharacterInfoPresenter {
    InMemoryStoryRepository inMemoryStoryRepository;
    ICharacterInfoFragment iCharacterInfoFragment;

    public CharacterInfoPresenter(ICharacterInfoFragment iCharacterInfoFragment) {
        this.iCharacterInfoFragment = iCharacterInfoFragment;
    }

    @Override
    public void showActorInfo() {
        Actor selectedActor = null;
        if (inMemoryStoryRepository == null) {
            inMemoryStoryRepository = new InMemoryStoryRepository();
        }
        String actorID = inMemoryStoryRepository.getActorId();
        Story story = inMemoryStoryRepository.getSelectedStory();
        for (Actor actor : story.getActors()) {
            if (actor.getId().equals(actorID)) {
                selectedActor = actor;
            }
        }
        iCharacterInfoFragment.showActorInfo(selectedActor);
    }
}
