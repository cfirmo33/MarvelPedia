package com.lucilu.marvelpedia.base;

import com.lucilu.marvelpedia.base.injection.modules.LoggingModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 Created by luciapayo on 11/06/2017
 */
@Singleton
@Component(modules = {LoggingModule.class})
public interface ApplicationComponent {

    void inject(MarvelPediaApplication app);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
