package com.kaifa.customview.transformer;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 *Created by zhb on 2018/2/2.
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.75f;

    @SuppressLint("NewApi")
    @Override
    public void transformPage(View view, float position) {

        //从a切换到b
        // a页的position 0->-1
        // b页的position 1->0

        int pageWidth = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            //view.setAlpha(0);
        } else if (position <= 0) { // [-1,0] A
            // Use the default slide transition when
            // moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (position <= 1) { // (0,1] B
            // Fade the page out.
            view.setAlpha(1 - position);
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);
            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                    * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            //view.setAlpha(0);

        }
    }

}
