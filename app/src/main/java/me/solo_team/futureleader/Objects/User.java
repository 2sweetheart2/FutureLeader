package me.solo_team.futureleader.Objects;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@JsonObject
public class User {
    @JsonField(name = "first_name")
    public String first_name;
    @JsonField(name = "last_name")
    public String last_name;
    @JsonField(name = "status")
    public String status;
    @JsonField(name = "age")
    public int age;
    @JsonField(name = "info_fields")
    public JSONObject info_fields;
    @JsonField(name="achievements_fields")
    public JSONArray achievements_fields;
    @JsonField(name = "admin_status")
    public int admin_status=0;
    public HashMap<String,String> editedFieldsTypes = new HashMap<String, String>(){{
        put("город","text");
        put("телефон","phone");
        put("telegram","text");
        put("whatsapp","phone");
    }};

    public LinkedHashMap<String, String> enums;

    public List<Achievement> getAchivements(){
        List<Achievement> ls = new ArrayList<>();
        for(int i =0;i<achievements_fields.length();i++){
            try {
                ls.add(LoganSquare.parse(achievements_fields.getJSONObject(i).toString(), Achievement.class));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return ls;
    }

    public enum FIELDS{

    }

}
