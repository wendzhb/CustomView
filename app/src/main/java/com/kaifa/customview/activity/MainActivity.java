package com.kaifa.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
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

    @OnClick({R.id.bt_01, R.id.bt_02, R.id.bt_03, R.id.vp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_01:
                startActivity(new Intent(this, ViewDay01Activity.class));

                break;
            case R.id.bt_02:
                startActivity(new Intent(this, ViewDay02Activity.class));

                break;
            case R.id.bt_03:
                startActivity(new Intent(this, ViewDay03Activity.class));

                break;
            case R.id.vp:
                startActivity(new Intent(this, ViewPagerActivity.class));

                break;
        }
    }
}
