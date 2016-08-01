package com.github.jydimir.twiader.data;

import android.support.annotation.Nullable;

import com.github.jydimir.twiader.data.model.TweetLocal;

import java.util.List;

import rx.Observable;

public interface DataSource {
    Observable<Long> saveTweets(List<TweetLocal> tweets);

    Observable<List<TweetLocal>> getTweets(@Nullable Long sinceId);

    Observable<List<TweetLocal>> deleteReadTweets();

    Observable<TweetLocal> markTweetAsRead(long tweetId);

    Observable<Long> getUnreadQuantity();

    Observable<Long> deleteAllTweets();
}
