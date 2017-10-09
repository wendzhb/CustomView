package com.kaifa.customview.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
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
