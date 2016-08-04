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
