package me.solo_team.futureleader.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Usluga {

    public String image;
    public String name;
    public String description;
    public List<JSONObject> fields = new ArrayList<>();

    public Usluga(String image, String name, String description, JSONArray fields){
        this.image = image;
        this.name = name;
        this.description = description;
        for(int i=0;i<fields.length();i++){
            try {
                this.fields.add(fields.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> fieldsToString(){
        ArrayList<String> ar = new ArrayList<>();
        for(JSONObject o : fields){
            ar.add(o.toString());
        }
        return ar;
    }
}
