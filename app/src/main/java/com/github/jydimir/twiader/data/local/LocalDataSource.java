/*
 * Copyright 2016  Andrii Lisun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
