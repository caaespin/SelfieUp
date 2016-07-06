package com.charly.selfieup;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.charly.selfieup.alarmdatabase.DBManager;
import com.charly.selfieup.alarmdatabase.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


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

        DBManager.initialize(getApplicationContext());
        alarms = (ArrayList<Alarm>) DBManager.instance().getAllAlarms();
        //recyclerAdapter = new RecyclerAdapter(alarms);

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private AnimationDrawable clock_animation;
        private RecyclerView recyclerView;
        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(alarms);

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //You can use getArguments() to get which fragment should be used and then inflate it
            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


            View rootView = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    ImageView clockImage = (ImageView) rootView.findViewById(R.id.clock_fig);
                    clockImage.setImageResource(R.drawable.animation_list);
                    clock_animation = (AnimationDrawable) clockImage.getDrawable();
                    clock_animation.start();
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_list_of_alarms, container, false);
                    ArrayList<Alarm> alarms = (ArrayList<Alarm>) DBManager.instance().getAllAlarms();
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(alarms);
                    recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(recyclerAdapter);
                    //recyclerAdapter = new RecyclerAdapter(cardsEventData);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    clockImage = (ImageView) rootView.findViewById(R.id.clock_fig);
                    clockImage.setImageResource(R.drawable.animation_list);
                    clock_animation = (AnimationDrawable) clockImage.getDrawable();
                    clock_animation.start();
                    break;
            }

            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //ImageView clockImage = (ImageView) rootView.findViewById(R.id.clock_fig);
            //clockImage.setImageResource(R.drawable.animation_list);
            //clock_animation = (AnimationDrawable) clockImage.getDrawable();
            //clock_animation.start();
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
