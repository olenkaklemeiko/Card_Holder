package com.example.cardholder.cards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cardholder.R;
import com.example.cardholder.base.BaseFragment;
import com.example.cardholder.model.CardModel;
import com.example.cardholder.model.TableItem;
import com.example.cardholder.view.FieldCreateView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardInfoFragment extends BaseFragment implements FieldCreateView.AddTableItem {

    private static final String MODEL_KEY = "key_info_model";

    @BindView(R.id.card_image)
    ImageView cardImage;
    @BindView(R.id.created_fields_layout)
    FieldCreateView fieldsCreateLayout;
    @BindView(R.id.card_name)
    TextView cardName;
    @BindView(R.id.card_code)
    TextView cardCode;

    public static CardInfoFragment getInstance(CardModel card){
        CardInfoFragment fragment = new CardInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(MODEL_KEY, card);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_card_info;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
    }

    private void initViews() {
        Bundle arguments = getArguments();
        if (null != arguments){
            CardModel model = arguments.getParcelable(MODEL_KEY);
            cardImage.setImageBitmap(model.getPhoto());
            cardName.setText(String.format("%s: %s", "Card name",model.getName()));
            cardCode.setText(String.format("%s: %s", "Card code", model.getCode()));
        }

        fieldsCreateLayout.setAddItemListener(this);

    }

    @Override
    public void addFieldItem(TableItem fieldItem) {
        TextView text = new TextView(getContext());
        text.setTextSize(32);
        text.setText(String.format("%s: %s", fieldItem.getTitle(), fieldItem.getType()));
        ((ViewGroup)fieldsCreateLayout.getParent()).addView(text);
    }
}
