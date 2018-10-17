package com.example.cardholder.view.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardholder.R;
import com.example.cardholder.listeners.ClickCardListener;
import com.example.cardholder.model.CardModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<CardModel, RecyclerViewAdapter.ViewHolder> {

    private ClickCardListener clickCardListener;
    @LayoutRes
    private int itemLayout;

    public RecyclerViewAdapter(OrderedRealmCollection<CardModel> data, @LayoutRes int itemLayout) {
        super(data, true);
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CardModel cardData = getItem(position);

        holder.cardName.setText(cardData.getName());
        holder.cardImage.setImageBitmap(cardData.getPhoto());

    }

    public void setClickCardListener(ClickCardListener clickCardListener) {
        this.clickCardListener = clickCardListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name_card)
        TextView cardName;
        @BindView(R.id.image_recycler_view)
        ImageView cardImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != clickCardListener) {
                clickCardListener.onCardClick(getItem(getAdapterPosition()));
            }
        }
    }
}
