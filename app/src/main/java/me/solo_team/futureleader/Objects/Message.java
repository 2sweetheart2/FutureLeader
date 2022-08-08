package me.solo_team.futureleader.Objects;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.type.DateTime;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class Message {

    public ChatMember author;
    public int authorId;
    public int messageId;
    public String text;
    public int peerId;
    public CDateTime dateTime;



    public Message(JSONObject payload){
        try{
            author = new ChatMember(payload.getJSONObject("author"));
            authorId = payload.getInt("author_id");
            peerId = payload.getInt("chat_id");
            dateTime = new CDateTime(payload.getString("datetime"));
            messageId = payload.getInt("id");
            text = payload.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public static class Sender{
        public String firstName;
        public String lastName;
        public String profilePicture;
        public int id;

        public Sender(JSONObject payload){
            try{
                firstName = payload.getString("first_name");
                lastName = payload.getString("last_name");
                profilePicture = payload.getString("profile_picture");
                id = payload.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
