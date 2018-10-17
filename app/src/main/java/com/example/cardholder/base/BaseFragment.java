package com.example.cardholder.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardholder.cards.CardsActivity;

import butterknife.ButterKnife;

public abstract class  BaseFragment extends Fragment {

    @LayoutRes
    protected abstract int layoutResId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId(), container, false);
    }

}
