package me.solo_team.futureleader.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    public String photo;
    public int peerId;
    public String name;

    public Message lastMessage;
    public int ownerId;

    public List<ChatMember> members = new ArrayList<>();


    private boolean chatPrivate = false;

    public Chat(JSONObject payload){
        try{
            if(!payload.isNull("image"))
                photo = payload.getString("image");
            peerId = payload.getInt("id");
            ownerId = payload.getInt("owner_id");
            if(payload.has("last_message"))
                lastMessage = new Message(payload.getJSONObject("last_message"));
            JSONArray mem = payload.getJSONArray("members");
            name = payload.getString("title");
            for(int i=0;i<mem.length();i++){
                members.add(new ChatMember(mem.getJSONObject(i)));
            }
            if(payload.has("private"))
                if(payload.getInt("private")==1)
                    chatPrivate = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isPrivate() {
        return chatPrivate;
    }
}
