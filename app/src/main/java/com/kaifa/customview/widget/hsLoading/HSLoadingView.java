package com.kaifa.customview.widget.hsLoading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.kaifa.customview.widget.hsLoading.CircleView;

/**
 * Created by zhb on 2018/12/17.
 * 花束直播加载
 */
public class HSLoadingView extends RelativeLayout {
    private CircleView mLeftView, mMiddleView, mRightView;
    private int mTranslationDistance = 20;
    private final long ANIMATION_TIME = 350;

    //是否停止动画
    private boolean mIsStopAnimator = false;

    public HSLoadingView(Context context) {
        this(context, null);
    }

    public HSLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HSLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(20);

        setBackgroundColor(Color.WHITE);

        //添加三个圆形View
        mLeftView = getCircleView(context);
        mLeftView.exchangeColor(Color.BLUE);
        mMiddleView = getCircleView(context);
        mMiddleView.exchangeColor(Color.RED);
        mRightView = getCircleView(context);
        mRightView.exchangeColor(Color.GREEN);

        addView(mLeftView);
        addView(mRightView);
        addView(mMiddleView);

        post(new Runnable() {
            @Override
            public void run() {
                //布局实例化之后再去开启动画
                expendAnimation();
            }
        });
    }

    /**
     * 展开动画
     */
    private void expendAnimation() {
        if (mIsStopAnimator) {
            return;
        }
        //往左右两边跑
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", 0, -mTranslationDistance);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", 0, mTranslationDistance);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME);
        set.playTogether(leftTranslationAnimator, rightTranslationAnimator);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //往中间跑
                innerAnimator();
            }
        });
        set.start();
    }

    /**
     * 收缩动画
     */
    private void innerAnimator() {
        if (mIsStopAnimator) {
            return;
        }
        //往中间跑
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", -mTranslationDistance, 0);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", mTranslationDistance, 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME);
        set.playTogether(leftTranslationAnimator, rightTranslationAnimator);
        set.setInterpolator(new AccelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //切换颜色的顺序:左边的给中间，中间的给右边，右边的给左边
                int leftColor = mLeftView.getColor();
                int middleColor = mMiddleView.getColor();
                int rightColor = mRightView.getColor();

                mMiddleView.exchangeColor(leftColor);
                mRightView.exchangeColor(middleColor);
                mLeftView.exchangeColor(rightColor);

                //往两边跑
                expendAnimation();
            }
        });
        set.start();
    }

    private CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        LayoutParams params = new LayoutParams(dip2px(10), dip2px(10));
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        return circleView;
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(INVISIBLE);//不要再去摆放和计算，少走一些系统源码
        //清理动画
        mLeftView.clearAnimation();
        mRightView.clearAnimation();

        //把LoadingView从父布局移除
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);//从父布局移除
            removeAllViews();//移除自己所有的View
        }

        mIsStopAnimator = true;
    }
}
