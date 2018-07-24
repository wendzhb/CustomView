package com.kaifa.customview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zhb on 2018/6/4.
 */
public class MyScrollView extends ScrollView {

    private ScrollChangeListener mListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener!=null){
            mListener.onScroll(l,t,oldl,oldt);
        }
    }

    public void setScrollChangeListener(ScrollChangeListener listener){
        mListener = listener;
    }
    public interface ScrollChangeListener{
        public void onScroll(int l, int t, int oldl, int oldt);
    }
}
