package com.kaifa.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhb on 2017/10/10.
 */

public class ShapeView extends View {
    private Shape mCurrentShape = Shape.Circle;
    private Paint mPaint;
    private Path mPath;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentShape) {
            case Circle:
                //画圆形
                mPaint.setColor(Color.YELLOW);
                int center = getWidth() / 2;
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                //画正方形
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case Triangle:
                //画三角形
                mPaint.setColor(Color.RED);
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    mPath.lineTo(0, (float) (getHeight() / 2 * Math.sqrt(3)));
                    mPath.lineTo(getWidth(), (float) (getHeight() / 2 * Math.sqrt(3)));
                    mPath.close();//路径闭合
                }
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public void exchange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Square;
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                mCurrentShape = Shape.Circle;
                break;
        }
        invalidate();
    }

    public enum Shape {
        Circle, Square, Triangle
    }

    public Shape getCurrentShape() {
        return mCurrentShape;
    }
}
