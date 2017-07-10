package com.lucilu.marvelpedia.characterlist.presentation.list;

import android.arch.lifecycle.ViewModel;

import io.reactivex.Observable;

/**
 * Created by Lucia on 10/07/2017.
 */
public class CharacterListViewModel extends ViewModel {

    Observable<CharacterViewEntity> characterViewEntityObservable() {
        return Observable.just(CharacterViewEntity.builder().name("DEAD POOL").build());
    }
}
