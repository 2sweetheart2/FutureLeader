package me.solo_team.futureleader.Objects;

public class Time {
    public int hour;
    public int minute;
    public int sec;

    public Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public Time(int hour, int minute, int sec){
        this.hour = hour;
        this.minute = minute;
        this.sec = sec;
    }


    public Time(String time){
        this.hour = Integer.parseInt(time.split(":")[0]);
        this.minute = Integer.parseInt(time.split(":")[1]);
    }

    public Time(long time) {

    }

    public String toStr(){
        String timeMinute = ""+minute;
        if(minute<10)
            timeMinute = "0"+minute;
        return hour+":"+timeMinute;
    }
}
