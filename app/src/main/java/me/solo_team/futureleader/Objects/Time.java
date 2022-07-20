package me.solo_team.futureleader.Objects;

public class Time {
    public int hour;
    public int minute;

    public Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public Time(String time){
        this.hour = Integer.parseInt(time.split(":")[0]);
        this.minute = Integer.parseInt(time.split(":")[1]);
    }

    public String toStr(){
        String timeMinute = ""+minute;
        if(minute<10)
            timeMinute = "0"+minute;
        return hour+":"+timeMinute;
    }
}
