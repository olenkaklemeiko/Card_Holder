package com.example.cardholder.listeners;

import android.support.v4.app.Fragment;

public interface FragmentNavigator {

    void replaceFragment(Fragment fragment, int container);

    void replaceFragment(Fragment fragment, int container, boolean addToBackStack);

}
