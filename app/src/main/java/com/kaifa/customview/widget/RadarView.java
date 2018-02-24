package com.kaifa.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2018/2/2.
 */

public class RadarView extends View {

    //每个圆圈所占的比例
    private static float[] circleProportion = {1 / 13f, 2 / 13f, 3 / 13f, 4 / 13f, 5 / 13f, 6 / 13f};
    private Paint mPaintCircle;//画圆需要用到的paint
    private Bitmap centerBitmap;//最中间icon

    private int height;
    private int width;

    private Paint mPaintScan;//画扫描需要用到的paint
    private Matrix matrix = new Matrix();//旋转需要的矩阵
    private int mRoteDegree;//扫描旋转的角度
    private Shader scanShader;//扫描渲染shader

    public Runnable run = new Runnable() {
        @Override
        public void run() {
            mRoteDegree = 2;
            matrix.postRotate(mRoteDegree, width / 2, height / 2);
            invalidate();
            postDelayed(run, 60);
        }
    };

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintScan = new Paint();

        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.BLUE);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        // 通过bitmap工厂区获取用户图像的bitmap
        centerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制六个白色圆圈
        drawCircle(canvas);
        drawScan(canvas);
        drawCenterIcon(canvas);
    }

    /**
     * 绘制扫描
     *
     * @param canvas
     */
    private void drawScan(Canvas canvas) {
        canvas.save();
        mPaintScan.setShader(scanShader);
        canvas.concat(matrix);
        canvas.drawCircle(width / 2, height / 2, width * circleProportion[3], mPaintScan);
        canvas.restore();

    }

    /**
     * 绘制最中间的图标
     *
     * @param canvas
     */
    private void drawCenterIcon(Canvas canvas) {
        float iconWidth = (width * circleProportion[0]);
        canvas.drawBitmap(centerBitmap, width / 2 - (centerBitmap.getWidth() / 2), height / 2 - (centerBitmap.getHeight() / 2), null);
    }

    /**
     * 绘制圆线圈
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, circleProportion[1] * width, mPaintCircle);
        canvas.drawCircle(width / 2, height / 2, circleProportion[2] * width, mPaintCircle);
        canvas.drawCircle(width / 2, height / 2, circleProportion[3] * width, mPaintCircle);
        canvas.drawCircle(width / 2, height / 2, circleProportion[4] * width, mPaintCircle);
        canvas.drawCircle(width / 2, height / 2, circleProportion[5] * width, mPaintCircle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        width = height = Math.min(width, height);

        //设置扫描渲染的shader
        scanShader = new SweepGradient(width / 5, height / 2,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
    }
}
