package com.example.cardholder.cards;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;
import com.example.cardholder.R;
import com.example.cardholder.base.BaseActivity;
import com.example.cardholder.model.CardModel;
import com.example.cardholder.settings.SettingsActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class CardsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final int SETTINGS_REQUEST_CODE = 0x3039;
    private final int PERMISSION_REQUEST_CODE = 1;
    private final int RC_SIGN_IN = 0x75c;

    private Realm mRealm;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private LinearLayout navHeader;
    private ImageView navImage;
    private TextView navName;
    private TextView navMail;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();
        initDrawerLayout();
        replaceFragment(MyCardsFragment.getInstance(), true);
        initNavViews();
        initSingIn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        requestMultiplePermissions();
    }

    private void initNavViews() {
        View headerLayout = navigationView.getHeaderView(0);
        navHeader = headerLayout.findViewById(R.id.nav_header_layout);
        navImage = headerLayout.findViewById(R.id.nav_header_image);
        navName = headerLayout.findViewById(R.id.nav_header_name);
        navMail = headerLayout.findViewById(R.id.nav_header_mail);

        navHeader.setOnClickListener(v -> {

//            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private void initSingIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("682251760601-50rn8b34mej2ba4s8e0nvj92ujgvsmfa.apps.googleusercontent.com")
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private void initDrawerLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void addCard(CardModel data) {
        mRealm.beginTransaction();
        CardModel card = mRealm.createObject(CardModel.class);
        card.copyFrom(data);
        mRealm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:

                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                break;
        }

        drawer.clearFocus();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(getClass().getName(), "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        //if (null != account) {
            navName.setText("Volodymyr Pavliuk");
            navMail.setText("vpavluk31@gmail.com");
            Glide.with(this).load("https://lh3.googleusercontent.com/-Vn5IaX9Y3RA/UlGUYJsFBTI/AAAAAAAAA5A/2Xgbx_iZ4ucmOAwqWOOVJ5lvcfVEyFQiACEwYBhgL/w140-h140-p/dPLPYmy4pnM.jpg").
                    apply(RequestOptions.circleCropTransform()).into(navImage);
        //}
    }

    //region Fragment Navigator
    public void replaceFragment(Fragment fragment) {
        super.replaceFragment(fragment, R.id.fragment_container);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        super.replaceFragment(fragment, R.id.fragment_container, addToBackStack);
    }
    //endregion


    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                },
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
