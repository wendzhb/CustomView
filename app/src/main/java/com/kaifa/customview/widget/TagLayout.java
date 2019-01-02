package com.kaifa.customview.widget;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhb on 2017/10/23.
 * 流式布局
 */

public class TagLayout extends ViewGroup {

    private List<List<View>> mChildViews = new ArrayList<>();

    private TagAdapter mAdapter;
    private DataSetObserver mDataSetObserver;

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 指定宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildViews.clear();
        //for循环测量子view
        int childCount = getChildCount();
        //获取到宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高度需要计算
        int height = getPaddingTop() + getPaddingBottom();

        ArrayList<View> childViews = new ArrayList<>();
        //一行的宽度
        int lineWidth = getPaddingLeft();

        //子view高度不一致的情况下
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            //这个方法执行之后，就可以获取子view的宽高，因为会调用子view的onMeasure方法
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //margin值 viewgroup.params 没有
            //LinearLayout有自己的 LayoutParams 会复写一个非常重要的方法
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

            if (lineWidth + (childView.getMeasuredWidth() + params.rightMargin + params.leftMargin) > width) {
                //换行,累加高度
                height += maxHeight;
                maxHeight = childView.getMeasuredHeight() + params.bottomMargin + params.topMargin;
                lineWidth = childView.getMeasuredWidth() + params.rightMargin + params.leftMargin;
                mChildViews.add(childViews);
                childViews = new ArrayList<>();
            } else {
                lineWidth += (childView.getMeasuredWidth() + params.rightMargin + params.leftMargin);
                maxHeight = Math.max(childView.getMeasuredHeight() + params.bottomMargin + params.topMargin, maxHeight);
            }
            childViews.add(childView);
        }
        mChildViews.add(childViews);
        height += maxHeight;
        //根据子view计算和指定自己的布局
        setMeasuredDimension(width, height);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 摆放子view
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top = getPaddingTop(), right, bootom;

        for (List<View> mChildView : mChildViews) {
            int maxTop = 0;
            left = getPaddingLeft();
            for (View view : mChildView) {
                if (view.getVisibility() == GONE) {
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                left += params.leftMargin;
                int childTop = top + params.topMargin;
                right = left + view.getMeasuredWidth();
                bootom = childTop + view.getMeasuredHeight();
                //摆放
                view.layout(left, childTop, right, bootom);
                left += view.getMeasuredWidth();
                maxTop = Math.max(view.getMeasuredHeight() + params.topMargin + params.bottomMargin, maxTop);
            }
            //不断地叠加top值
            top += maxTop;
        }
    }

    public void addTags(List<String> mDatas) {

    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(TagAdapter adapter) {
        if (this.mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mAdapter = null;
        }

        if (adapter == null) {
            throw new NullPointerException("adapter is not null");
        }

        //清空所有子view
        removeAllViews();

        this.mAdapter = adapter;

        mDataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                resetLayout();
            }
        };
        mAdapter.registerDataSetObserver(mDataSetObserver);
        resetLayout();
    }

    private void resetLayout() {
        //获取数量
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            //通过位置获取view
            View view = mAdapter.getView(i, this);
            addView(view);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    public abstract static class TagAdapter {
        private final DataSetObservable mDataSetObservable = new DataSetObservable();
        // 子view的集合
        private final ArrayList<View> views = new ArrayList<View>();

        //1.有多少个条目
        public abstract int getCount();

        public abstract View getView(int position, ViewGroup parent);

        //观察者模式
        public void unregisterDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.unregisterObserver(observer);
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.registerObserver(observer);
        }

        public void notifyDateSetChange() {
            mDataSetObservable.notifyChanged();
        }
    }
}
