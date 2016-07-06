package com.charly.selfieup;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        TextDrawable tdHour = TextDrawable.builder().beginConfig().bold().endConfig()
                .buildRoundRect(time[0]+":"+time[1]+"pm", Color.parseColor("#3333ff"), 10);;
        //TextDrawable tdMinute = TextDrawable.builder().beginConfig().bold().endConfig()
        //        .buildRoundRect(time[1], Color.parseColor("#3333ff"), 10);;
        TextDrawable tdDays = TextDrawable.builder().beginConfig().bold().endConfig()
                .buildRoundRect("M T W Th F S Su", Color.parseColor("#3333ff"), 10);;
        holder.vHour.setImageDrawable(tdHour);
        //holder.vMinute.setImageDrawable(tdMinute);
        //holder.vDays.setImageDrawable(tdDays);

    }

    @Override
    public int getItemCount() {
        return alarmDataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView vHour;
        protected ImageView vMinute;
        protected ImageView vDays;

        public ViewHolder(View cardView) {
            super(cardView);
            vHour = (ImageView) cardView.findViewById(R.id.hourIs);
            //vMinute = (ImageView) cardView.findViewById(R.id.minuteIs);
            vDays = (ImageView) cardView.findViewById(R.id.daysAre);

        }
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
