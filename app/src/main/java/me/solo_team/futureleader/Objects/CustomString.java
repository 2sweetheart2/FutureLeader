package me.solo_team.futureleader.Objects;

/**
 * Кастомозированный класс для строковых типов, ибо я думал как бы сделать нормальный парсер с {@link org.json.JSONObject} но так ничего более разумного и не придумал
 */
public class CustomString {
    public String name;
    public String value;

    public int valueInt;

    public boolean isInt = false;

    public CustomString(String name, String value){
        this.name = name;
        this.value = value;
    }

    public CustomString(String name, int value){
        this.name = name;
        isInt=true;
        this.valueInt = value;
    }
}
