package com.kaifa.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2017/9/12.
 */

public class TextView extends View {
    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;
    private Paint mPaint;

    //构造函数会在代码里面new的时候
    public TextView(Context context) {
        this(context, null);
    }

    //在布局中使用
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //在布局中使用，或有style
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText = typedArray.getString(R.styleable.TextView_custext);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_custextSize, sp2px(mTextSize));
        mTextColor = typedArray.getColor(R.styleable.TextView_custextColor, mTextColor);
        //回收
        typedArray.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
//        mPaint.setStyle(Paint.Style.STROKE);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    /**
     * 自定义view的测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高都是由这个方法指定
        //指定控件的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //1.确定的值，这个时候不需要计算，给的多少就是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.给的wrap_content 需要计算
        if (widthMode == MeasureSpec.AT_MOST) {
            //计算的宽度 与字体的长度和大小有关
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            //计算的宽度 与字体的长度和大小有关
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }
        //设置控件的宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 用于绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画文本   text    x   y   paint
        //x 就是开始的位置 0
        //y 基线 baseline
        //dy 代表高度的一半到baseline的距离
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        //top是一个负值  bottom是一个正值  top bottom 代表的是    bottom是baseline到文字底部的距离
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        int x = getPaddingLeft();
        canvas.drawText(mText, x, baseLine, mPaint);
        //画弧
//        canvas.drawArc();
        //画圆
//        canvas.drawCircle();
    }

    /**
     * 处理跟用户交互的
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://抬起
                break;
            case MotionEvent.ACTION_MOVE://移动
                break;
            case MotionEvent.ACTION_UP://按下
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
