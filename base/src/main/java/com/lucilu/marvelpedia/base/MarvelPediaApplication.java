package com.lucilu.marvelpedia.base;

import com.lucilu.marvelpedia.base.common.injection.components.DaggerApplicationComponent;

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
        getComponent().inject(this);
        initialize();
    }

    private ApplicationComponent getComponent() {
        if(component == null) {
            component = DaggerApplicationComponent.builder().build();
        }
        return component;
    }

    private void initialize() {
        initLogging();
    }

    private void initLogging() {
        Timber.uprootAll();
        Timber.plant(loggingTree);
    }
}
