package com.lucilu.marvelpedia.characterlist.presentation.home.characters;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Lucia on 10/07/2017.
 */
class CharacterListViewModel extends ViewModel {

    private BehaviorSubject<CharacterViewEntity> characterStream = BehaviorSubject.create();

    @Inject
    CharacterListViewModel() {
        characterStream.onNext(CharacterViewEntity.builder().name("DEAD POOL").build());
    }

    Observable<CharacterViewEntity> characterViewEntityObservable() {
        return characterStream;
    }
}
