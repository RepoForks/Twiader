package com.github.jydimir.twiader.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LobsterTextView extends TextView {
    public LobsterTextView(Context context) {
        super(context);
        setFontType(context);
    }

    public LobsterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontType(context);
    }

    public LobsterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontType(context);
    }

    private void setFontType(Context context) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/lobster_regular.ttf");
        setTypeface(typeFace);
    }
}
