package com.charly.selfieup;

//Used the following git repo:
//https://github.com/fafaldo/FABToolbar  //used it for the toolbar expansion thing

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        View rootView = inflater.inflate(R.layout.fragment_set_alarm, container, false);
        final FloatingActionButton fabA = (FloatingActionButton) rootView.findViewById(R.id.fabtoolbar_fab);
        final FABToolbarLayout layout = (FABToolbarLayout) rootView.findViewById(R.id.fabtoolbar);
        fabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.show();
            }
        });
        return rootView;

    }

}
