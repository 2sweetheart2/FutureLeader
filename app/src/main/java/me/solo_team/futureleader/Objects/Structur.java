package me.solo_team.futureleader.Objects;

import java.util.ArrayList;
import java.util.List;

public class Structur {
    public List<Structur> chields = new ArrayList<>();
    public int id;
    public String name;
    public Integer userId;

    public Structur(String name,int id, Integer userId){
        this.name = name;
        this.id = id;
        this.userId = userId;
    }

    public void addChiled(Structur structur){
        chields.add(structur);
    }

    public boolean hasChield(){
        return chields.size()!=0;
    }
}
