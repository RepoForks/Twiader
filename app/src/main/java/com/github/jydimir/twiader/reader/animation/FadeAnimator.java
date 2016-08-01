package com.github.jydimir.twiader.reader.animation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class FadeAnimator extends Animator {

    private Animation fadeIn;
    private Animation fadeOut;

    public FadeAnimator(@NonNull Context context) {
        fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        fadeIn.setAnimationListener(fadeInListener);
        fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        fadeOut.setAnimationListener(fadeOutListener);
    }

    /*start parameter will be ignored, so you can pass null*/
    @Override
    public void showWithAnimation(@Nullable View start, View target, long durationInMillis) {
        fadeIn.setDuration(durationInMillis);
        target.startAnimation(fadeIn);
        target.setVisibility(View.VISIBLE);
    }

    /*start parameter will be ignored, so you can pass null*/
    @Override
    public void hideWithAnimation(@Nullable View start, View target, long durationInMillis) {
        fadeOut.setDuration(durationInMillis);
        target.startAnimation(fadeOut);
        target.setVisibility(View.INVISIBLE);
    }

    private Animation.AnimationListener fadeInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            showAnimationStarted();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            showAnimationFinished();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    private Animation.AnimationListener fadeOutListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            hideAnimationStarted();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            hideAnimationFinished();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };
}
