package com.kaifa.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.*;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2017/9/19.
 * 字体变色
 */

public class ColorTrackTextView extends android.widget.TextView {
    //实现一个文字两种颜色-绘制不变色字体的画笔
    private Paint mOriginPaint;
    //绘制变色字体的画笔
    private Paint mChangePaint;
    //当前的进度
    private float mCurrentProgress = 0f;

    //实现不同朝向
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LIFT
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    /**
     * 初始化画笔
     *
     * @param context
     * @param attrs
     */
    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, Color.BLACK);
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, Color.BLUE);

        array.recycle();

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);

    }

    /**
     * 根据颜色获取画笔
     *
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    //一个文字两种颜色
    //利用clipRect的api可以裁剪 左边用一个画笔去画 右边用另一个画笔去画  不断的改变中间值
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //canvas.clipRect(); 裁剪区域
        //根据进度算出中间值
        int middle = (int) (mCurrentProgress * getWidth());
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mOriginPaint, middle, getWidth());
            //绘制变色
            drawText(canvas, mChangePaint, 0, middle);
        } else {
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
            //绘制变色
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
        }
    }

    /**
     * 绘制text
     *
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //绘制不变色的
        canvas.clipRect(start, 0, end, getHeight());
        //自己来画
        String text = getText().toString();
        //获取字体的宽度
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseline, paint);
        canvas.restore();
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

}
