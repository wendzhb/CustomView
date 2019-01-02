package com.kaifa.customview.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.transformer.RotateDownPageTransformer;
import com.kaifa.customview.transformer.ZoomOutPageTransformer;
import com.kaifa.customview.widget.PagerTransformerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Viewpager动画效果
 */
public class ViewDay20Activity extends BaseActivity {

    private PagerTransformerViewPager viewPager;
    private int[] imgs = new int[]{R.drawable.len, R.drawable.leo, R.drawable.lep, R.drawable.leq, R.drawable.ler};
    private List<ImageView> imageViews;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day20);

    }

    @Override
    protected void initView() {
        viewPager = (PagerTransformerViewPager) findViewById(R.id.vp);
    }

    @Override
    protected void initData() {
        imageViews = new ArrayList<>();
        findViewById(R.id.rl).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

    @Override
    protected void setData() {
        //为Viewpager添加动画效果 3.0以上才行 原因 3.0之后才支持属性动画
        //兼容低版本 -- nineoldandroids 支持低版本使用属性动画 viewhelp
//        viewPager.setPageTransformer(true,new DepthPageTransformer());
//        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
//        viewPager.setPageTransformer(true, new RotateDownPageTransformer());

        viewPager.setOffscreenPageLimit(imgs.length);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(ViewDay20Activity.this);
                imageView.setImageResource(imgs[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                imageViews.add(imageView);
                viewPager.setViewForPostion(imageView,position);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViews.get(position));
                viewPager.removeViewFromPostion(position);
            }

        });
    }

    @Override
    protected void setListener() {

    }
}
