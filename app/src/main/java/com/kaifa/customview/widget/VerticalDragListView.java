package com.kaifa.customview.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by zhb on 2018/2/28.
 */

public class VerticalDragListView extends FrameLayout {

    //可以认为这是系统给我们写好的一个工具
    private ViewDragHelper mDragHelper;

    private View mDragListView;

    //后面菜单的高度
    private int mMenuHeight;
    //菜单是否打开
    private boolean mMenuIsOpen = false;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, mDrawHelperCallback);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("VerticalDragListView is only support two child!!");
        }

        mDragListView = getChildAt(1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuHeight = getChildAt(0).getMeasuredHeight();
    }

   /*
    高度也可以在这几个方法内获取

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }*/

    //拖动我们的自View
    private ViewDragHelper.Callback mDrawHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //指定该子View是否可以拖动，就是child
            //后面的子View不能拖动
            return mDragListView == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //垂直拖动移动的位置
            //垂直拖动的范围,只能是后面菜单View的高度
            if (top <= 0) {
                top = 0;
            }

            if (top >= mMenuHeight) {
                top = mMenuHeight;
            }
            return top;
        }

        //列表只能垂直拖动
//        @Override
//        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            //水平拖动移动的位置
//            return left;
//        }


        //手指松开的时候两者选其一，要么打开要么关闭
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == mDragListView) {

//                if (yvel > mMenuHeight / 2) {
                if (mDragListView.getTop() > mMenuHeight / 2) {
                    //滚动到菜单的高度(打开)
                    mDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    mMenuIsOpen = true;
                } else {
                    //滚动到0的位置(关闭)
                    mDragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }
        }
    };

    /**
     * 响应滚动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    //Listview 可以滑动，但是菜单不见了
    private float mDownY;

    //
    //VerticalDragListView.onInterceptTouchEvent().Down -->ListView.OnTouch()-->
    //VerticalDragListView.onInterceptTouchEvent().Move -->VerticalDragListView.onTouchEvent().Move
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //菜单打开要全部拦截
        if (mMenuIsOpen){
            return true;
        }
        //向下滑动拦截，不要给listview做处理
        //父View拦截子View,但是子View可以调用request requestDisallowInterceptTouchEvent请求父View不要拦截
        //改变的其实就是mGroupFlags的值
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                //让DragHelper拿到一个完整的事件
                mDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if ((moveY - mDownY)>0 && !canChildScrollUp()) {
                    //向下滑动,并且滚动到了顶部的时候拦截，不让listview滑动
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     *
     *         判断View是否滚动到了最顶部
     */
    public boolean canChildScrollUp() {

        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragListView, -1) || mDragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragListView, -1);
        }
    }
}
