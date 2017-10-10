package com.kaifa.customview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.ProgressBarView;
import com.kaifa.customview.widget.ShapeView;

public class ViewDay04Activity extends BaseActivity {

    private ProgressBarView progressBarView;
    private ShapeView shapeView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day04);
    }

    @Override
    protected void initView() {
        progressBarView = (ProgressBarView) findViewById(R.id.progressbar);
        shapeView = (ShapeView) findViewById(R.id.shapeview);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setData() {
        progressBarView.setMax(100);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 100);
        animator.setDuration(2000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                progressBarView.setProgress((int) progress);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    public void exchanger(View view) {
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 3);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue == 0 || animatedValue == 1 || animatedValue == 2 || animatedValue == 3) {
                    shapeView.exchange();
                }
            }
        });
    }
}
