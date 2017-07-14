package com.lucilu.marvelpedia.characterlist.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucilu.marvelpedia.characterlist.R;
import com.lucilu.marvelpedia.characterlist.presentation.list.CharacterListFragment;

import static polanski.option.Option.ofObj;

public class CharactersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        ofObj(savedInstanceState).ifNone(this::attachCharacterListFragment);
    }

    private void attachCharacterListFragment() {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.fragment_container, CharacterListFragment.newInstance())
                                   .commit();
    }
}
