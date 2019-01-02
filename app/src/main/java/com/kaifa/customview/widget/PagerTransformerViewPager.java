package com.kaifa.customview.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhb on 2018/12/26.
 */
public class PagerTransformerViewPager extends ViewPager {

    private View mLeft;
    private View mRight;

    private float mTrans;
    private float mScale;

    private static final float MIN_SCALE = 0.5f;

    private Map<Integer, View> mChildren = new HashMap<>();

    public void setViewForPostion(View view, int postion) {
        mChildren.put(postion, view);
    }

    public void removeViewFromPostion(int postion) {
        mChildren.remove(postion);
    }


    public PagerTransformerViewPager(Context context) {
        this(context, null);
    }

    public PagerTransformerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //0->1 position 0 ; offset 0-1
    //1->0 position 0 ; offset 1-0
    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {

        Log.e("tag", "position" + position + "offset" + offset + "offsetPixels" + offsetPixels);
        mLeft = mChildren.get(position);
        mRight = mChildren.get(position + 1);

        animStack(mLeft, mRight, offset, offsetPixels);
        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void animStack(View mLeft, View mRight, float offset, float offsetPixels) {
        if (mRight != null) {

            //从0-》1 offset 0-1
            mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;

            mTrans = -getWidth() - getPageMargin() + offsetPixels;

            mRight.setScaleX(mScale);
            mRight.setScaleY(mScale);

            mRight.setTranslationX(mTrans);
        }

        if (mLeft!=null) {
            mLeft.bringToFront();
        }
    }
}
