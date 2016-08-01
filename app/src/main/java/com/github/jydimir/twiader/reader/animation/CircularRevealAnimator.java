package com.github.jydimir.twiader.reader.animation;

import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

public class CircularRevealAnimator extends Animator {

    public CircularRevealAnimator() {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showWithAnimation(View start, View target, long durationInMillis) {
        int startCenterX = start.getWidth() / 2;
        int startCenterY = start.getHeight() / 2;
        float endRadius = target.getWidth() - startCenterX;
        android.animation.Animator anim = ViewAnimationUtils
                .createCircularReveal(target, startCenterX, startCenterY, 0, endRadius);
        anim.setDuration(durationInMillis);
        anim.addListener(showAnimatorListener);
        target.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void hideWithAnimation(View start, View target, long durationInMillis) {
        int startCenterX = start.getWidth() / 2;
        int startCenterY = start.getHeight() / 2;
        float initRadius = target.getWidth() - startCenterX;
        android.animation.Animator anim = ViewAnimationUtils
                .createCircularReveal(target, startCenterX, startCenterY, initRadius, 0);
        anim.setDuration(durationInMillis);
        anim.addListener(hideAnimatorListener);
        anim.start();
    }

    private AnimatorListenerAdapter showAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(android.animation.Animator animation) {
            super.onAnimationStart(animation);
            showAnimationStarted();
        }

        @Override
        public void onAnimationEnd(android.animation.Animator animation) {
            super.onAnimationEnd(animation);
            showAnimationFinished();
        }
    };

    private AnimatorListenerAdapter hideAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(android.animation.Animator animation) {
            super.onAnimationStart(animation);
            hideAnimationStarted();
        }

        @Override
        public void onAnimationEnd(android.animation.Animator animation) {
            super.onAnimationEnd(animation);
            hideAnimationFinished();
        }
    };
}
