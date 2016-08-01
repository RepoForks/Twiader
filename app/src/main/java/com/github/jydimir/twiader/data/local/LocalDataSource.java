package com.github.jydimir.twiader.data.local;

import android.support.annotation.Nullable;

import com.github.jydimir.twiader.data.DataSource;
import com.github.jydimir.twiader.data.model.TweetLocal;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class LocalDataSource implements DataSource {

    private DbManager dbManager;

    @Inject
    public LocalDataSource() {
        this.dbManager = new DbManager();
    }

    @Override
    public Observable<List<TweetLocal>> getTweets(@Nullable Long sinceId) {
        return dbManager.tweets();
    }

    @Override
    public Observable<Long> saveTweets(List<TweetLocal> tweets) {
        return dbManager.saveTweets(tweets);
    }

    @Override
    public Observable<List<TweetLocal>> deleteReadTweets() {
        return dbManager.deleteReadTweets();
    }

    @Override
    public Observable<TweetLocal> markTweetAsRead(long tweetId) {
        return dbManager.markTweetAsRead(tweetId);
    }

    @Override
    public Observable<Long> getUnreadQuantity() {
        return dbManager.unreadQuantity();
    }

    @Override
    public Observable<Long> deleteAllTweets() {
        return dbManager.deleteAllTweets();
    }
}
