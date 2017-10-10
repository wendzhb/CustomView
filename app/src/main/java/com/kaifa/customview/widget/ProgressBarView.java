package com.kaifa.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2017/10/9.
 * 自定义圆形进度条
 */

public class ProgressBarView extends View {
    private int mInnerBackground = Color.RED;
    private int mOuterBackground = Color.BLACK;
    private float mRoundWidth = 10;
    private int mProgressTextSize = 15;
    private int mProgressTextColor = Color.RED;

    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private Paint mTextPaint;
    private int mMAX = 100;
    private int mProgress = 0;

    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
        mInnerBackground = typedArray.getColor(R.styleable.ProgressBarView_innerBackground, mInnerBackground);
        mOuterBackground = typedArray.getColor(R.styleable.ProgressBarView_outerBackground, mOuterBackground);
        mProgressTextColor = typedArray.getColor(R.styleable.ProgressBarView_progressTextColor, mProgressTextColor);
        mRoundWidth = typedArray.getDimension(R.styleable.ProgressBarView_roundWidth, dip2px(mRoundWidth));
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.ProgressBarView_progressTextSize, sp2px(mProgressTextSize));
        typedArray.recycle();

        mInnerPaint = initPaint(mInnerBackground);
        mOuterPaint = initPaint(mOuterBackground);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
    }

    private Paint initPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(mRoundWidth);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private float dip2px(float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        //先画内圆
        canvas.drawCircle(center, center, center - mRoundWidth / 2, mInnerPaint);
        //画外圆
        RectF rectF = new RectF(mRoundWidth / 2, mRoundWidth / 2, getWidth() - mRoundWidth / 2, getHeight() - mRoundWidth / 2);
        if (mProgress == 0) {
            return;
        }
        float percent = (float) mProgress / mMAX;
        canvas.drawArc(rectF, 90, percent * 360, false, mOuterPaint);
        //画文字
        String text = (int)(percent * 100) + "%";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int dx = center - textBounds.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalStateException("不能小于0");
        }
        this.mMAX = max;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalStateException("不能小于0");
        }
        this.mProgress = progress;
        //重绘刷新
        invalidate();
    }

}
