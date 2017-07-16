package com.lucilu.marvelpedia.characterlist.presentation.home.characters;

import com.lucilu.marvelpedia.base.common.injection.scopes.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Lucia on 15/07/2017.
 */
@FragmentScope
@Subcomponent
public interface CharacterListFragmentComponent {

    void inject(CharacterListFragment fragment);

}
