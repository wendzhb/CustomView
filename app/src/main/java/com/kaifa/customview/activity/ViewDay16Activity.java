package com.kaifa.customview.activity;

import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.love.LoveLayout;

public class ViewDay16Activity extends BaseActivity {

    private LoveLayout loveLayout;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day16);

    }

    @Override
    protected void initView() {
        loveLayout = (LoveLayout) findViewById(R.id.love_layout);
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

    public void addLove(View view){
        loveLayout.addLove();
    }
}
