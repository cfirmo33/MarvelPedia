package com.lucilu.marvelpedia.common.injection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 Created by luciapayo on 11/06/2017
 */
@Module
public class LoggingModule {

    @Provides
    @Singleton
    static Timber.Tree provideLoggingTree() {
        return new Timber.Tree(){
            @Override
            protected void log(final int i, final String s, final String s1, final Throwable throwable) {
                // Do nothing for now
            }
        };
    }
}
