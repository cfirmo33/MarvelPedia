package com.lucilu.marvelpedia.characterlist.presentation.home.characters;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.lucilu.marvelpedia.base.presentation.common.base.BaseInjectingFragment;
import com.lucilu.marvelpedia.characterlist.R;
import com.lucilu.marvelpedia.characterlist.presentation.home.HomeActivity;

/**
 * Created by Lucia on 14/07/2017.
 */
public class CharacterListFragment extends BaseInjectingFragment {

    private TextView character;

    @NonNull
    public static Fragment newInstance() {
        return new CharacterListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharacterListViewModel viewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        viewModel.getCharacterLiveData().observe(this, entity -> character.setText(entity.name()));
    }

    @Override
    public void onInject() {
        HomeActivity.class.cast(getActivity())
                          .getComponent()
                          .createCharacterListFragmentComponent()
                          .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_character_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        character = view.findViewById(R.id.text_character);
    }
}
