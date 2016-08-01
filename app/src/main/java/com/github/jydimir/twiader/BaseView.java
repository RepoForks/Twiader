package com.github.jydimir.twiader;

import android.support.annotation.NonNull;

public interface BaseView<T> {
    void setPresenter(@NonNull T presenter);
}
