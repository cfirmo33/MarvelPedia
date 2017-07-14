package com.lucilu.marvelpedia.base.presentation.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lucilu.marvelpedia.base.common.preconditions.Preconditions;


/**
 * Created by Lucia on 14/07/2017.
 */
public abstract class BaseInjectingActivity<Component> extends AppCompatActivity {

    @Nullable
    private Component component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        component = createComponent();
        onInject(component);
        
        super.onCreate(savedInstanceState);
    }

    @NonNull
    public Component getComponent() {
        return Preconditions.get(component);
    }

    public abstract void onInject(@NonNull final Component component);

    @LayoutRes
    protected abstract int getLayoutId();

    @NonNull
    public abstract Component createComponent();
}
