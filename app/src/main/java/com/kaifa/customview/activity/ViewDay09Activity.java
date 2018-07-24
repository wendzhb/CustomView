package com.kaifa.customview.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.RadarView;

public class ViewDay09Activity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day09);

    }

    @Override
    protected void initView() {
        RadarView radarView = (RadarView) findViewById(R.id.rv);
        radarView.run.run();
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
}
