package com.example.cardholder.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;

import com.example.cardholder.R;
import com.example.cardholder.model.CardModel;

import java.util.List;

public class SharedPreferencesUtils {

    private final String SP_NAME = "cards_sp";
    private final String THEME_ID_KEY = "theme_id";
    private final String MAIN_ITEM_LAYOUT = "theme_id";

    private static SharedPreferencesUtils utils;

    private SharedPreferences preferences;

    private SharedPreferencesUtils(Context context) {
        preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getSPUtils(Context context) {
        if (null == utils) {
            utils = new SharedPreferencesUtils(context);
        }
        return utils;
    }

    public void saveThemeId(@StyleRes int themeId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(THEME_ID_KEY, themeId);
        editor.apply();
    }

    @StyleRes
    public int loadThemeId() {
        return preferences.getInt(THEME_ID_KEY, R.style.AppTheme);
    }

    public void saveMainItemLayout(MainItemsEnum layoutId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MAIN_ITEM_LAYOUT, layoutId.getId());
        editor.apply();
    }

    @LayoutRes
    public int loadMainItemLayout() {
        return preferences.getInt(MAIN_ITEM_LAYOUT, 0);
    }
}
