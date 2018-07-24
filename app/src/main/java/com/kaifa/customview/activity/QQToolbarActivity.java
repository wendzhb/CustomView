package com.kaifa.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kaifa.customview.R;
import com.kaifa.customview.util.StatusBarUtils;
import com.kaifa.customview.widget.MyScrollView;

public class QQToolbarActivity extends AppCompatActivity {

    private LinearLayout mTitleBar;
    private MyScrollView mScrollView;
    private ImageView mImageView;
    private int imageViewHeight, titleBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqtoolbar);

        StatusBarUtils.setActivityTranslucent(this);
        //qq效果1.不断监听ScrollView的滑动，判断当前滚动的位置跟头部的imageview比较计算背景透明度
        //2.自定义behavior

        //1.刚进来背景完全透明
        mTitleBar = (LinearLayout) findViewById(R.id.title_bar);
        mTitleBar.getBackground().setAlpha(0);
        mImageView = (ImageView) findViewById(R.id.iv_image);

        //在这里拿不到高度，没有测量完
//        imageViewHeight = mImageView.getMeasuredHeight();
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                imageViewHeight = mImageView.getMeasuredHeight();
            }
        });
        mTitleBar.post(new Runnable() {
            @Override
            public void run() {
                titleBarHeight = mTitleBar.getMeasuredHeight();
            }
        });
        //不断地监听滚动   判断当前滚动的位置跟头部的imageview比较计算背景透明度
        mScrollView = (MyScrollView) findViewById(R.id.scroll_view);
//        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });
        mScrollView.setScrollChangeListener(new MyScrollView.ScrollChangeListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                //获取图片的高度，根据当前滚动的位置计算alpha
                if (imageViewHeight == 0) return;
                //imageViewHeight-titleBarHeight
                float alpha = (float) t / (imageViewHeight - titleBarHeight);

                if (alpha <= 0) {
                    alpha = 0;
                }
                if (alpha > 1) {
                    alpha = 1;
                }
                mTitleBar.getBackground().setAlpha((int) (alpha * 255));
            }
        });
    }
}
