package me.solo_team.futureleader.Objects;

import me.solo_team.futureleader.stuff.Utils;

public class Date {
    public int day;
    public int month;
    public int year;

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String date){
        String[] d = date.split("/");
        day = Integer.parseInt(d[0]);
        month = Integer.parseInt(d[1]);
        year = Integer.parseInt(d[2]);
    }

    public String toStr(){
        String dayStr = ""+day;
        if(dayStr.length()==1) dayStr="0"+dayStr;
        String monthStr = ""+month;
        if(monthStr.length()==1) monthStr="0"+monthStr;
        return dayStr+"/"+monthStr+'/'+year;
    }
    public String toVisibleStrV2(){
        return Utils.parseDateBirthday(toStr());
    }
    public String toVisibleStr(){
        return toVisibleStr_();
    }

    private String toVisibleStr_(){
        String dayStr = ""+day;
        if(dayStr.length()==1) dayStr="0"+dayStr;
        String monthStr = ""+month;
        if(monthStr.length()==1) monthStr="0"+monthStr;
        return dayStr+"."+monthStr+'.'+year;
    }
}
