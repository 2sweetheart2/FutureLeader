package me.solo_team.futureleader.Objects;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;


public class Achievement {
    public int id;
    public String name;
    public String description;
    public String image_url;
    public int coins;

    public ChatMember owner;

    public int limit = -1;

    public Bitmap imageBitMap = null;

    public Achievement(JSONObject payload, boolean hasUser){
        try{
            id = payload.getInt("id");
            name = payload.getString("name");
            description = payload.getString("description");
            image_url = payload.getString("image_url");
            coins = payload.getInt("coins");
            limit = payload.getInt("limited");
            if(hasUser)
                owner = new ChatMember(payload);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
