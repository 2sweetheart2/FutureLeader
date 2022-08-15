package me.solo_team.futureleader.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

public class Audio {
    public String name;
    public String author;
    public int id;
    public int userId;
    public String url;
    public String urlPhoto;

    public boolean liked = false;

    public Bitmap imageBitmap;

    public long duratation=0;

    private final Context context;

    public Audio(JSONObject json, Context context){
        this.context = context;
        try{
            name = json.getString("name");
            author = json.getString("author");
            id = json.getInt("id");
            if(json.has("user_id"))
                userId = json.getInt("user_id");
            url = json.getString("url");
            urlPhoto = json.getString("photo");
            if(json.has("like"))
                liked = json.getBoolean("like");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        return "{\"name\":\""+name+"\",\"author\":\""+author+"\",\"id\":\""+id+"\",\"url\":\""+url+"\",\"photo\":\""+urlPhoto+"\",\"like\":\""+liked+"\"}";
    }

}
