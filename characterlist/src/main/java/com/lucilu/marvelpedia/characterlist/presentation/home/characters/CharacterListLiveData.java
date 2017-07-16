package com.lucilu.marvelpedia.characterlist.presentation.home.characters;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.lucilu.marvelpedia.base.common.preconditions.Preconditions.get;

/**
 * Created by Lucia on 16/07/2017.
 */

class CharacterListLiveData extends LiveData<CharacterViewEntity> {

    @NonNull
    private final CharacterListViewModel viewModel;

    @Nullable
    private Disposable disposable;

    @Inject
    CharacterListLiveData(@NonNull final CharacterListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void onActive() {
        super.onActive();
        disposable = viewModel.characterViewEntityObservable()
                              .subscribe(this::setValue,
                                  e -> Timber.e(e, "Error updating the LiveData for CharacterViewEntity"));
    }

    @Override
    protected void onInactive() {
        get(disposable).dispose();
        super.onInactive();
    }
}
