package com.example.cardholder;

import android.app.Application;

import com.example.cardholder.utils.SettingsManager;
import com.example.cardholder.utils.SharedPreferencesUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SettingsManager.initSettingsManager(SharedPreferencesUtils.getSPUtils(this));
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
