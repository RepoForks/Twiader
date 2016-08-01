package com.github.jydimir.twiader.reader.animation;

import android.content.Context;
import android.os.Build;

public class AnimatorFactory {

    public static Animator createAnimator(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return new FadeAnimator(context);
        } else {
            return new CircularRevealAnimator();
        }
    }

    private AnimatorFactory() {
    }
}
