/*
 * This class was taken from Jake Wharton and included to this project
 * <p/>
 * Copyright 2014 Jake Wharton
 * <p/>
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jydimir.twiader.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

public class BetterViewAnimator extends ViewAnimator {
    public BetterViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChildId(int id) {
        if (getDisplayedChildId() == id) {
            return;
        }
        for (int i = 0, count = getChildCount(); i < count; i++) {
            if (getChildAt(i).getId() == id) {
                setDisplayedChild(i);
                return;
            }
        }
        String name = getResources().getResourceEntryName(id);
        throw new IllegalArgumentException("No view with ID " + name);
    }

    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }
}
