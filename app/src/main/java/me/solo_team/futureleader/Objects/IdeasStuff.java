package me.solo_team.futureleader.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IdeasStuff {

    public List<Idea> userIdeas = new ArrayList<>();
    public List<Idea> waitIdea = new ArrayList<>();

    public IdeasStuff(JSONArray userIdeas, JSONArray waitIdea){
        try {
            this.userIdeas = forceIdea(userIdeas);
            this.waitIdea = forceIdea(waitIdea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private List<Idea> forceIdea(JSONArray ideas) throws JSONException {
        List<Idea> ideaslist = new ArrayList<>();
        for(int i=0;i<ideas.length();i++){
            JSONObject id = ideas.getJSONObject(i);
            Idea idea;
            if(id.has("status"))
                idea = new Idea(id.getString("text"),id.getString("label"),id.getInt("user_id"),id.getString("status"));
            else
                idea = new Idea(id.getString("text"),id.getString("label"),id.getInt("user_id"));
            ideaslist.add(idea);
        }
        return ideaslist;
    }


    public class Idea{
        public String text;
        public String label;
        public int user_id;
        public String status;


        public Idea(String text, String label, int user_id){
            this.text = text;
            this.label = label;
            this.user_id = user_id;
            this.status = "wait";
        }

        public Idea(String text, String label, int user_id, String status){
            this.text = text;
            this.label = label;
            this.user_id = user_id;
            this.status = status;
        }
    }
}
