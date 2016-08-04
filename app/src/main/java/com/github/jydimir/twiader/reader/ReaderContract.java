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
