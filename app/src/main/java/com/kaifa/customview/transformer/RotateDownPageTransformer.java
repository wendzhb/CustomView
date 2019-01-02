package com.kaifa.customview.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by zhb on 2018/12/26.
 */
public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private final static float MAX_ROTATE = 20f;

    //从a切换到b 角度变化
    // a页的position 0->-1    0~-20
    // b页的position 1->0     20~0
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        view.setPivotX(pageWidth/2);
        view.setPivotY(pageHeight);
//        ViewHelper.setPivotX(view,pageWidth/2);
//        ViewHelper.setPivotY(view,pageHeight);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            //view.setAlpha(0);
//            ViewHelper.setRotation(view,0);

//            view.setRotation(0);
        } else if (position <= 0) { // [-1,0] A
            // Use the default slide transition when
            // moving to the left page
            float rotate = position * MAX_ROTATE;//0~-20
//            ViewHelper.setRotation(view,rotate);
            view.setRotation(rotate);

        } else if (position <= 1) { // (0,1] B
            float rotate = position * MAX_ROTATE;//20~0
//            ViewHelper.setRotation(view,rotate);

            view.setRotation(rotate);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            //view.setAlpha(0);
//            ViewHelper.setRotation(view,0);
//            view.setRotation(0);

        }
    }
}
