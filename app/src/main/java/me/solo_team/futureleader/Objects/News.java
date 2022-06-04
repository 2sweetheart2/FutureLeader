package me.solo_team.futureleader.Objects;

public class News {
    public int id;
    public String photoUrl;
    public String title;
    public int viewCount;

    public News(int id,String photoUrl,String title){
        this.id = id;
        this.photoUrl = photoUrl;
        this.title = title;
    }

    public News setViewCount(int count){
        this.viewCount = count;
        return this;
    }

}
