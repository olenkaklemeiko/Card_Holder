
package com.example.cardholder.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.cardholder.listeners.FragmentNavigator;
import com.example.cardholder.utils.SettingsManager;

public abstract class BaseActivity extends AppCompatActivity implements FragmentNavigator {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(SettingsManager.getSettings().getAppTheme());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void startActivity(Class clazz) {
        startActivity(clazz, true);
    }

    protected void startActivity(Class clazz, boolean needBeFinished) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (needBeFinished) {
            finish();
        }
    }

    public void replaceFragment(Fragment fragment, int container){
        replaceFragment(fragment, container, false);
    }

    public void replaceFragment(Fragment fragment, int container, boolean addToBackStack){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(addToBackStack ? fragment.getClass().getName() : null);
        transaction.commit();
    }
}
