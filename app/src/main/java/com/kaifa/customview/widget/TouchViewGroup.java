package com.kaifa.customview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhb on 2017/10/26.
 */

public class TouchViewGroup extends LinearLayout {
    public TouchViewGroup(Context context) {
        super(context);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("tag", "ViewGroup dispatchTouchEvent" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("tag", "ViewGroup onInterceptTouchEvent" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("tag", "ViewGroup onTouchEvent" + event.getAction());
        return super.onTouchEvent(event);
    }
}
