package com.cesarferreira.rxfacebook.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cesarferreira.rxfacebook.library.RxFacebook;
import com.cesarferreira.rxfacebook.library.data.Fields;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            RxFacebook.getInstance(AccessToken.getCurrentAccessToken())
                    .request(Fields.ID, Fields.NAME, Fields.BIRTHDAY, Fields.GENDER)
                    .subscribe(facebookUser -> {
                        Toast.makeText(MainActivity.this, facebookUser.getName(), Toast.LENGTH_SHORT).show();
                    });
        });

        RxFacebook.getInstance(AccessToken.getCurrentAccessToken())
                .startAccessTokenTracking()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accessToken -> {
                    Toast.makeText(MainActivity.this, "AccessToken changed", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxFacebook.getInstance(AccessToken.getCurrentAccessToken())
                .stopAccessTokenTracking();
    }
}
