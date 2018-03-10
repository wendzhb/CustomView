//package com.kaifa.customview.widget;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Point;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.View;
//
///**
// * Created by zhb on 2018/2/24.
// */
//
//public class LockPatternView extends View {
//
//    // 是否初始化
//    private int[][] mPoints = new int[3][3];
//    private boolean mIsInit = false;
//    private int mWidth = 0;
//    private int mHeight = 0;
//    // 外圆的半径
//    private int mDotRadius = 0;
//    // 画笔
//    private Paint mLinePaint;
//    private Paint mPressedPaint;
//    private Paint mErrorPaint;
//    private Paint mNormalPaint;
//    private Paint mArrowPaint;
//    // 颜色
//    private int mOuterPressedColor = 0xff8cbad8;
//    private int mInnerPressedColor = 0xff0596f6;
//    private int mOuterNormalColor = 0xffd9d9d9;
//    private int mInnerNormalColor = 0xff929292;
//    private int mOuterErrorColor = 0xff901032;
//    private int mInnerErrorColor = 0xffea0945;
//
//    public LockPatternView(Context context) {
//        this(context, null);
//    }
//
//    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (!mIsInit) {
//            initPoints();
//        }
//
//        drawToCanvas(canvas);
//    }
//
//    private void drawToCanvas(Canvas canvas) {
//        for (int i = 0; i < mPoints.length; i++) {
//            for (int j = 0; j < mPoints[i].length; j++) {
//                int point = mPoints[i][j];
//                if (point != 0) {
//                    // 循环绘制默认圆
//                    mNormalPaint!!.color = mOuterNormalColor
//                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), mDotRadius.toFloat(), mNormalPaint!!)
//                    mNormalPaint!!.color = mInnerNormalColor
//                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat(), (mDotRadius / 6).toFloat(), mNormalPaint!!
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 初始化点
//     */
//    private void initPoints() {
//        mWidth = width;
//        mHeight = height;
//
//        int offsetX = 0;
//        int offsetY = 0;
//
//        if (mWidth > mHeight) {
//            offsetX = (mWidth - mHeight) / 2;
//            mWidth = mHeight
//        } else {
//            offsetY = (mHeight - mWidth) / 2;
//            mHeight = mWidth
//        }
//
//        mDotRadius = mWidth / 12;
//
//        int padding = mDotRadius / 2;
//        int sideSize = (mWidth - 2 * padding) / 3;
//        offsetX += padding;
//        offsetY += padding;
//
//        for (i in mPoints.indices) {
//            for (j in mPoints.indices) {
//                // 循环初始化九个点
//                mPoints[i][j] = Point(offsetX + sideSize * (i * 2 + 1) / 2,
//                        offsetY + sideSize * (j * 2 + 1) / 2, i * mPoints.size + j)
//            }
//        }
//
//        initPaint();
//
//        mIsInit = true;
//    }
//
//
//    private void initPaint() {
//        // 线的画笔
//        mLinePaint = Paint();
//        mLinePaint!!.color = mInnerPressedColor
//        mLinePaint!!.style = Paint.Style.STROKE
//        mLinePaint!!.isAntiAlias = true
//        mLinePaint!!.strokeWidth = (mDotRadius / 9).toFloat()
//        // 按下的画笔
//        mPressedPaint = Paint();
//        mPressedPaint!!.style = Paint.Style.STROKE
//        mPressedPaint!!.isAntiAlias = true
//        mPressedPaint!!.strokeWidth = (mDotRadius / 6).toFloat()
//        // 错误的画笔
//        mErrorPaint = Paint();
//        mErrorPaint!!.style = Paint.Style.STROKE
//        mErrorPaint!!.isAntiAlias = true
//        mErrorPaint!!.strokeWidth = (mDotRadius / 6).toFloat()
//        // 默认的画笔
//        mNormalPaint = Paint();
//        mNormalPaint!!.style = Paint.Style.STROKE
//        mNormalPaint!!.isAntiAlias = true
//        mNormalPaint!!.strokeWidth = (mDotRadius / 9).toFloat()
//        // 箭头的画笔
//        mArrowPaint = Paint();
//        mArrowPaint!!.color = mInnerPressedColor
//        mArrowPaint!!.style = Paint.Style.FILL
//        mArrowPaint!!.isAntiAlias = true
//    }
//
//}
