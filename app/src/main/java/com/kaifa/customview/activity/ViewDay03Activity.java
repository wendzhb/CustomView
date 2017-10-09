package com.kaifa.customview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.ColorTrackTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDay03Activity extends BaseActivity {

    @BindView(R.id.cttv)
    ColorTrackTextView cttv;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day03);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {

    }

    public void lefttoright(View view) {
        cttv.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                cttv.setCurrentProgress(current);
            }
        });
        valueAnimator.start();
    }

    public void righttoleft(View view) {
        cttv.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LIFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                cttv.setCurrentProgress(current);
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
