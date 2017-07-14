package com.lucilu.marvelpedia.characterlist.presentation.list;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.lucilu.marvelpedia.base.presentation.common.BaseInjectingFragment;
import com.lucilu.marvelpedia.characterlist.R;

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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_character_list;
    }
}
