package com.kaifa.customview.recycleview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kaifa.customview.recycleview.WrapRecyclerAdapter;
import com.kaifa.customview.recycleview.WrapRecyclerView;

/**
 * Created by zhb on 2018/12/5.
 */
public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    //也可以使用系统的一个属性 android.R.attrs.listDriver
    public GridLayoutItemDecoration(Context context, int drawableResId) {
        mDivider = ContextCompat.getDrawable(context, drawableResId);
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
        //绘制
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();

            int left = childView.getRight() + layoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int top = childView.getTop() - layoutParams.topMargin;
            int bottom = childView.getBottom() + layoutParams.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

        }
    }

    /**
     * 绘制水平方向
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();

            int left = childView.getLeft() - layoutParams.leftMargin;
            int right = childView.getRight() + mDivider.getIntrinsicWidth() + layoutParams.rightMargin;
            int top = childView.getBottom() + layoutParams.bottomMargin;

            int bottom = top + mDivider.getIntrinsicHeight();

            if (isLastRow(i, parent)) {//是否是最后一行
                bottom = top;
            }

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

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
        //留出分割线的位置 下边和右边
        int bottom = mDivider.getIntrinsicHeight();
        int right = mDivider.getIntrinsicWidth();

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        if (parent instanceof WrapRecyclerView) {

            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter instanceof WrapRecyclerAdapter) {
                if (isLastColumn(position - ((WrapRecyclerAdapter) adapter).getHeaderViews().size(), parent)) {//是否是最后一列
                    right = 0;
                }
                if (isLastRow(position - ((WrapRecyclerAdapter) adapter).getHeaderViews().size(), parent)) {//是否是最后一行
                    bottom = 0;
                }
                outRect.bottom = bottom;
                outRect.right = right;
            }

        }
    }

    private boolean isLastRow(int position, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int line = Double.valueOf(Math.ceil(parent.getAdapter().getItemCount() / (double) spanCount)).intValue();
        return (position + 1) > (spanCount * (line - 1));
    }

    private boolean isLastColumn(int position, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        return (position + 1) % spanCount == 0;
    }

    /**
     * 获取一行有多少列
     */
    public int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            // 获取一行的spanCount
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            return spanCount;
        }
        return 1;
    }
}
