package me.solo_team.futureleader.Objects;

import com.google.type.DateTime;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CDateTime {
    public Date date;
    public Time time;

    public CDateTime(DateTime dateTime){
        date = new Date(dateTime.getDay(),dateTime.getMonth(),dateTime.getYear());
        time = new Time(dateTime.getHours(),dateTime.getMinutes(),dateTime.getSeconds());
    }
    public CDateTime(String dateTime){
        String[] days = dateTime.substring(0,10).split("-");
        String[] times = dateTime.substring(11,18).split(":");
        date = new Date(Integer.parseInt(days[2]),Integer.parseInt(days[1]),Integer.parseInt(days[0]));
        time = new Time(Integer.parseInt(times[2]),Integer.parseInt(times[1]),Integer.parseInt(times[0]));
    }
    public CDateTime(String dateTime,boolean hhui){
        String[] days = dateTime.substring(0,10).split("-");
        String[] times = dateTime.substring(11,19).split(":");
        date = new Date(Integer.parseInt(days[2]),Integer.parseInt(days[1]),Integer.parseInt(days[0]));
        time = new Time(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));
    }

    @NotNull
    public String toString(boolean userSec){
        return date.toVisibleStr()+' '+time.toString(userSec);
    }

}
