package com.charly.selfieup.alarmdatabase;

/**
 * Created by Charly on 7/3/16.
 */
public class Alarm {

    private long ID;
    private String time;
    private String days;  // it would be in the format of 0123..etc like for exampple, for MWF, it would be 024


    public Alarm() {
    }

    public Alarm(String time, Boolean[] daysArr) {
        this.setTime(time);
        this.setDays(daysArr);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Boolean[] getDays() {
        Boolean[] daysArr = new Boolean[7];
        for(int i=0; i<daysArr.length; i++){
            //If the int is present, set it to true, otherwise, set it to false
            if(days.contains(Integer.toString(i))){
                daysArr[i] = true;
            }
            else{
                daysArr[i] = false;
            }
        }
        return daysArr;
    }

    public void setDays(Boolean[] days) {
        for(int i=0; i<days.length; i++){
            if(days[i]){
                this.days += Integer.toString(i);
            }
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
