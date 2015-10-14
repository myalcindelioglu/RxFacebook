package com.cesarferreira.rxfacebook.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cesarferreira.rxfacebook.library.RxFacebook;
import com.cesarferreira.rxfacebook.library.data.Fields;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            RxFacebook.getInstance(null)
                    .request(Fields.ID, Fields.NAME, Fields.BIRTHDAY, Fields.GENDER)
                    .subscribe(facebookUser -> {
                        Toast.makeText(MainActivity.this, facebookUser.getName(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

}
