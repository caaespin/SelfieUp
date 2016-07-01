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
        final FloatingActionButton fabA = (FloatingActionButton) rootView.findViewById(R.id.fabtoolbar_fab);
        final FABToolbarLayout layout = (FABToolbarLayout) rootView.findViewById(R.id.fabtoolbar);
        fabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextDrawable monday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("M", Color.parseColor("#6633ff"), 10); // radius in px

                TextDrawable tuesday_drawable = TextDrawable.builder().beginConfig().bold().endConfig()
                        .buildRoundRect("T", Color.parseColor("#3333ff"), 10);
                //ImageView image = (ImageView) rootView.findViewById(R.id.monday);
                monday.setImageDrawable(monday_drawable);
                tuesday.setImageDrawable(tuesday_drawable);
                layout.show();
            }
        });
        return rootView;

    }

}
