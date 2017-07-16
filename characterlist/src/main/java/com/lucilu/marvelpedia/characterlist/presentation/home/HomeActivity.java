package com.lucilu.marvelpedia.characterlist.presentation.home;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lucilu.marvelpedia.base.MarvelPediaApplication;
import com.lucilu.marvelpedia.base.presentation.common.base.BaseInjectingActivity;
import com.lucilu.marvelpedia.characterlist.R;
import com.lucilu.marvelpedia.characterlist.presentation.home.characters.CharacterListFragment;

import static polanski.option.Option.ofObj;

public class HomeActivity extends BaseInjectingActivity<HomeActivityComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        ofObj(savedInstanceState).ifNone(this::attachCharacterListFragment);
    }

    @NonNull
    @Override
    protected HomeActivityComponent createComponent() {
        return DaggerHomeActivityComponent.builder()
                                          .activity(this)
                                          .applicationComponent(MarvelPediaApplication.class.cast(getApplication()).getComponent())
                                          .build();
    }

    @Override
    public void onInject(@NonNull final HomeActivityComponent homeActivityComponent) {
        homeActivityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_character_list;
    }

    private void attachCharacterListFragment() {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.fragment_container, CharacterListFragment.newInstance())
                                   .commit();
    }
}
