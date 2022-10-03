package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Postal {
    public int fromId;
    public int toId;
    public boolean visibility;
    public String photoUrl;
    public String text = null;


    public Postal(JSONObject payload){
        try{
            fromId = payload.getInt("from_id");
            toId = payload.getInt("to_id");
            if(payload.getInt("visibility") == 0)
                visibility = false;
            else
                visibility = true;
            photoUrl = payload.getString("photo_link");
            if(!payload.isNull("text"))
                text = payload.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
