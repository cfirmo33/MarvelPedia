package com.lucilu.marvelpedia.characterlist.presentation.home.characters;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Lucia on 10/07/2017.
 */
public class CharacterListViewModel extends ViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();

    private BehaviorSubject<CharacterViewEntity> characterStream = BehaviorSubject.create();

    private MutableLiveData<CharacterViewEntity> characterLiveData = new MutableLiveData<>();

    public CharacterListViewModel() {
        bind();
        characterStream.onNext(CharacterViewEntity.builder().name("DEAD POOL").build());
    }

    private void bind() {
        disposables.add(characterStream.observeOn(Schedulers.computation())
                                       .subscribe(characterLiveData::postValue));
    }

    @NonNull
    LiveData<CharacterViewEntity> getCharacterLiveData () {
        return characterLiveData;
    }
}
