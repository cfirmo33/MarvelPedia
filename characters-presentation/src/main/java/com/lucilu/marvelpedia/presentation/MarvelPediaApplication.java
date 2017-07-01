package com.lucilu.marvelpedia.presentation;

import com.lucilu.marvelpedia.common.injection.components.ApplicationComponent;
import com.lucilu.marvelpedia.common.injection.components.DaggerApplicationComponent;

import android.app.Application;
import android.support.annotation.CallSuper;

import javax.inject.Inject;

import timber.log.Timber;

/**
 Created by luciapayo on 11/06/2017
 */

public class MarvelPediaApplication extends Application {

    private ApplicationComponent component;

    @Inject
    Timber.Tree loggingTree;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        createComponent().inject(this);
        initialize();
    }

    private ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder().build();
    }

    private void initialize() {
        initLogging();
    }

    private void initLogging() {
        Timber.uprootAll();
        Timber.plant(loggingTree);
    }
}
