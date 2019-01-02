package com.kaifa.customview.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.YHLoadingView;

public class ViewDay19Activity extends BaseActivity {

    private YHLoadingView viewById;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day19);

    }

    @Override
    protected void initView() {
        viewById = (YHLoadingView) findViewById(R.id.yh_loadingview);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewById.disappear();
            }
        },5000);
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
