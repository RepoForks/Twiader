package com.github.jydimir.twiader.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TweetLocal extends RealmObject {

    @PrimaryKey
    private long id;
    private long createdAt;
    private String text;
    private Boolean favorited;
    private Boolean retweeted;
    private String lang;

    private long userId;
    private String userName;
    private String userScreenName;

    private boolean alreadyRead = false;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public boolean isAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    @Override
    public String toString() {
        return "TweetLocal{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", favorited=" + favorited +
                ", retweeted=" + retweeted +
                ", lang='" + lang + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userScreenName='" + userScreenName + '\'' +
                '}';
    }
}
