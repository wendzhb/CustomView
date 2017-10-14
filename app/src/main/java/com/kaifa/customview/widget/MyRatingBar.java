package com.kaifa.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2017/10/10.
 */

public class MyRatingBar extends View {
    private Bitmap mNormalBitmap, mFocusBitmap;
    private int mGradeNumber;
    private int mCurrentGrade;

    public MyRatingBar(Context context) {
        this(context, null);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        int normalId = array.getResourceId(R.styleable.MyRatingBar_starNormal, 0);
        int focusId = array.getResourceId(R.styleable.MyRatingBar_starFocus, 0);
        if (normalId == 0 || focusId == 0) {
            throw new RuntimeException("请设置 startNormal和starFocus");
        }
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), normalId);
        mFocusBitmap = BitmapFactory.decodeResource(getResources(), focusId);
        mGradeNumber = array.getInt(R.styleable.MyRatingBar_gradeNumber, mGradeNumber);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度，一张图片的高度
        int height = mNormalBitmap.getHeight();
        int width = (mNormalBitmap.getWidth() + getPaddingLeft()) * mGradeNumber;//加上间隔
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mGradeNumber; i++) {
            int x = (mNormalBitmap.getWidth() + getPaddingLeft()) * i;
            //结合与用户交互
            if (mCurrentGrade > i) {
                canvas.drawBitmap(mFocusBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mNormalBitmap, x, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //移动 按下 抬起处理模式都是一样，判断手指的位置，根据当前位置计算分数，再去刷新界面
        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN://按下 尽量减少onDraw()的调用
            case MotionEvent.ACTION_MOVE://移动
//            case MotionEvent.ACTION_UP://抬起   尽量减少onDraw()的调用

                float moveX = event.getX();//event.getRawX() 获取屏幕的距离x   event.getX()获取当前控件的位置x
                int currentGrade = (int) (moveX / mNormalBitmap.getWidth() + 1);
                //范围设置
                if (currentGrade < 0) {
                    currentGrade = 0;
                }
                if (currentGrade > mGradeNumber) {
                    currentGrade = mGradeNumber;
                }
                //再去刷新显示
                //在分数相同的情况下，不要再去绘制了
                if (currentGrade == mCurrentGrade){
                    return true;
                }
                mCurrentGrade = currentGrade;
                invalidate();//onDraw 尽量减少onDraw()的调用，目前是不断调用

        }
//        return super.onTouchEvent(event);//onTouch 后面看源码      false不消费  第一次 Down false  Down以后的事件进不去了
        return true;

    }
}
