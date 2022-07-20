package me.solo_team.futureleader.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class User {
    public String firstName;
    public String lastName;
    public String status;
    public int age;
    public int id;
    public String profilePictureLink;
    public String token;
    public JSONObject user_fields = new JSONObject();
    public List<Field> fields = new ArrayList<>();
    public String achievementsIds;
    public int adminStatus = 0;
    public JSONArray achievements;
    public FieldsStuff fieldsStuff;
    public HashMap<String, String> editedFieldsTypes = new HashMap<String, String>() {{
        put("город", "text");
        put("телефон", "phone");
        put("telegram", "text");
        put("whatsapp", "phone");
    }};


    public List<Field> convertToFields(String fields_) throws JSONException {
        List<Field> fieldsList = new ArrayList<>();
        JSONArray fields = new JSONArray(fields_);
        for(int i =0;i<fields.length();i++)
        {
            JSONObject l = fields.getJSONObject(i);
            Field field = new Field(l.getString("name"),l.getString("visual_name"),l.getString("value"),l.getString("type"),l.getBoolean("can_edit"));
            fieldsList.add(field);
        }
        return fieldsList;
    }

    public void addFields(String fields_) throws JSONException {
        this.fields = convertToFields(fields_);
    }


    public LinkedHashMap<String, String> enums;

    public List<Achievement> getAchivements() {
        List<Achievement> ls = new ArrayList<>();
//        for (int i = 0; i < achievementsFields.length(); i++) {
//            try {
//                Achievement achievement = new Achievement();
//                achievement.coins = achievementsFields.getJSONObject(i).getInt("coins");
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            }
//        }
        return ls;
    }

    public String getFields() {
        StringBuilder text = new StringBuilder();
        for (Iterator<String> it = user_fields.keys(); it.hasNext(); ) {
            String s = it.next();
            try {
                text.append(s).append("=").append(user_fields.getString(s)).append("&");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return text.toString().substring(0, text.toString().length() - 1);
    }


}
