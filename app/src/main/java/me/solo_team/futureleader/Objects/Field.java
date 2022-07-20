package me.solo_team.futureleader.Objects;

public class Field {
    public String name;
    public String visualName;
    public String value;
    public String type;
    public boolean canEdit = false;


    public Field(String name, String visualName, String value,String type, boolean canEdit){
        this.name = name;
        this.type = type;
        this.value = value;
        this.visualName = visualName;
        this.canEdit = canEdit;
    }
}
