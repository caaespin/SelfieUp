package com.charly.selfieup;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charly.selfieup.alarmdatabase.Alarm;
import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

/**
 * Created by Charly on 7/4/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<Alarm> alarmDataSource;

    public RecyclerAdapter(ArrayList<Alarm> dataArgs){
        alarmDataSource = dataArgs;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm mAlarm = alarmDataSource.get(position);
        String[] time = mAlarm.getTime().split(":");
        String days = mAlarm.getDays();
        Integer hour = Integer.parseInt(time[0]);
        String correctHour = hour > 12 ? Integer.toString(hour - 12) +":"+ time[1] +"pm" : Integer.toString(hour)+":"+ time[1] +"am";
        TextDrawable tdHour = TextDrawable.builder().beginConfig().bold().textColor(Color.parseColor("#7e7e7e")).endConfig()
                .buildRoundRect(correctHour, Color.TRANSPARENT, 10);
        TextView[] daysArray = {holder.vMonday, holder.vTuesday, holder.vWednesday, holder.vThursday, holder.vFriday, holder.vSaturday, holder.vSunday};
        highLightDays(daysArray, days);
        //TextDrawable tdMinute = TextDrawable.builder().beginConfig().bold().endConfig()
        //        .buildRoundRect(time[1], Color.parseColor("#3333ff"), 10);;
        holder.vHour.setImageDrawable(tdHour);

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

        public ViewHolder(View cardView) {
            super(cardView);
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
