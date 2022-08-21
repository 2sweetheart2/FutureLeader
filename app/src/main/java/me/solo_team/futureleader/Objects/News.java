package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class News {
    public int id;
    public String photoUrl;
    public String title;
    public int viewCount;
    public int likes;

    public News(int id,String photoUrl,String title){
        this.id = id;
        this.photoUrl = photoUrl;
        this.title = title;
    }

    public News(JSONObject payload){
        try{
            id = payload.getInt("id");
            title = payload.getString("title");
            photoUrl = payload.getString("photo");
            viewCount = payload.getInt("views_count");
            likes = payload.getInt("likes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
