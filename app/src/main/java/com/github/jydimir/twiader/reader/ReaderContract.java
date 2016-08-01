package com.github.jydimir.twiader.reader;

import android.support.annotation.NonNull;

import com.github.jydimir.twiader.BasePresenter;
import com.github.jydimir.twiader.BaseView;
import com.github.jydimir.twiader.services.TwiaderService;

public interface ReaderContract {

    interface View extends BaseView<Presenter> {

        void showError();

        void showEmpty();

        void showLoadingView();

        void showMainView();

        void showUnreadQuantity(long unreadQuantity);

        void showCurrentTweet(String author, String tweet);

        void showLoggedUser(String username);

        void showNoConnection();
    }

    interface Presenter extends BasePresenter {
        void setService(@NonNull TwiaderService service);

        void startSpeak();

        void stopSpeak();

        void skipToNext();

        void skipToPrevious();

        void refresh();

        void markAllAsRead();
    }
}
