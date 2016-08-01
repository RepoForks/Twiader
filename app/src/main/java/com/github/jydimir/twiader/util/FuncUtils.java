package com.github.jydimir.twiader.util;

import rx.functions.Func1;

public class FuncUtils {
    public static <T> Func1<T, Boolean> not(final Func1<T, Boolean> func) {
        return value -> !func.call(value);
    }

    private FuncUtils() {
    }
}
