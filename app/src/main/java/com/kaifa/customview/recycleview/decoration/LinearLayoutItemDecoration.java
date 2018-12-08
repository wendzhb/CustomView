package com.kaifa.customview.recycleview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhb on 2018/12/5.
 */
public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    //也可以使用系统的一个属性 android.R.attrs.listDriver
    public LinearLayoutItemDecoration(Context context,int drawableResId) {
        mDivider = ContextCompat.getDrawable(context,drawableResId);
    }

    /**
     * 绘制分割线
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //利用canvas绘制，想绘制什么绘制什么
        //在每一个item的头部
        int childCount = parent.getChildCount();
        //指定绘制区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - mDivider.getIntrinsicHeight();

            mDivider.setBounds(rect);
            mDivider.draw(c);
        }
    }

    /**
     * 留出分割线位置
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //代表在每个底部的位置留出10px来绘制分割线，最后一个位置不需要分割线
        int postion = parent.getChildAdapterPosition(view);
        //parent.getChildCount()是不断变化的，现在没有办法保证最后一条
        //保证第一条
        if (postion != 0) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }
}
