package com.example.cardholder.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cardholder.R;
import com.example.cardholder.model.TableItem;
import com.example.cardholder.utils.SettingsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FieldCreateView extends LinearLayout {

    AddTableItem addItemListener;

    @BindView(R.id.name)
    TextView fName;
    @BindView(R.id.type)
    TextView fType;
    @BindView(R.id.button_add)
    ImageButton addButton;

    public FieldCreateView(Context context) {
        this(context, null);
    }

    public FieldCreateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FieldCreateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this, inflate(context, R.layout.create_field, this));
        addButton.setImageResource(SettingsManager.getSettings().getAppTheme() == R.style.AppTheme?
        R.drawable.ic_add_black : R.drawable.ic_add_white);
    }

    @OnClick(R.id.button_add)
    public void onClick() {
        TableItem item = new TableItem();
        item.setTitle(fName.getText().toString());
        item.setType(fType.getText().toString());
        addItemListener.addFieldItem(item);
    }

    public void setAddItemListener(AddTableItem listener) {
        addItemListener = listener;
    }

    public interface AddTableItem {

        void addFieldItem(TableItem fieldItem);
    }
}