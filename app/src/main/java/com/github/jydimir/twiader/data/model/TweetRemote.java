package com.github.jydimir.twiader.data.model;

import com.google.gson.annotations.SerializedName;

public class TweetRemote {

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private long id;
    @SerializedName("text")
    private String text;
    @SerializedName("user")
    private User user;
    @SerializedName("favorited")
    private Boolean favorited;
    @SerializedName("retweeted")
    private Boolean retweeted;
    @SerializedName("lang")
    private String lang;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    class User {
        @SerializedName("id")
        private long id;
        @SerializedName("name")
        private String name;
        @SerializedName("screen_name")
        private String screenName;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScreenName() {
            return screenName;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }
    }
}
