package com.lucilu.marvelpedia.characterlist.presentation.list;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Lucia on 10/07/2017.
 */
@AutoValue
abstract class CharacterViewEntity {

    @NonNull
    abstract String name();

    @NonNull
    static CharacterViewEntity.Builder builder() {
        return new AutoValue_CharacterViewEntity.Builder();
    }

    @AutoValue.Builder
    interface Builder {

        Builder name(String name);

        CharacterViewEntity build();
    }
}
