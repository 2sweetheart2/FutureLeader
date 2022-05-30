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
    public String achievementsIds;
    public int adminStatus = 0;
    public JSONArray achievements;
    public HashMap<String, String> editedFieldsTypes = new HashMap<String, String>() {{
        put("город", "text");
        put("телефон", "phone");
        put("telegram", "text");
        put("whatsapp", "phone");
    }};


    public void addFields(String fields) {
        for (String i : fields.split("&")) {
            try {
                user_fields.put(i.split("=")[0], i.split("=")[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
