package com.lucilu.marvelpedia.characterlist.presentation.home;

import android.support.v7.app.AppCompatActivity;

import com.lucilu.marvelpedia.base.common.injection.modules.ActivityModule;
import com.lucilu.marvelpedia.base.common.injection.scopes.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Lucia on 14/07/2017.
 */
@ActivityScope
@Component(modules = {ActivityModule.class, SubcomponentsModule.class})
public interface HomeActivityComponent {

    void inject(HomeActivity activity);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder activity(AppCompatActivity activity);

        HomeActivityComponent build();
    }
}
