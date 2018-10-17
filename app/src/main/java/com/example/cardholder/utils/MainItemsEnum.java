package com.example.cardholder.utils;

import com.example.cardholder.R;

public enum MainItemsEnum {

    BIG_ITEM(0, R.layout.recycler_view_row),
    SMALL_ICON_ITEM(1, R.layout.main_item_small_icon),
    NO_ICON_ITEM(2, R.layout.main_item_no_icon);

    private final int id;
    private int itemLayoutRes;

    MainItemsEnum(int id, int res){
        this.id = id;
        this.itemLayoutRes = res;
    }

    public int getId() {
        return id;
    }

    public int getItemLayoutRes() {
        return itemLayoutRes;
    }

    public boolean compare(int i) {
        return id == i;
    }

    public static MainItemsEnum getItemById(int id){
        MainItemsEnum[] appStates = MainItemsEnum.values();
        for (MainItemsEnum A : appStates) {
            if (A.compare(id))
                return A;
        }
        return MainItemsEnum.BIG_ITEM;
    }
}
