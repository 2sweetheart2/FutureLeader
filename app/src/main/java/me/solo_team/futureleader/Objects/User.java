package me.solo_team.futureleader.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class User {
    public String firstName;
    public String lastName;
    public String status;
    public int age;
    public int id;
    public int currency;
    public String profilePictureLink;
    public String token;
    public JSONObject user_fields = new JSONObject();
    public List<Field> fields = new ArrayList<>();
    public String achievementsIds;
    public int adminStatus = 0;
    public JSONArray achievements;
    public FieldsStuff fieldsStuff;
    public String mobileToken;

    public Permission permission;


    public User(JSONObject payload) {
        try {
            this.firstName = payload.getString("first_name");
            this.lastName = payload.getString("last_name");
            this.profilePictureLink = payload.getString("profile_picture");

            this.achievementsIds = payload.getString("achievement_ids");
            this.adminStatus = payload.getInt("admin_status");
            try {
                this.age = payload.getInt("age");
            } catch (Exception ignored) {
            }
            JSONObject field_stuff = payload.getJSONObject("fields_stuff");
            this.addFields(field_stuff.getString("fields"));
            this.fieldsStuff = new FieldsStuff(this.fields, this.convertToFields(field_stuff.getString("can_edit_fields")), field_stuff.getInt("max_fields_size"));
            this.id = payload.getInt("id");
            this.status = payload.getString("status");
            if(!payload.isNull("token"))
                this.token = payload.getString("token");
            this.currency = payload.getInt("currency");
            permission = new Permission(payload.getJSONObject("permission"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


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

    public String toChatMemder() {
        return "{\"first_name\":\"" + firstName + "\",\"last_name\":\"" + lastName + "\",\"profile_picture\":\"" + profilePictureLink + "\",\"id\":\"" + id + "\"}";
    }


}
