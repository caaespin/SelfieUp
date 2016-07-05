package com.charly.selfieup;

//Used the following git repo:
//https://github.com/fafaldo/FABToolbar  //used it for the toolbar expansion thing
//https://github.com/amulyakhare/TextDrawable //used this for letter for the days

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.charly.selfieup.alarmdatabase.DBManager;
import com.charly.selfieup.alarmdatabase.Alarm;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class set_alarmFragment extends Fragment {

    //Array of days selected, and a flag bool
    Boolean[] selected = {false, false, false, false, false, false, false};
    Boolean flag = true;
    //Array of the initials for the days
    String[] dayInitial = {"M", "T", "W", "Th", "F", "S", "Su"};
    View rootView;
    //Declare the objects;
    ImageView close;
    TextDrawable close_drawable;
    //Declare the arrays
    ImageView[] dayImage = new ImageView[7];
    TextDrawable[] dayDrawable = new TextDrawable[7];
    int[] imageId = {R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday, R.id.sunday};
    //Buttons and time picker
    Button choose, save;
    TimePicker timepick;
    //Counter loop variables
    int i;
    //Ints for the hour and minute to be set
    Integer mHour, mMinute;
    //Intent variables
    Intent soundActivity;
    //Alarm to be set
    Alarm mAlarm;
    //Progress diaglos
    ProgressDialog pd;


    public set_alarmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Defines the rootView
        rootView = inflater.inflate(R.layout.fragment_set_alarm, container, false);
        //Images to be used for the days and close button, as well as the close button and the FAB
        //close = (ImageView) rootView.findViewById(R.id.close);
        setBarImages();
        //Initialize the drawables
        setInitialDrawables();
        //Set the listeners for the days and the button for opening the app toolbar,
        //as well as the close button.
        setDayListeners();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMinute = timepick.getMinute();
            mHour = timepick.getHour();
        }
        else{
            mMinute = timepick.getCurrentMinute();
            mHour = timepick.getCurrentMinute();
        }


        return rootView;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 4) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if(uri!=null){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(Settings.System.canWrite(getContext())) {
                        RingtoneManager.setActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_ALARM, uri);
                        //TextView ringtoneName = (TextView) rootView.findViewById(R.id.choose_ringtone);
                        choose.setText((CharSequence) RingtoneManager.getRingtone(getContext(), uri).getTitle(getContext()));

                    }
                    else {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                Log.wtf("Request code", Integer.toString(requestCode));
            }
        }
    }

    private void setBarImages() {
        for(i = 0; i<imageId.length; i++){
            dayImage[i] = (ImageView) rootView.findViewById(imageId[i]);
        }
        //fabA = (FloatingActionButton) rootView.findViewById(R.id.fabtoolbar_fab);
        //layout = (FABToolbarLayout) rootView.findViewById(R.id.fabtoolbar);
        choose = (Button) rootView.findViewById(R.id.choose);
        save = (Button) rootView.findViewById(R.id.save);
        timepick = (TimePicker) rootView.findViewById(R.id.timePicker);
    }

    //Private method for initializing in the beggining the day drawables and the closing mark
    private void setInitialDrawables() {
        //Set the drawables for the days
        for(i = 0; i < 7; i++){
            dayDrawable[i] = TextDrawable.builder().beginConfig().bold().endConfig()
                    .buildRoundRect(dayInitial[i], Color.parseColor("#3333ff"), 10);
            dayImage[i].setImageDrawable(dayDrawable[i]);
        }
        //Set the drawables for the closing icon.


        /**
        close_drawable = TextDrawable.builder().beginConfig().bold().withBorder(7).endConfig()
                .buildRound("X", Color.parseColor("#ffb433"));
        close.setImageDrawable(close_drawable);

        **/
    }


    //Private method for setting the Listeners for each day.
    private void setDayListeners() {
        //Go through each day and initialize the listeners
        for(i = 0; i<7 ; i++){
            final int j = i;
            dayImage[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //Highlight the day and add it to the list of those days that have been selected
                    if(!selected[j]){
                        dayDrawable[j] = TextDrawable.builder().beginConfig().bold().endConfig()
                                .buildRoundRect(dayInitial[j], Color.parseColor("#24b38f"), 10);
                        selected[j] = true;
                    }
                    else{
                        dayDrawable[j] = TextDrawable.builder().beginConfig().bold().endConfig()
                                .buildRoundRect(dayInitial[j], Color.parseColor("#3333ff"), 10);
                        selected[j] = false;
                    }
                    dayImage[j].setImageDrawable(dayDrawable[j]);
                }
            });
        }

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundActivity = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                soundActivity.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                soundActivity.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                startActivityForResult(soundActivity, 4);
            }
        });

        /**
        fabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the final layout
                //layout.show();
                soundActivity = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                soundActivity.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                soundActivity.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                startActivityForResult(soundActivity, 4);
            }
        });

        **/

        /**
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layout.hide();
            }
        });
        **/
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAlarm = new Alarm(mHour.toString() + ":" + mMinute.toString(), selected);
                new AddAlarmToCalendar().execute(mAlarm);
                NavUtils.navigateUpFromSameTask(getActivity());

            }
        });

        timepick.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mMinute = minute;
                mHour = hourOfDay;
            }
        });

    }


    private class AddAlarmToCalendar extends AsyncTask<Alarm, Void, Void> {


        @Override
        protected Void doInBackground(Alarm... alarm) {

            List<Alarm> alarms = DBManager.instance().getAllAlarms();
            if(alarms.size() >= 10){
                flag = false;
                return null;
            }

            DBManager.instance().insertAlarm(mAlarm);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();
            if(!flag){
                Toast.makeText(getContext(), "You can only have up 10 alarms!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(getContext(), "Adding your alarm", "Please wait...");
        }
    }



}
