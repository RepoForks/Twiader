package com.github.jydimir.twiader.reader;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.jydimir.twiader.R;
import com.github.jydimir.twiader.reader.animation.Animator;
import com.github.jydimir.twiader.reader.animation.AnimatorFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PlaybackPanelView extends FrameLayout implements Animator.ShowAnimationListener, Animator.HideAnimationListener {

    public interface PlaybackControlsListener {
        void onPlayClick();

        void onStopClick();

        void onNextClick();

        void onPreviousClick();
    }

    @BindView(R.id.prl_collapseable)
    PercentRelativeLayout collapseableView;
    @BindView(R.id.fab_play)
    FloatingActionButton playButton;
    @BindView(R.id.ib_stop)
    ImageButton stopButton;
    @BindView(R.id.ib_previous)
    ImageButton previousButton;
    @BindView(R.id.ib_next)
    ImageButton nextButton;
    @BindView(R.id.tv_tweet)
    TextView tweetView;
    @BindView(R.id.tv_author)
    TextView authorView;

    private final int ANIMATION_DURATION = 700;
    private Animator animator;
    private PlaybackControlsListener controlsListener;

    public PlaybackPanelView(Context context) {
        super(context);
        init(context);
    }

    public PlaybackPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlaybackPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        animator = AnimatorFactory.createAnimator(context);
        animator.setShowAnimationListener(this);
        animator.setHideAnimationListener(this);
        LayoutInflater.from(context).inflate(R.layout.playback_panel, this);
    }

    public void setControlsListener(PlaybackControlsListener controlsListener) {
        this.controlsListener = controlsListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setTweet(String author, String tweet) {
        authorView.setText(author);
        tweetView.setText(tweet);
    }

    @OnClick(R.id.fab_play)
    public void playClicked() {
        showControls();
        if (controlsListener != null) {
            controlsListener.onPlayClick();
        }
    }

    @OnClick(R.id.ib_stop)
    public void stopClicked() {
        hideControls();
        if (controlsListener != null) {
            controlsListener.onStopClick();
        }
    }

    @OnClick(R.id.ib_previous)
    public void previousClicked() {
        if (controlsListener != null) {
            controlsListener.onPreviousClick();
        }
    }

    @OnClick(R.id.ib_next)
    public void nextClicked() {
        if (controlsListener != null) {
            controlsListener.onNextClick();
        }
    }

    @Override
    public void hideAnimationStarted() {
        disableViews();
    }

    @Override
    public void hideAnimationFinished() {
        playButton.setVisibility(VISIBLE);
        collapseableView.setVisibility(INVISIBLE);
        enableViews();
    }

    @Override
    public void showAnimationStarted() {
        playButton.setVisibility(INVISIBLE);
        disableViews();
    }

    @Override
    public void showAnimationFinished() {
        enableViews();
    }

    private void showControls() {
        animator.showWithAnimation(collapseableView, collapseableView, ANIMATION_DURATION);
    }

    private void hideControls() {
        animator.hideWithAnimation(collapseableView, collapseableView, ANIMATION_DURATION);
    }

    private void disableViews() {
        playButton.setClickable(false);
        stopButton.setClickable(false);
        nextButton.setClickable(false);
        previousButton.setClickable(false);
        tweetView.setSelected(false);
    }

    private void enableViews() {
        playButton.setClickable(true);
        stopButton.setClickable(true);
        nextButton.setClickable(true);
        previousButton.setClickable(true);
        tweetView.setSelected(true);
    }
}

