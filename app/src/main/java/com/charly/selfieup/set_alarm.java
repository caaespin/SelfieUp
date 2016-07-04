package com.charly.selfieup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.charly.selfieup.alarmdatabase.DBManager;

public class set_alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Set Your Alarm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitle("Set Your Alarm");
        //DBManager.initialize(getApplicationContext());
    }
}
