package com.charly.selfieup;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class AlarmClock extends AppCompatActivity {

    AnimationDrawable clock_animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                //Go to the set_alarm activity
                Intent setAlarm = new Intent(getApplicationContext(), set_alarm.class);
                startActivity(setAlarm);
            }
        });
        ImageView clockImage = (ImageView) findViewById(R.id.clock_fig);
        clockImage.setImageResource(R.drawable.animation_list);
        clock_animation = (AnimationDrawable) clockImage.getDrawable();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
       // ImageView clockImage = (ImageView) findViewById(R.id.clock_fig);
        //clockImage.setBackgroundResource(R.drawable.animation_list);
        //clock_animation = (AnimationDrawable) clockImage.getBackground();
        clock_animation.start();

    }
}
