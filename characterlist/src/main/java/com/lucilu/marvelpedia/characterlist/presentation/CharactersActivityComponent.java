package com.lucilu.marvelpedia.characterlist.presentation;

import com.lucilu.marvelpedia.base.ApplicationComponent;
import com.lucilu.marvelpedia.base.common.injection.modules.ActivityModule;
import com.lucilu.marvelpedia.base.common.injection.scopes.ActivityScope;

import dagger.Component;

/**
 * Created by Lucia on 14/07/2017.
 */
@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface CharactersActivityComponent {

    void inject(CharactersActivity activity);

    @Component.Builder
    interface Builder {
        CharactersActivityComponent build();
    }
}
