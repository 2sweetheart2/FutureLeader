package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Dr {
    public Date date;
    public int userId;
    public String userName;
    public String userProfilePicture;

    public Dr(JSONObject payload){
        try{
            date = new Date(payload.getString("date"));
            userId = payload.getInt("user_id");
            userName = payload.getString("user_name");
            userProfilePicture = payload.getString("user_profile_picture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
