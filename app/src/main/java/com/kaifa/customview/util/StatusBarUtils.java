package com.kaifa.customview.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by zhb on 2018/5/11.
 */

public class StatusBarUtils {
    /**
     * 为activity的状态栏设置颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //直接调用系统提供的方法
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0之间
            //首先改成全屏,然后在状态栏的部分加一个布局
            //电量 时间 网络状态都还在
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //在状态栏的部分加一个布局(高度是状态栏的高度)
            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            view.setBackgroundColor(color);

            //decorview是一个framelayout,会加载一个系统布局(linearlayout),在系统布局中会有一个id为android.R.id.content(RelativeLayout)
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);


//            android:fitsSystemWindows="true"  每个布局都要写
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View activityView = contentView.getChildAt(0);
//            activityView.setPadding(0, getStatusBarHeight(activity), 0, 0);
            activityView.setFitsSystemWindows(true);
        }

    }

    private static int getStatusBarHeight(Activity activity) {
        //插件式换肤：怎么获取资源的，先获取资源id，根据id获取资源
        Resources resources = activity.getResources();
        int statuBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(statuBarHeight);
    }

    /**
     * 设置activity全屏
     *
     * @param activity
     */
    public static void setActivityTranslucent(Activity activity) {
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        //4.4-5.0之间
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
