package com.kaifa.customview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhb on 2017/10/25.
 */

public class TouchView extends View {
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("tag", "VIEW onTouchEvent" + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("tag", "VIEW dispatchTouchEvent" + event.getAction());

        return super.dispatchTouchEvent(event);
    }
}
