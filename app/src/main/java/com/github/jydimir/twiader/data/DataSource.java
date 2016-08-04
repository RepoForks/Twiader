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
