package me.solo_team.futureleader.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Surveys {



    public ChatMember creator;
    public int id;
    public String name;
    public Date date;
    public List<SurveysObject> fields = new ArrayList<>();
    public List<String> answers = new ArrayList<>();

    public Surveys(JSONObject payload){
        try{
            id = payload.getInt("id");
            name = payload.getString("name");
            date = new Date(payload.getString("date"));
            JSONArray fields = new JSONArray(payload.getString("fields"));
            for(int i =0;i<fields.length();i++){
                this.fields.add(new SurveysObject(fields.getJSONObject(i)));
            }
            if(payload.has("answers"))
                answers.addAll(Arrays.asList(payload.getString("answers").split("\\{\\{\\{<--->\\}\\}\\}")));
            if(payload.has("creator"))
                creator = new ChatMember(payload.getJSONObject("creator"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class SurveysObject{
        public String type;
        public String name;
        public String extras;

        public SurveysObject(JSONObject payload){
            try {
                type = payload.getString("type");
                name = payload.getString("name");
                if(payload.has("extras"))
                    extras = payload.getString("extras");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}
