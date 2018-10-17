package com.example.cardholder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cardholder.R;
import com.example.cardholder.base.BaseActivity;
import com.example.cardholder.utils.MainItemsEnum;
import com.example.cardholder.utils.SettingsManager;
import com.example.cardholder.view.adapter.SettingsRadioListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.theme_switch)
    SwitchCompat switchCompat;
    SettingsManager settingsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        settingsManager = SettingsManager.getSettings();
        initViews();
    }

    private void initViews() {
        switchCompat.setChecked(settingsManager.getAppTheme() != R.style.AppTheme);

        List<Integer> values = new ArrayList<>();
        values.add(R.layout.recycler_view_row);
        values.add(R.layout.main_item_small_icon);
        values.add(R.layout.main_item_no_icon);


        final SettingsRadioListAdapter adapter = new SettingsRadioListAdapter(this, R.layout.activity_settings, values);
        ListView listView = findViewById(R.id.radioGroup);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            adapter.setSelectedIndex(position);  // set selected position and notify the adapter
            adapter.notifyDataSetChanged();
            SettingsManager.getSettings().setMainItemView(MainItemsEnum.getItemById(position));
        });

        justifyListViewHeightBasedOnChildren(listView);
    }

    @OnClick({R.id.theme_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.theme_switch:
                handleThemeSwitch(view);
                break;
        }
    }

    private void handleThemeSwitch(View view) {
        SwitchCompat switchView = (SwitchCompat) view;
        settingsManager.setAppTheme(switchView.isChecked() ? R.style.AppThemeDark : R.style.AppTheme);
        setTheme(settingsManager.getAppTheme());
        recreate();
    }

    private void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
