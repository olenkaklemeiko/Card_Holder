package com.example.cardholder.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.cardholder.base.BaseActivity;
import com.example.cardholder.cards.CardsActivity;
import com.example.cardholder.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> startActivity(CardsActivity.class), 1500);
    }
}
