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

    CDateTime dateTime;

    public ChatMember owner;

    public int limit = -1;

    public Bitmap imageBitMap = null;

    public Achievement(JSONObject payload, boolean hasUser){
        try{
            System.out.println(payload);
            name = payload.getString("name");
            description = payload.getString("description");
            if(payload.has("id"))
                id = payload.getInt("id");
            image_url = payload.getString("image_url");
            coins = payload.getInt("coins");
            if(payload.has("limited"))
                limit = payload.getInt("limited");
            if(payload.has("datetime"))
                try {
                    dateTime = new CDateTime(payload.getString("datetime"), true);
                }catch (ArrayIndexOutOfBoundsException ignored){
                    dateTime = new CDateTime(payload.getString("datetime"));
                }
            if(hasUser)
                owner = new ChatMember(payload);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
