package com.kaifa.customview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.QQStepView;

public class ViewDay02Activity extends BaseActivity {

    QQStepView qqStepView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day02);

    }

    @Override
    protected void initView() {
        qqStepView = (QQStepView) findViewById(R.id.qq_sv);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        qqStepView.setStepMax(4000);
//        //属性动画
//        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, 3000);
//        valueAnimator.setDuration(1500);
//        //设置差值器，刚开始快，后来慢
//        valueAnimator.setInterpolator(new DecelerateInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int current = (int) animation.getAnimatedValue();
//                qqStepView.setStepCurrent(current);
//            }
//        });
//        valueAnimator.start();

        qqStepView.start(3000, 1000);
    }

    @Override
    protected void setListener() {

    }
}
