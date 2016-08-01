package com.github.jydimir.twiader.services;

import android.support.annotation.NonNull;

import com.github.jydimir.twiader.data.model.TweetLocal;

import java.util.List;
import java.util.ListIterator;

public class Playlist {

    private TweetLocal currentTweet;
    private ListIterator<TweetLocal> iterator;

    public Playlist() {
    }

    public void setTweets(@NonNull List<TweetLocal> tweets) {
        checkListAndThrow(tweets);
        //we have at least one tweet
        iterator = tweets.listIterator();
        currentTweet = nextTweet();
    }

    public TweetLocal nextTweet() {
        if (hasNext()) {
            TweetLocal next = iterator.next();
            /*this is needed, because ListIterator doesn't have "current element"
            and points between elements.
            currentTweet != null only for the first time after setTweets(...)*/
            if (currentTweet != null && currentTweet.getId() == next.getId() && hasNext()) {
                next = iterator.next();
            }
            currentTweet = next;
        }
        return currentTweet;
    }

    public TweetLocal previousTweet() {
        if (hasPrevious()) {
            TweetLocal previous = iterator.previous();
            /*this is needed, because ListIterator doesn't have "current element"
            and points between elements*/
            if (previous.getId() == currentTweet.getId() && hasPrevious()) {
                previous = iterator.previous();
            }
            currentTweet = previous;
        }
        return currentTweet;
    }

    public TweetLocal getCurrentTweet() {
        return currentTweet;
    }

    private void checkListAndThrow(List<TweetLocal> list) {
        if (!isListValid(list)) {
            throw new IllegalArgumentException("tweets argument of " +
                    "setTweets(@NonNull List<TweetLocal> tweets) can't be null or empty");
        }
    }

    private boolean isListValid(List<TweetLocal> list) {
        return (list != null && list.size() > 0);
    }

    private void checkIteratorAndThrow(ListIterator iterator) {
        if (iterator == null) {
            throw new IllegalStateException("call setTweets(@NonNull List<TweetLocal> tweets) first");
        }
    }

    private boolean hasNext() {
        checkIteratorAndThrow(iterator);
        return iterator.hasNext();
    }

    private boolean hasPrevious() {
        checkIteratorAndThrow(iterator);
        return iterator.hasPrevious();
    }
}
