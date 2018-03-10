package com.kaifa.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2017/12/21.
 */

public class QQSlidingMenu extends HorizontalScrollView {

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * dp
     */
    private int mMenuRightPadding = 50;

    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    private int mHalfMenuWidth;

    private ViewGroup mMenu;
    private ViewGroup mContent;

    private View mShadowView;

    /**
     * 菜单是否打开
     */

    //处理快速滑动
    private GestureDetector mGestureDetector;
    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //快速滑动,只要快速滑动就会回调
            //条件 打开的时候往右边快速滑动切换(关闭)， 关闭的时候往左边快速滑动（打开）
            //快速往右边滑动是一个正数，往左边是一个负数
            if (isOpen) {
                //打开的时候往右边快速滑动切换(关闭)
                if (velocityX < 0) {
                    closeMenu();
                    return true;
                }
            } else {
                //关闭的时候往左边快速滑动（打开）
                if (velocityX > 0) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    private boolean once;
    private boolean isOpen;
    private boolean mIsIntercept;

    public QQSlidingMenu(Context context) {
        this(context, null);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    // 默认50
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50f,
                                    getResources().getDisplayMetrics()));// 默认为10DP
                    break;
            }
        }
        a.recycle();

        mGestureDetector = new GestureDetector(context, mOnGestureListener);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显示的设置一个宽度
         */
        if (!once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) wrapper.getChildAt(0);
            mContent = (ViewGroup) wrapper.getChildAt(1);

            // dp to px
            mMenuRightPadding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, mContent
                            .getResources().getDisplayMetrics());
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            mMenu.getLayoutParams().width = mMenuWidth;

            //把内容布局单独提取出来
            wrapper.removeView(mContent);
            //套一层阴影
            RelativeLayout contentContainer = new RelativeLayout(getContext());
            contentContainer.addView(mContent);
            mShadowView = new View(getContext());
            mShadowView.setBackgroundColor(Color.parseColor("#55000000"));
            contentContainer.addView(mShadowView);
            //放回到原来的位置
            mContent.getLayoutParams().width = mScreenWidth;
            contentContainer.setLayoutParams(mContent.getLayoutParams());
            wrapper.addView(contentContainer);
            mShadowView.setAlpha(0.0f);



        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    //XML 解析完毕
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果有拦截不要执行自己的onTouch
        if (mIsIntercept) {
            return true;
        }
        //获取手指滑动的速率，当大于一定值的时候就认为是快速滑动 GestureDetector(系统提供好的类)
        //处理事件拦截+viewgroup事件分发
        //快速滑动触发了下面的就不要执行了
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }

        switch (ev.getAction()) {
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth) {
                    closeMenu();

                } else {
                    openMenu();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;

        //控制阴影
        float alphaScale = 1 - scale;
        mShadowView.setAlpha(alphaScale);
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;


//        ViewCompat.setScaleX(mMenu, leftScale);
//        ViewCompat.setScaleY(mMenu, leftScale);
//        ViewCompat.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
//        ViewCompat.setTranslationX(mMenu, mMenuWidth * scale * 0.6f);

//        ViewCompat.setPivotX(mContent, 0);
//        ViewCompat.setPivotY(mContent, mContent.getHeight() / 2);
//        ViewCompat.setScaleX(mContent, rightScale);
//        ViewCompat.setScaleY(mContent, rightScale);

        ViewCompat.setTranslationX(mMenu, mMenuWidth * scale * 0.6f);


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当菜单打开的时候，手指触摸右边内容需要关闭菜单，还需要拦截事件（打开情况下点击内容也不会响应点击事件）
        if (isOpen) {
            float currentX = ev.getX();
            if (currentX > mMenuWidth) {
                mIsIntercept = true;
                closeMenu();
                return true;//返回true，拦截子View的事件，但是会响应自己的onTouch事件
            }
        } else {
            mIsIntercept = false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
