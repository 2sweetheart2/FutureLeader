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


    public String toStr(){
        String timeMinute = ""+minute;
        if(minute<10)
            timeMinute = "0"+minute;
        return hour+":"+timeMinute;
    }

    public String toString(boolean useSec) {
        String timeMinute = ""+minute;
        String timeHour = ""+hour;
        String timeSec = ""+sec;
        if(minute<10)
            timeMinute = "0"+minute;
        if(hour<10)
            timeHour = "0"+hour;
        if(sec<10)
            timeSec = "0"+sec;
        if(useSec)
            return timeHour+":"+timeMinute+":"+timeSec;
        else
            return timeHour+":"+timeMinute;
    }
}
