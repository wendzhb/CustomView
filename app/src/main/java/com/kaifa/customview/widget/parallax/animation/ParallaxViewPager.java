package com.kaifa.customview.widget.parallax.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.kaifa.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/9 8:28
 * Version 1.0
 * Params:
 * Description:  视差动画的ViewPager
*/
public class ParallaxViewPager extends ViewPager {


    private List<ParallaxFragment> mFragments ;

    public ParallaxViewPager(Context context) {
        this(context , null);
    }

    public ParallaxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFragments = new ArrayList<>() ;
    }


    public void setLayoutId(FragmentManager fm  ,int[] layoutIds){
        mFragments.clear();
        for (int layoutId : layoutIds) {
            ParallaxFragment fragment = new ParallaxFragment() ;
            Bundle bundle = new Bundle() ;
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY , layoutId);
            fragment.setArguments(bundle);
            mFragments.add(fragment) ;
        }

        // 给我们的ViewPager  设置Adapter
        setAdapter(new ParallaxPagerAdapter(fm));


        // 2.2.3  监听滑动改变位移
        addOnPageChangeListener(new OnPageChangeListener() {

            // 从第一张图 滑动到 第二张图 滑动的过程
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // positionOffset值是 0-1  positionOffsetPixels值是 0-屏幕的宽度px

                // 获取左边出去的fragment右边进来的fragment
                ParallaxFragment outFragment = mFragments.get(position) ;
                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);

                    // 为什么这样写 ?
                    parallaxView.setTranslationX((-positionOffsetPixels)*tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels)*tag.translationYOut);

                }


                try {
                    ParallaxFragment inFragment = mFragments.get(position+1) ;
                    parallaxViews = inFragment.getParallaxViews() ;
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);

                        parallaxView.setTranslationX((getMeasuredWidth()-positionOffsetPixels)*tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth()-positionOffsetPixels)*tag.translationYIn);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // 已经切换完毕
            // 已经滑动到具体某一页，比如滑动到第一页、滑动到第二页、滑动到第三页
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private class ParallaxPagerAdapter extends FragmentPagerAdapter {

        public ParallaxPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
