package com.lucilu.marvelpedia.characterlist.presentation.home;

import android.support.v7.app.AppCompatActivity;

import com.lucilu.marvelpedia.base.common.injection.modules.ActivityModule;
import com.lucilu.marvelpedia.base.common.injection.scopes.ActivityScope;
import com.lucilu.marvelpedia.characterlist.presentation.home.characters.CharacterListFragmentComponent;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Lucia on 14/07/2017.
 */
@ActivityScope
@Component(modules = {ActivityModule.class})
public interface HomeActivityComponent {

    void inject(HomeActivity activity);

    CharacterListFragmentComponent createCharacterListFragmentComponent();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder activity(AppCompatActivity activity);

        HomeActivityComponent build();
    }
}
