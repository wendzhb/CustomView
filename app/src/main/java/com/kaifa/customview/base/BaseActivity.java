package com.kaifa.customview.base;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kaifa.customview.util.StatusBarUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
//        StatusBarUtils.setStatusBarColor(this, Color.BLUE);
        StatusBarUtils.setActivityTranslucent(this);
        ButterKnife.bind(this);
        initView();
        initData();
        setData();
        setListener();
    }

    protected abstract void setContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setData();

    protected abstract void setListener();


}
