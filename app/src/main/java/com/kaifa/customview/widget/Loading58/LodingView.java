package com.kaifa.customview.widget.Loading58;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2018/5/31.
 * 58加载数据动画
 */
public class LodingView extends LinearLayout {
    private ShapeView mShapeView;
    private View mShadowView;

    private int mTranslationDistance = 0;
    private final int ANIMATION_DURATION = 450;

    //是否停止动画
    private boolean mIsStopAnimator = false;

    public LodingView(Context context) {
        this(context, null);
    }

    public LodingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LodingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(80);
        initLayout();
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 初始化加载布局
     */
    private void initLayout() {
        //加载写好的ui_loading_view
        //实例化view
//        View loadView = inflate(getContext(), R.layout.ui_loading_view, null);
//        //添加view
//        addView(loadView);

        inflate(getContext(), R.layout.ui_loading_view, this);

        mShapeView = (ShapeView) findViewById(R.id.shape_view);
        mShadowView = findViewById(R.id.shadow_view);

        post(new Runnable() {
            @Override
            public void run() {
                //onresume view的绘制流程执行完毕之后
                startfailAnimator();

            }
        });
    }

    //开始下落动画
    private void startfailAnimator() {
        if (mIsStopAnimator){
            return;
        }
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, mTranslationDistance);
//        translationAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        translationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        translationAnimator.setDuration(ANIMATION_DURATION);

        //配合中间阴影缩小
        ObjectAnimator scalaAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f);
//        scalaAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        scalaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scalaAnimator.setDuration(ANIMATION_DURATION);

        AnimatorSet animatorSet = new AnimatorSet();
//        下落的时候越来越快
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(translationAnimator, scalaAnimator);

//        animatorSet.playSequentially(translationAnimator,scalaAnimator);有序播放动画
        animatorSet.start();
        //下落之后上抛，监听动画
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //改变形状
                mShapeView.exchange();
                startupAnimator();
                //开始旋转
            }
        });

//        translationAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//                mShapeView.exchange();
//            }
//        });


    }

    private void startupAnimator() {
        if (mIsStopAnimator){
            return;
        }
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY",  mTranslationDistance,0);
//        translationAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        translationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        translationAnimator.setDuration(ANIMATION_DURATION);

        //配合中间阴影放大
        ObjectAnimator scalaAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX",  0.3f,1f);
//        scalaAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        scalaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scalaAnimator.setDuration(ANIMATION_DURATION);

        AnimatorSet animatorSet = new AnimatorSet();
//        上抛越来越慢
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(translationAnimator, scalaAnimator);

//        animatorSet.playSequentially(translationAnimator,scalaAnimator);有序播放动画
        //上抛之后下落，监听动画
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startfailAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //开始旋转
                startRotationAnimator();
            }
        });
        animatorSet.start();
    }

    private void startRotationAnimator() {
        ObjectAnimator rotationAnimator = null;

        switch (mShapeView.getCurrentShape()){
            case Circle:
            case Triangle:
                //60
                rotationAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,-120);
                break;
            case Square:
                //180
                rotationAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,180);

                break;
        }
        rotationAnimator.setDuration(ANIMATION_DURATION);
        rotationAnimator.start();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(INVISIBLE);//不要再去摆放和计算，少走一些系统源码
        //清理动画
        mShapeView.clearAnimation();
        mShadowView.clearAnimation();

        //把LoadingView从父布局移除
        ViewGroup parent = (ViewGroup) getParent();
        if (parent!=null){
            parent.removeView(this);//从父布局移除
            removeAllViews();//移除自己所有的View
        }

        mIsStopAnimator = true;
    }
}
