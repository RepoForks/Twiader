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

import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.jydimir.twiader.data.local.LocalDataSource;
import com.github.jydimir.twiader.data.model.TweetLocal;
import com.github.jydimir.twiader.data.remote.RemoteDataSource;
import com.github.jydimir.twiader.util.ConnectivityUntil;
import com.github.jydimir.twiader.util.PreferencesConstants;
import com.github.jydimir.twiader.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

import static com.github.jydimir.twiader.util.Preconditions.checkNotNull;

public class Repository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private RxSharedPreferences rxSharedPreferences;
    private RxBus rxBus;
    private ConnectivityUntil connectivityUntil;

    @Inject
    public Repository(@NonNull LocalDataSource localDataSource, @NonNull RemoteDataSource remoteDataSource,
                      @NonNull RxSharedPreferences rxPreferences, @NonNull RxBus rxBus,
                      @NonNull ConnectivityUntil connectivityUntil) {
        this.localDataSource = checkNotNull(localDataSource);
        this.remoteDataSource = checkNotNull(remoteDataSource);
        this.rxSharedPreferences = checkNotNull(rxPreferences);
        this.rxBus = checkNotNull(rxBus);
        this.connectivityUntil = checkNotNull(connectivityUntil);
    }

    public Observable<Event> getEventsBus() {
        return rxBus.getObservable(Event.class);
    }

    public Observable<List<TweetLocal>> getTweets() {
        return localDataSource.getTweets(null);
    }

    public Observable<List<TweetLocal>> syncLocalWithRemote() {
        Preference<Long> maxIdPref = rxSharedPreferences.getLong(PreferencesConstants.MAX_ID, null);

        Observable<Boolean> connectivity = Observable.fromCallable(() -> connectivityUntil.isConnectedToInternet());

        Observable<Object> notConnected = connectivity
                .filter(aBoolean -> !aBoolean)
                .doOnNext(aBoolean -> rxBus.send(new Repository.Event(Event.NO_CONNECTION)))
                .map((Func1<Boolean, Object>) aBoolean -> aBoolean); //just for merging

        Observable connected = connectivity
                .filter(aBoolean -> aBoolean)
                .flatMap(aBoolean -> maxIdPref.asObservable())
                .flatMap(aLong -> remoteDataSource.getTweets(aLong))   //get tweet from Twitter
                .flatMap(tweetLocals -> localDataSource.saveTweets(tweetLocals)) //save tweets locally
                .doOnNext(maxId -> saveMaxId(maxIdPref, maxId)) //save new max id
                .doOnNext(aLong -> localDataSource.deleteReadTweets()) //delete read tweets
                .map((Func1<Long, Object>) aLong -> aLong); //just for merging

        return notConnected.mergeWith(connected)
                .flatMap(object -> localDataSource.getTweets(null)); //retrieve all tweets not just remote ones
    }

    public Observable<TweetLocal> markTweetAsRead(long tweetId) {
        return localDataSource.markTweetAsRead(tweetId)
                .doOnNext(tweetLocal -> rxBus.send(new Event(Event.MARKED_AS_READ)));
    }

    public Observable<Long> getUnreadQuantity() {
        return localDataSource.getUnreadQuantity();
    }

    public Observable<Long> deleteAllTweets() {
        return localDataSource.deleteAllTweets()
                .doOnNext(tweetLocal -> rxBus.send(new Event(Event.MARKED_AS_READ)));
    }

    private void saveMaxId(Preference<Long> maxIdPref, long maxId) {
        //noinspection ConstantConditions
        if (maxIdPref.get() == null || maxId > maxIdPref.get()) {
            maxIdPref.set(maxId);
        }
    }

    public static class Event {
        public static final String MARKED_AS_READ = "marked_as_read";
        public static final String NO_CONNECTION = "no_connection";

        private String event;

        public Event(String event) {
            this.event = event;
        }

        public String getEvent() {
            return event;
        }
    }
}
