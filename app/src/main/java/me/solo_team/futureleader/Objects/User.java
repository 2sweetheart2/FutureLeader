package me.solo_team.futureleader.Objects;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public JSONArray info_fields;
    @JsonField(name="achievements_fields")
    public JSONArray achievements_fields;
    @JsonField(name = "admin_status")
    public int admin_status=0;


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

}
