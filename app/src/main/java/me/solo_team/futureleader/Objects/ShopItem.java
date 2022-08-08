package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopItem {
    public String name;
    public String description = null;
    public int id;
    public String photo;
    public int count;
    public int maxCount;
    public boolean auto = false;
    public int cost;


    public ShopItem(JSONObject payload){
        try{
            name = payload.getString("name");
            if(!payload.isNull("description"))
                description = payload.getString("description");
            id = payload.getInt("id");
            photo = payload.getString("image");
            count = payload.getInt("count");
            maxCount = payload.getInt("max_count");
            cost = payload.getInt("cost");
            if(payload.has("auto"))
                auto = payload.getBoolean("auto");
        } catch (JSONException e) {
            System.err.println(payload);
            e.printStackTrace();
        }
    }
}
