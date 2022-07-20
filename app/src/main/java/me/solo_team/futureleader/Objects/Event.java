package me.solo_team.futureleader.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {

    public Date date;
    public Time timeFrom;
    public Time timeTo;
    public String name;
    public int id;
    public String urlLogo;
    public String description;
    public int maxMembers;
    public String type;
    public int freePlace;


    public String creatorName;
    public String creatorPhoto;
    public int creatorId;

    public JSONObject payload;

    public Event(JSONObject payload) {
        try {
            this.payload = payload;
            date = new Date(payload.getString("date"));
            timeFrom = new Time(payload.getString("time_from"));
            timeTo = new Time(payload.getString("time_to"));
            name = payload.getString("name");
            description = payload.getString("description");
            urlLogo = payload.getString("url_logo");
            id = payload.getInt("id");
            maxMembers = payload.getInt("max_members");
            type = payload.getString("type");
            freePlace = payload.getInt("free_place");

            JSONObject creator = payload.getJSONObject("from");
            creatorName = creator.getString("name");
            creatorPhoto = creator.getString("photo");
            creatorId = creator.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
