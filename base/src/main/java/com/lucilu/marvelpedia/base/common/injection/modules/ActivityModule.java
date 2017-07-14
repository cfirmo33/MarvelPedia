package com.lucilu.marvelpedia.base.common.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lucilu.marvelpedia.base.common.injection.qualifiers.ForActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lucia on 14/07/2017.
 */
@Module
public final class ActivityModule {

    @NonNull
    private final AppCompatActivity activity;

    @NonNull
    private final FragmentManager fragmentManager;

    public ActivityModule(@NonNull final AppCompatActivity activity,
                          @NonNull final FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @ForActivity
    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return fragmentManager;
    }
}
