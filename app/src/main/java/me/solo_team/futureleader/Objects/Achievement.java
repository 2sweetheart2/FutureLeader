package me.solo_team.futureleader.Objects;

import android.graphics.Bitmap;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Achievement {
    @JsonField(name="id")
    public int id;
    @JsonField(name = "name")
    public String name;
    @JsonField(name = "description")
    public String description;
    @JsonField(name = "image_url")
    public String image_url;
    @JsonField(name = "coins")
    public int coins;

    public Bitmap imageBitMap = null;
}
