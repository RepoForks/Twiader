package com.github.jydimir.twiader.data.local;


import com.github.jydimir.twiader.data.model.TweetLocal;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

public class DbManager {

    public Observable<List<TweetLocal>> tweets() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            List<TweetLocal> tweets = realm.where(TweetLocal.class)
                    .equalTo(DbConstants.ALREADY_READ, false)
                    .findAll()
                    .sort(DbConstants.TWEET_ID, Sort.DESCENDING);
            tweets = realm.copyFromRealm(tweets);
            realm.close();
            return tweets;
        });
    }

    public Observable<Long> saveTweets(final List<TweetLocal> tweets) {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealm(tweets);
            realm.commitTransaction();
            long newMaxId = maxId(realm);
            realm.close();
            return newMaxId;
        });
    }

    public Observable<List<TweetLocal>> deleteReadTweets() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<TweetLocal> readTweets = realm.where(TweetLocal.class)
                    .equalTo(DbConstants.ALREADY_READ, true)
                    .findAll();
            List<TweetLocal> forReturn = realm.copyFromRealm(readTweets);
            readTweets.deleteAllFromRealm();
            realm.commitTransaction();
            realm.close();
            return forReturn;
        });
    }

    public Observable<TweetLocal> markTweetAsRead(long tweetId) {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            TweetLocal tweet = realm.where(TweetLocal.class)
                    .equalTo(DbConstants.TWEET_ID, tweetId)
                    .findFirst();
            tweet.setAlreadyRead(true);
            tweet = realm.copyFromRealm(tweet);
            realm.commitTransaction();
            realm.close();
            return tweet;
        });
    }

    public Observable<Long> unreadQuantity() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            long unreadQuantity = realm.where(TweetLocal.class)
                    .equalTo(DbConstants.ALREADY_READ, false)
                    .count();
            realm.close();
            return unreadQuantity;
        });
    }

    public Observable<Long> deleteAllTweets() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<TweetLocal> tweets = realm.where(TweetLocal.class).findAll();
            long quantity = tweets.size();
            tweets.deleteAllFromRealm();
            realm.commitTransaction();
            realm.close();
            return quantity;
        });
    }

    private long maxId(Realm realm) {
        Number maxId = realm.where(TweetLocal.class).max(DbConstants.TWEET_ID);
        return (maxId == null) ? Long.MIN_VALUE : maxId.longValue();
    }
}
