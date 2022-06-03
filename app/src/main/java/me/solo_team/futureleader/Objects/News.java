package me.solo_team.futureleader.Objects;

public class News {
    public int id;
    public String name;
    public String photoUrl;
    public String text;
    public String title;
    public int viewCount=-1;
    public String viewsIds = null;

    public News(int id,String photoUrl,String text,String name,String title){
        this.id = id;
        this.photoUrl = photoUrl;
        this.text = text;
        this.name = name;
        this.title = title;
    }

    public News setViewCount(int count){
        this.viewCount = count;
        return this;
    }

    public News setViewsIds(String viewsIds){
        this.viewsIds = viewsIds;
        return this;
    }
}
