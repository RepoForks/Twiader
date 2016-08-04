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

package com.github.jydimir.twiader.reader;

import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.jydimir.twiader.data.Repository;
import com.github.jydimir.twiader.data.model.TweetLocal;
import com.github.jydimir.twiader.services.TwiaderService;
import com.github.jydimir.twiader.util.FuncUtils;
import com.github.jydimir.twiader.util.PreferencesConstants;
import com.github.jydimir.twiader.util.RxUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static com.github.jydimir.twiader.util.Preconditions.checkNotNull;

public class ReaderPresenter implements ReaderContract.Presenter {

    private final Repository repository;
    private final ReaderContract.View mvpView;
    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private Subscription serviceEventsSubscription;
    private final PublishSubject<Object> syncSubject;
    private final PublishSubject<Object> markAllAsReadSubject;
    private TwiaderService service;
    private RxSharedPreferences rxPreferences;

    public ReaderPresenter(@NonNull Repository repository, @NonNull ReaderContract.View mvpView,
                           @NonNull RxSharedPreferences rxSharedPreferences) {
        this.repository = checkNotNull(repository);
        this.mvpView = checkNotNull(mvpView);
        this.rxPreferences = checkNotNull(rxSharedPreferences);
        this.mvpView.setPresenter(this);
        this.syncSubject = PublishSubject.create();
        this.markAllAsReadSubject = PublishSubject.create();
    }

    @Override
    public void start() {

        Preference<String> usernamePref = rxPreferences.getString(PreferencesConstants.USERNAME);
        String username = usernamePref.get();
        mvpView.showLoggedUser(username);

        ConnectableObservable<List<TweetLocal>> syncResult = syncSubject
                .doOnNext(o -> mvpView.showLoadingView())
                .observeOn(Schedulers.io())
                .flatMap(o -> repository.syncLocalWithRemote())
                .observeOn(AndroidSchedulers.mainThread())
                .publish();
        Observable<List<TweetLocal>> success = syncResult.filter(FuncUtils.not(isEmpty))
                .doOnNext(tweetLocals -> service.setTweets(tweetLocals))
                .doOnNext(tweetsLoaded);
        Observable<List<TweetLocal>> empty = syncResult.filter(isEmpty)
                .doOnNext(tweetLocals -> mvpView.showEmpty());
        subscriptions.add(success.mergeWith(empty).subscribe(syncSubscriber));
        syncResult.connect();
        syncSubject.onNext(null);

        ConnectableObservable<Long> markAsReadResult = repository.getEventsBus()
                .filter(event -> event.getEvent().equals(Repository.Event.MARKED_AS_READ))
                .observeOn(Schedulers.io())
                .flatMap(event -> repository.getUnreadQuantity())
                .observeOn(AndroidSchedulers.mainThread())
                .publish();
        subscriptions.add(markAsReadResult.filter(unreadQty -> (unreadQty == 0))
                .doOnNext(aLong -> service.stopSpeak())
                .subscribe(unread -> mvpView.showEmpty()));
        subscriptions.add(markAsReadResult.filter(unreadQty -> (unreadQty > 0))
                .subscribe(mvpView::showUnreadQuantity));
        markAsReadResult.connect();

        subscriptions.add(markAllAsReadSubject.flatMap(o -> repository.deleteAllTweets())
                .subscribeOn(Schedulers.io())
                .subscribe());

        subscriptions.add(repository.getEventsBus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(event -> event.getEvent().equals(Repository.Event.NO_CONNECTION))
                .subscribe(event -> mvpView.showNoConnection()));
    }

    @Override
    public void stop() {
        subscriptions.unsubscribe();
        RxUtil.unsubscribe(serviceEventsSubscription);
    }

    @Override
    public void setService(@NonNull TwiaderService service) {
        this.service = checkNotNull(service);
        RxUtil.unsubscribe(serviceEventsSubscription);
        serviceEventsSubscription = this.service.getEventBus()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(event -> event.getEvent().equals(TwiaderService.Event.TWEET_SPEAK_STARTED))
                .doOnError(throwable -> mvpView.showCurrentTweet("", ""))
                .subscribe(event -> mvpView.showCurrentTweet(service.getCurrentTweet().getUserName(),
                        service.getCurrentTweet().getText()));
    }

    @Override
    public void startSpeak() {
        service.startSpeak();
    }

    @Override
    public void stopSpeak() {
        service.stopSpeak();
    }

    @Override
    public void skipToNext() {
        service.speakNext();
    }

    @Override
    public void skipToPrevious() {
        service.speakPrevious();
    }

    @Override
    public void refresh() {
        syncSubject.onNext(null);
    }

    @Override
    public void markAllAsRead() {
        markAllAsReadSubject.onNext(null);
    }

    private Func1<List<TweetLocal>, Boolean> isEmpty = tweetLocals -> (tweetLocals.size() == 0);

    private Action1<List<TweetLocal>> tweetsLoaded = new Action1<List<TweetLocal>>() {
        @Override
        public void call(List<TweetLocal> tweetLocals) {
            mvpView.showUnreadQuantity(tweetLocals.size());
            mvpView.showMainView();
        }
    };

    private Subscriber syncSubscriber = new Subscriber() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onNext(Object o) {
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "");
            mvpView.showError();
        }
    };
}
