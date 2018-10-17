package com.example.cardholder.utils;

import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.example.cardholder.R;

public class SettingsManager {

    private static SettingsManager settingsManager;

    private SharedPreferencesUtils sharedPreferencesUtils;

    @StyleRes
    private int appTheme;
    private MainItemsEnum mainItemView;

    private SettingsManager(SharedPreferencesUtils utils) {
        sharedPreferencesUtils = utils;
        appTheme = sharedPreferencesUtils.loadThemeId();
        mainItemView = MainItemsEnum.getItemById(sharedPreferencesUtils.loadMainItemLayout());
    }

    public static void initSettingsManager(SharedPreferencesUtils utils) {
        settingsManager = new SettingsManager(utils);
    }

    @Nullable
    public static SettingsManager getSettings() {
        return settingsManager;
    }

    @StyleRes
    public int getAppTheme() {
        return appTheme;
//        return R.style.AppThemeDark;
    }

    public void setAppTheme(@StyleRes int styleRes) {
        appTheme = styleRes;
        sharedPreferencesUtils.saveThemeId(styleRes);
    }

    public int getMainItemView() {
        return mainItemView.getItemLayoutRes();
    }

    public void setMainItemView(MainItemsEnum mainItemView) {
        this.mainItemView = mainItemView;
        sharedPreferencesUtils.saveMainItemLayout(mainItemView);
    }
}
