package com.charly.selfieup;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charly.selfieup.alarmdatabase.Alarm;
import com.amulyakhare.textdrawable.TextDrawable;
import com.charly.selfieup.alarmdatabase.DBManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Charly on 7/4/16.
 *
 * Current problem: 12:00am is being shown as 0:0am; 12:05am is being shown as 12:5am
 *                  :12:00pm is being saved as 12:00am
 *                  am and pm marks are not working appropriately. Need to debug
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<Alarm> alarmDataSource;
    private static ArrayList<Alarm> alarmsSelected = new ArrayList<Alarm>();
    private static ArrayList<CardView> cvsSelected = new ArrayList<CardView>();
    private static ArrayList<View> vsSelected = new ArrayList<View>();

    public RecyclerAdapter(ArrayList<Alarm> dataArgs, Activity myActivity){
        alarmDataSource = dataArgs;
        mActivity = myActivity;
    }

    private Context context;
    private ViewGroup vG;
    private ActionMode mActionMode;
    private Activity mActivity;
    private Integer cardsSelected = 0;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        vG = parent;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Alarm mAlarm = alarmDataSource.get(position);
       // mAlarm.setPosition(position); //testing position within the alarm
        String[] time = mAlarm.getTime().split(":");
        String days = mAlarm.getDays();
        Integer hour = Integer.parseInt(time[0]);

        //String correctHour = hour > 12 ? Integer.toString(hour - 12) +":"+ time[1] +"pm" : Integer.toString(hour)+":"+ time[1] +"am";

        String correctHour = getRightHour(time[0], time[1]);
        TextDrawable tdHour = TextDrawable.builder().beginConfig().bold().textColor(Color.parseColor("#7e7e7e")).endConfig()
                .buildRoundRect(correctHour, Color.TRANSPARENT, 10);
        TextView[] daysArray = {holder.vMonday, holder.vTuesday, holder.vWednesday, holder.vThursday, holder.vFriday, holder.vSaturday, holder.vSunday};
        highLightDays(daysArray, days);
        //TextDrawable tdMinute = TextDrawable.builder().beginConfig().bold().endConfig()
        //        .buildRoundRect(time[1], Color.parseColor("#3333ff"), 10);;
        holder.vHour.setImageDrawable(tdHour);
        holder.cV.setCardBackgroundColor(Color.WHITE); //Testings
        holder.cV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above]
                cardsSelected++;
                mActionMode = mActivity.startActionMode(mActionModeCallback);
                CardView cview = (CardView) v.findViewById(R.id.card_view);

                cview.setCardBackgroundColor(Color.DKGRAY);
                v.setSelected(true);
                vsSelected.add(v);
                alarmsSelected.add(mAlarm);
                cvsSelected.add(cview);

                //mActionMode.setTitle(Integer.toString(cardsSelected) + " selected");
                return true;

            }
        });
        holder.cV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mActionMode == null) {
                    return;
                }
                CardView cview = (CardView) v.findViewById(R.id.card_view);
                if(!v.isSelected()){
                    cview.setCardBackgroundColor(Color.DKGRAY);
                    v.setSelected(true);
                    alarmsSelected.add(mAlarm);
                    cvsSelected.add(cview);
                    vsSelected.add(v);
                    cardsSelected++;
                    //mActionMode.setTitle(Integer.toString(cardsSelected++) + " selected");
                }
                else{
                    cview.setCardBackgroundColor(Color.WHITE);
                    v.setSelected(false);
                    alarmsSelected.remove(mAlarm);
                    cvsSelected.remove(cview);
                    vsSelected.remove(v);
                    cardsSelected--;
                    //mActionMode.setTitle(Integer.toString(cardsSelected--) + " selected");
                }
                mActionMode.setTitle(Integer.toString(cardsSelected) + " selected");
                return;
            }
        });
    }

    private String getRightHour(String mH, String mM) {
        Integer numericHour = Integer.parseInt(mH);
        String am_pm = (numericHour >= 12 ? "pm" :  "am");
        numericHour = (numericHour > 12 ? (numericHour-12) :  numericHour);
        if(numericHour == 0) numericHour = 12;
        String hour = Integer.toString(numericHour);
        if(hour.length()<2){
            hour = "0" + hour;
        }

        if(mM.length()<2){
            mM = "0" + mM;
        }
        return (hour + ":" + mM + am_pm);
    }

    private void highLightDays(TextView[] daysArray, String days) {
        for(int i = 0; i < 7; i++){
            if(days.contains(Integer.toString(i))){
                daysArray[i].setTypeface(null, Typeface.BOLD);
                daysArray[i].setTextColor(Color.parseColor("#24b38f"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return alarmDataSource.size();
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu, menu);
            mode.setTitle(Integer.toString(cardsSelected) + " selected");
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }


        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_delete:
                    //deleteCurrentItems();
                    deleteCurrentItems(true, alarmsSelected);
                    mode.finish(); // Action picked, so close the CAB
                    mActionMode = null;
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            //Have to implement method for setting all the selected items back to normal
            deleteCurrentItems(false, alarmsSelected);
            cardsSelected = 0;
            mActionMode = null;
        }


        private void deleteCurrentItems(Boolean trashCanClicked, ArrayList<Alarm> selectedAlarms){
            for(int i =0; i<selectedAlarms.size(); i++){
                if(trashCanClicked){
                    DBManager.instance().deleteAlarm(selectedAlarms.get(i).getID());
                    alarmDataSource.remove(selectedAlarms.get(i));
                }
                else{
                    cvsSelected.get(i).setCardBackgroundColor(Color.WHITE);
                    vsSelected.get(i).setSelected(false);
                }

            }
            cardsSelected = 0;
            cvsSelected.clear();
            vsSelected.clear();
            selectedAlarms.clear();
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView vHour;
        //protected ImageView vMinute;
        protected ImageView vDays;
        protected TextView vMonday;
        protected TextView vTuesday;
        protected TextView vWednesday;
        protected TextView vThursday;
        protected TextView vFriday;
        protected TextView vSaturday;
        protected TextView vSunday;
        protected CardView cV;

        public ViewHolder(View cardView) {
            super(cardView);
            cV = (CardView) cardView.findViewById(R.id.card_view);
            vHour = (ImageView) cardView.findViewById(R.id.hourIs);
            //vMinute = (ImageView) cardView.findViewById(R.id.minuteIs);
            vDays = (ImageView) cardView.findViewById(R.id.daysAre);
            vMonday = (TextView) cardView.findViewById(R.id.mTxtV);
            vTuesday = (TextView) cardView.findViewById(R.id.tTxtV);
            vWednesday = (TextView) cardView.findViewById(R.id.wTxtV);
            vThursday = (TextView) cardView.findViewById(R.id.thTxtV);
            vFriday = (TextView) cardView.findViewById(R.id.fTxtV);
            vSaturday = (TextView) cardView.findViewById(R.id.sTxtV);
            vSunday = (TextView) cardView.findViewById(R.id.suTxtV);

        }
    }
}
