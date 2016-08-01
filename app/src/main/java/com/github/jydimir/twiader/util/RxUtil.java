package com.github.jydimir.twiader.util;

import rx.Subscription;

public class RxUtil {
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
