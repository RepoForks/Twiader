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

package com.github.jydimir.twiader.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.jydimir.twiader.TwiaderApp;
import com.github.jydimir.twiader.data.Repository;
import com.github.jydimir.twiader.data.model.TweetLocal;
import com.github.jydimir.twiader.injection.components.DaggerTwiaderServiceComponent;
import com.github.jydimir.twiader.injection.modules.TwiaderServiceModule;
import com.github.jydimir.twiader.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class TwiaderService extends Service implements BetterTTS.SpeakProgressListener {

    @Inject
    BetterTTS betterTTS;
    @Inject
    Repository repository;
    @Inject
    RxBus rxBus;

    private android.os.Binder binder;
    private Playlist playlist = new Playlist();
    private PublishSubject<Object> utteranceCompletedSubject;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private UtteranceFormatter utteranceFormatter;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new Binder();
        DaggerTwiaderServiceComponent.builder()
                .applicationComponent(TwiaderApp.get(this).getApplicationComponent())
                .twiaderServiceModule(new TwiaderServiceModule(this))
                .build()
                .inject(this);

        utteranceCompletedSubject = PublishSubject.create();
        subscriptions.add(utteranceCompletedSubject
                .flatMap(markTweetAsRead)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

        subscriptions.add(rxBus.getObservable(Repository.Event.class)
                .filter(event -> event.getEvent().equals(Repository.Event.MARKED_AS_READ))
                .flatMap(event -> repository.getUnreadQuantity())
                .filter(unreadQty -> unreadQty == 0)
                .subscribe(aLong -> stopSpeak()));

        utteranceFormatter = new UtteranceFormatter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        betterTTS.shutdown();
        subscriptions.unsubscribe();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public Observable<Event> getEventBus() {
        return rxBus.getObservable(Event.class);
    }

    public void startSpeak() {
        String text = utteranceFormatter.format(playlist.getCurrentTweet());
        betterTTS.speak(text, String.valueOf(playlist.getCurrentTweet().getId()));
        rxBus.send(new Event(Event.TWEET_SPEAK_STARTED));
    }

    public void stopSpeak() {
        betterTTS.stopSpeak();
    }

    public void speakNext() {
        utteranceCompletedSubject.onNext(null);
        playlist.nextTweet();
        startSpeak();
    }

    public void speakPrevious() {
        playlist.previousTweet();
        startSpeak();
    }

    public void setTweets(List<TweetLocal> tweets) {
        playlist.setTweets(tweets);
    }

    public TweetLocal getCurrentTweet() {
        return playlist.getCurrentTweet();
    }

    @Override
    public void onComplete(String utteranceId) {
        Timber.i("TwiaderService#onComplete.");
        if (String.valueOf(playlist.getCurrentTweet().getId()).equals(utteranceId)) {
            Timber.i("TwiaderService#onComplete.SPEAK NEXT");
            speakNext();
        }
    }

    public class Binder extends android.os.Binder {
        public TwiaderService getService() {
            return TwiaderService.this;
        }
    }

    private final Func1<Object, Observable<TweetLocal>> markTweetAsRead = new Func1<Object, Observable<TweetLocal>>() {
        @Override
        public Observable<TweetLocal> call(Object o) {
            return repository.markTweetAsRead(playlist.getCurrentTweet().getId())
                    .subscribeOn(Schedulers.io());
        }
    };

    public static class Event {

        public static final String TWEET_SPEAK_STARTED = "tweet_speak_started";

        private String event;

        public Event(String event) {
            this.event = event;
        }

        public String getEvent() {
            return event;
        }
    }
}

