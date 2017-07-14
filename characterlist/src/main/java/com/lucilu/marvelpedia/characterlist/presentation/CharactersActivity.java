package com.lucilu.marvelpedia.characterlist.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lucilu.marvelpedia.base.presentation.common.BaseInjectingActivity;
import com.lucilu.marvelpedia.characterlist.R;
import com.lucilu.marvelpedia.characterlist.presentation.list.CharacterListFragment;

import static polanski.option.Option.ofObj;

public class CharactersActivity extends BaseInjectingActivity<CharactersActivityComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        ofObj(savedInstanceState).ifNone(this::attachCharacterListFragment);
    }

    @NonNull
    @Override
    public CharactersActivityComponent createComponent() {
        return DaggerCharactersActivityComponent.builder()
                                                .activity(this)
                                                .build();
    }

    @Override
    public void onInject(@NonNull final CharactersActivityComponent charactersActivityComponent) {
        charactersActivityComponent.inject(this);
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
