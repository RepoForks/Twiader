package com.github.jydimir.twiader.reader.animation;

import android.view.View;

public abstract class Animator {

    public interface ShowAnimationListener {
        void showAnimationStarted();

        void showAnimationFinished();
    }

    public interface HideAnimationListener {
        void hideAnimationStarted();

        void hideAnimationFinished();
    }

    private ShowAnimationListener showAnimationListener;
    private HideAnimationListener hideAnimationListener;

    public void setShowAnimationListener(ShowAnimationListener showAnimationListener) {
        this.showAnimationListener = showAnimationListener;
    }

    public void setHideAnimationListener(HideAnimationListener hideAnimationListener) {
        this.hideAnimationListener = hideAnimationListener;
    }

    public void showAnimationStarted() {
        if (showAnimationListener != null) {
            showAnimationListener.showAnimationStarted();
        }
    }

    public void showAnimationFinished() {
        if (showAnimationListener != null) {
            showAnimationListener.showAnimationFinished();
        }
    }

    public void hideAnimationStarted() {
        if (hideAnimationListener != null) {
            hideAnimationListener.hideAnimationStarted();
        }
    }

    public void hideAnimationFinished() {
        if (hideAnimationListener != null) {
            hideAnimationListener.hideAnimationFinished();
        }
    }

    public abstract void showWithAnimation(View start, View target, long durationInMillis);

    public abstract void hideWithAnimation(View start, View target, long durationInMillis);
}
