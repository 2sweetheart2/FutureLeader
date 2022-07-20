package me.solo_team.futureleader.Objects;

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
        return day+"/"+month+'/'+year;
    }
    public String toVisibleStr(){return day+"."+month+'.'+year;}
}
