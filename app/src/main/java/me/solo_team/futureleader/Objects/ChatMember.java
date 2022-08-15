package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatMember {
    public String firstName;
    public String lastName;
    public int userId;
    public String profilePicture;

    public ChatMember(JSONObject payload){
        try {
            firstName = payload.getString("first_name");
            lastName = payload.getString("last_name");
            userId = payload.getInt("id");
            if(!payload.isNull("profile_picture"))
                profilePicture = payload.getString("profile_picture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toChatMemder(){
        return "{\"first_name\":\""+firstName+"\",\"last_name\":\""+lastName+"\",\"profile_picture\":\""+profilePicture+"\",\"id\":\""+userId+"\"}";
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }
}
