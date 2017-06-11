package com.lucilu.marvelpedia.common.injection.components;

import com.lucilu.marvelpedia.common.injection.modules.LoggingModule;
import com.lucilu.marvelpedia.presentation.MarvelPediaApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 Created by luciapayo on 11/06/2017
 */
@Singleton
@Component(modules = {LoggingModule.class})
public interface ApplicationComponent {

    void inject(MarvelPediaApplication app);
}
