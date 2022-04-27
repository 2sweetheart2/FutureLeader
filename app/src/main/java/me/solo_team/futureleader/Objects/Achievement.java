package me.solo_team.futureleader.Objects;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Achievement {
    @JsonField(name = "name")
    public String name;
    @JsonField(name = "description")
    public String description;
    @JsonField(name = "image_url")
    public String image_url;
    @JsonField(name = "coins")
    public String coins;
}
