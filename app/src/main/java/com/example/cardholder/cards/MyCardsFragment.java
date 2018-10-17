package com.example.cardholder.cards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cardholder.R;
import com.example.cardholder.base.BaseActivity;
import com.example.cardholder.base.BaseFragment;
import com.example.cardholder.listeners.ClickCardListener;
import com.example.cardholder.model.CardModel;
import com.example.cardholder.utils.SettingsManager;
import com.example.cardholder.view.adapter.RecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MyCardsFragment extends BaseFragment implements ClickCardListener {

    private Realm mRealm;

    @BindView(R.id.fab)
    FloatingActionButton addButton;

    public static MyCardsFragment getInstance(){
        return new MyCardsFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_my_cards;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mRealm.where(CardModel.class).findAllAsync(), SettingsManager.getSettings().getMainItemView());
        adapter.setClickCardListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCardClick(CardModel card) {
        ((CardsActivity)getActivity()).replaceFragment(CardInfoFragment.getInstance(card));
    }

    @OnClick(R.id.fab)
    public void onClick() {
        ((CardsActivity)getActivity()).replaceFragment(AddNewCardFragment.getInstance());
    }
}
