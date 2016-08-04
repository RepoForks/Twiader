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

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jydimir.twiader.R;
import com.github.jydimir.twiader.util.BetterViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;
import static com.github.jydimir.twiader.util.Preconditions.checkNotNull;

public class ReaderView extends LinearLayout implements ReaderContract.View {

    @BindView(R.id.reader_animator)
    BetterViewAnimator animatorView;
    @BindView(R.id.reader_loading_text)
    TextView loadingView;
    @BindView(R.id.reader_unread_quantity)
    TextView unreadQuantityView;
    @BindView(R.id.playback_panel_view)
    PlaybackPanelView playbackPanelView;
    @BindView(R.id.tv_logged_user)
    TextView loggedUserView;

    private ReaderContract.Presenter presenter;

    public ReaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        AnimationDrawable movingEllipsis =
                (AnimationDrawable) ContextCompat.getDrawable(getContext(), R.drawable.moving_ellipsis);
        loadingView.setCompoundDrawablesWithIntrinsicBounds(null, null, movingEllipsis, null);
        movingEllipsis.start();

        PlaybackPanelView controlsPanel = findById(this, R.id.playback_panel_view);
        controlsPanel.setControlsListener(controlsListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.stop();
    }

    @Override
    public void setPresenter(@NonNull ReaderContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showError() {
        animatorView.setDisplayedChildId(R.id.reader_error);
    }

    @Override
    public void showEmpty() {
        animatorView.setDisplayedChildId(R.id.reader_empty);
    }

    @Override
    public void showLoadingView() {
        animatorView.setDisplayedChildId(R.id.reader_loading);
    }

    @Override
    public void showMainView() {
        animatorView.setDisplayedChildId(R.id.reader_main);
    }

    @Override
    public void showUnreadQuantity(long unreadQuantity) {
        unreadQuantityView.setText(String.valueOf(unreadQuantity));
    }

    @Override
    public void showCurrentTweet(String author, String tweet) {
        playbackPanelView.setTweet(author, tweet);
    }

    @Override
    public void showLoggedUser(String username) {
        loggedUserView.setText(getResources().getString(R.string.twitter_name_template, username));
    }

    @Override
    public void showNoConnection() {
        Toast.makeText(getContext(), getContext().getString(R.string.twitter_no_connection), Toast.LENGTH_LONG)
                .show();
    }

    private PlaybackPanelView.PlaybackControlsListener controlsListener = new PlaybackPanelView.PlaybackControlsListener() {
        @Override
        public void onPlayClick() {
            presenter.startSpeak();
        }

        @Override
        public void onStopClick() {
            presenter.stopSpeak();
        }

        @Override
        public void onNextClick() {
            presenter.skipToNext();
        }

        @Override
        public void onPreviousClick() {
            presenter.skipToPrevious();
        }
    };
}
