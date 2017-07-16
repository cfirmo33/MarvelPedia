package com.lucilu.marvelpedia.characterlist.presentation.home.characters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.lucilu.marvelpedia.base.presentation.common.base.BaseInjectingFragment;
import com.lucilu.marvelpedia.characterlist.R;
import com.lucilu.marvelpedia.characterlist.presentation.home.HomeActivity;

/**
 * Created by Lucia on 14/07/2017.
 */
public class CharacterListFragment extends BaseInjectingFragment {

    @NonNull
    public static Fragment newInstance() {
        return new CharacterListFragment();
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
}
