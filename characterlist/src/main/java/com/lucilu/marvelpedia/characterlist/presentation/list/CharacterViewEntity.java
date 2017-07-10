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

    @AutoValue.Builder
    interface Builder {

        Builder name(String name);

        CharacterViewEntity build();
    }
}
