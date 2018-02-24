package com.kaifa.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhb on 2017/10/13.
 *
 * 字母条
 */

public class LetterSideBar extends View {
    private Paint mPaint, mTouchPaint;

    // 26个字母
    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    //当前触摸位置的字母
    private String mCurrentTouchLetter;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //自定义属性 颜色 字体大小
        mPaint.setTextSize(sp2px(16));//设置的像素
        mPaint.setColor(Color.BLUE);//默认颜色

        mTouchPaint = new Paint();
        mTouchPaint.setAntiAlias(true);
        //自定义属性 颜色 字体大小
        mTouchPaint.setTextSize(sp2px(16));//设置的像素
        mTouchPaint.setColor(Color.RED);//默认颜色
    }

    /**
     * sp 转 像素
     *
     * @param sp
     * @return
     */
    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int textWidth = (int) mPaint.measureText("A");
        //计算指定宽度 = 左右的pading + 字母的宽度
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        //高度可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算出当前触摸字母 获取当前位置
                float currentMoveY = event.getY();
                //位置 = currentMoveY / 字母高度 ，通过位置获取字母
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
                int currentPosition = (int) (currentMoveY / itemHeight);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > mLetters.length - 1) {
                    currentPosition = mLetters.length - 1;
                }

                if (mLetters[currentPosition].equals(mCurrentTouchLetter)) {
                    return true;
                }

                mCurrentTouchLetter = mLetters[currentPosition];

                if (mListener != null) {
                    mListener.touch(mCurrentTouchLetter);
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.dissmis();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画26个字母
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
        for (int i = 0; i < mLetters.length; i++) {
            //x 绘制在中间 = 宽带/2 -文字/2
            int itemWidth = (int) mPaint.measureText(mLetters[i]);
            int x = getWidth() / 2 - itemWidth / 2;
            //知道每个字母的中心位置   1 字母高度的一半 2 字母高度的一半 + 前面字母的高度
            int letterCenterY = (itemHeight / 2) + i * itemHeight + getPaddingTop();
            //基线
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLine = letterCenterY + dy;

            //当前字母高亮
            if (mLetters[i].equals(mCurrentTouchLetter)) {
                canvas.drawText(mLetters[i], x, baseLine, mTouchPaint);
            } else {
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            }
        }
    }

    private LetterTouchListener mListener;

    public void setOnLetterTouchListener(LetterTouchListener mListener) {
        this.mListener = mListener;
    }

    public interface LetterTouchListener {
        void touch(CharSequence letter);

        void dissmis();
    }
}
