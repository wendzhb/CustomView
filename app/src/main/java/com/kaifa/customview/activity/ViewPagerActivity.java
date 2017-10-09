package com.kaifa.customview.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.fragment.ItemFragment;
import com.kaifa.customview.widget.ColorTrackTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends BaseActivity {
    @BindView(R.id.ll_top)
    LinearLayout linearLayout;
    @BindView(R.id.vp)
    ViewPager mViewPager;

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private List<ColorTrackTextView> mIndicators = new ArrayList<>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_pager);
    }

    @Override
    protected void initView() {
        initIndicator();
        initViewPager();
    }

    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            //动态添加颜色变化的TextView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            //设置颜色
            colorTrackTextView.setTextSize(30);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            //把新的加入LinearLayout
            linearLayout.addView(colorTrackTextView);
            //加入集和
            mIndicators.add(colorTrackTextView);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             *
             * @param position  代表当前的值
             * @param positionOffset    代表 0 - 1 滚动的百分比
             * @param positionOffsetPixels  代表滚动的值
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //1.左边的 位置 position
                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LIFT);
                left.setCurrentProgress(1 - positionOffset);

                try {
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
