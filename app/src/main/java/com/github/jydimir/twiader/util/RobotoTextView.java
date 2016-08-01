package com.github.jydimir.twiader.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoTextView extends TextView {
    public RobotoTextView(Context context) {
        super(context);
        setFontType(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontType(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontType(context);
    }

    private void setFontType(Context context) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto_thin.ttf");
        setTypeface(typeFace);
    }
}
