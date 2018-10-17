package com.example.cardholder.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.cardholder.R;

import java.util.List;

public class SettingsRadioListAdapter extends ArrayAdapter<Integer> {

    int selectedIndex = -1;

    public SettingsRadioListAdapter(Context context, int activity_radio_button, List<Integer> saveItems) {
        super(context, activity_radio_button, saveItems);
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Integer itemContents = getItem(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.settings_list_item, null);

            ViewGroup container = v.findViewById(R.id.settings_inflater_container);
            View.inflate(v.getContext(), itemContents, container);
        }

        RadioButton rbSelect = v.findViewById(R.id.radioButton);
        if (selectedIndex == position) {  // check the position to update correct radio button.
            rbSelect.setChecked(true);
        } else {
            rbSelect.setChecked(false);
        }


        if (itemContents != null) {
            ViewGroup container = v.findViewById(R.id.settings_inflater_container);
            ImageView cardImage = container.findViewById(R.id.image_recycler_view);
            TextView cardText = container.findViewById(R.id.name_card);

            if (null != cardImage) {
                cardImage.setImageResource(R.drawable.card_add);
            }
            cardText.setText(R.string.card_name);
        }

        return v;
    }
}