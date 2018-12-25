package com.kaifa.customview.activity;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.parallax.animation.ParallaxViewPager;

public class ViewDay17Activity extends BaseActivity {

    // 2.2.1 先把布局 和 fragment 创建好
    private ParallaxViewPager mParallaxViewPager;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day17);

        mParallaxViewPager = (ParallaxViewPager) findViewById(R.id.parallax_vp);

        // 在这里直接给一个方法，获取一个布局数组，一定要采用最简便的方式让别人来使用
        mParallaxViewPager.setLayoutId(getSupportFragmentManager(),
                new int[]{R.layout.fragment_page_first, R.layout.fragment_page_second, R.layout.fragment_page_third});

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
}
