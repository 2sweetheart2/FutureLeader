package me.solo_team.futureleader.Objects;

/**
 * Кастомозированный класс для строковых типов, ибо я думал как бы сделать нормальный парсер с {@link org.json.JSONObject} но так ничего более разумного и не придумал
 */
public class CustomString {
    public String name;
    public String value;

    public CustomString(String name, String value){
        this.name = name;
        this.value = value;
    }
}
