package com.charly.selfieup;

//Used the following git repo:
//https://github.com/fafaldo/FABToolbar  //used it for the toolbar expansion thing
//https://github.com/amulyakhare/TextDrawable //used this for letter for the days

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class set_alarmFragment extends Fragment {

    public set_alarmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Defines the rootView
        final View rootView = inflater.inflate(R.layout.fragment_set_alarm, container, false);
        //Images to be used for the days
        final ImageView monday = (ImageView) rootView.findViewById(R.id.monday);
        final ImageView tuesday = (ImageView) rootView.findViewById(R.id.tuesday);
        final ImageView wednesday = (ImageView) rootView.findViewById(R.id.wednesday);
        final ImageView thursday = (ImageView) rootView.findViewById(R.id.thursday);
        final ImageView friday = (ImageView) rootView.findViewById(R.id.friday);
        final ImageView saturday = (ImageView) rootView.findViewById(R.id.saturday);
        final ImageView sunday = (ImageView) rootView.findViewById(R.id.sunday);
        final ImageView close = (ImageView) rootView.findViewById(R.id.close);
        final FloatingActionButton fabA = (FloatingActionButton) rootView.findViewById(R.id.fabtoolbar_fab);
        final FABToolbarLayout layout = (FABToolbarLayout) rootView.findViewById(R.id.fabtoolbar);
        fabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make the days of the week and the close tiles
                TextDrawable monday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("M", Color.parseColor("#3333ff"), 10);
                TextDrawable tuesday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("T", Color.parseColor("#3333ff"), 10);
                TextDrawable wednesday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("W", Color.parseColor("#3333ff"), 10);
                TextDrawable thursday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("Th", Color.parseColor("#3333ff"), 10);
                TextDrawable friday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("F", Color.parseColor("#3333ff"), 10);
                TextDrawable saturday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("S", Color.parseColor("#3333ff"), 10);
                TextDrawable sunday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("Su", Color.parseColor("#3333ff"), 10);
                TextDrawable close_drawable = TextDrawable.builder().beginConfig().bold().withBorder(7).endConfig()
                        .buildRound("X", Color.parseColor("#ffb433"));
                //Set the days and close tiles as the drawables.
                monday.setImageDrawable(monday_drawable);
                tuesday.setImageDrawable(tuesday_drawable);
                wednesday.setImageDrawable(wednesday_drawable);
                thursday.setImageDrawable(thursday_drawable);
                friday.setImageDrawable(friday_drawable);
                saturday.setImageDrawable(saturday_drawable);
                sunday.setImageDrawable(sunday_drawable);
                close.setImageDrawable(close_drawable);
                //Show the final layout
                layout.show();
            }
        });

        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               layout.hide();
            }
        });
        return rootView;

    }

}
