package com.kaifa.customview.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2017/9/18.
 */

public class QQStepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20;//20px
    private int mStepTextSize;
    private int mStepTextColor;
    private Paint mOuterPaint, mInnerPaint, mTextPaint;

    //总共的和当前的
    private int mStepMax = 0;
    private int mStepCurrent = 0;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定自定义属性，编写attrs.xml
        //3.布局中使用
        //4.在自定义view中获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        typedArray.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mStepTextSize);

        //5.onMeasure()
        //6.画外圆弧，内圆弧，文字
        //7.其他处理
    }

    //5.onMeasure
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能是wrap_content 宽度高度不一致
        //获取模式  at_most 40dp
        //宽高不一致，取最小值，确保是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    //6.画外圆弧，内圆弧，文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //6.1画外圆弧
        int center = getWidth() / 2;
        int radius = center - mBorderWidth / 2;
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, 135, 270, false, mOuterPaint);

        if (mStepMax == 0) {
            return;
        }

        //6.2内圆弧 怎么画不能写死 百分比 由使用者设置
        float sweepAngle = (float) mStepCurrent / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPaint);

        //6.3文字
        String stepText = mStepCurrent + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = center - textBounds.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);
    }

    //7.其他，写几个方法动起来
    public synchronized void setStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    public synchronized void setStepCurrent(int mStepCurrent) {
        this.mStepCurrent = mStepCurrent;
        //不断绘制
        invalidate();
    }

    /**
     * 开启默认动画
     * @param mStepCurrent 当前值
     * @param time  时间
     */
    public void start(int mStepCurrent, long time) {
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, mStepCurrent);
        valueAnimator.setDuration(time);
        //设置差值器，刚开始快，后来慢
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                setStepCurrent(current);
            }
        });
        valueAnimator.start();
    }


}
