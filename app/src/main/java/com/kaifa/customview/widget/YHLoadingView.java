package com.kaifa.customview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2018/12/25.
 * 雅虎加载动画
 */
public class YHLoadingView extends View {
    //旋转动画执行的时间
    private static final long ROTATION_ANIMATION_TIME = 3000;
    //当前大圆的角度-弧度
    private float mCurrentRotationAngle = 0f;

    //大圆的半径-包含很多小圆
    private float mRotationRadius;
    //小圆的半径
    private float mCircleRadius;
    //当前大圆的半径
    private float mCurrentRotationRadius = mRotationRadius;
    //空心圆的半径
    private float mHoleRadius = 0F;
    //屏幕对角线的一半
    private float mDiagonalDist;

    //小圆的颜色列表
    private int[] mCircleColors;
    //绘制所有效果的画笔
    private Paint mPaint;

    //中心店
    private int mCenterX, mCenterY;
    //整体的颜色背景
    private int mSplashColor = Color.WHITE;


    //当前状态所画的动画
    private LoadingState mLoadingState;

    public YHLoadingView(Context context) {
        this(context, null);
    }

    public YHLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YHLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //小圆的颜色列表
        mCircleColors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    private boolean mInitParams = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mInitParams) {
            initParams();
        }

        if (mLoadingState == null) {
            mLoadingState = new RotationState();
        }

        mLoadingState.draw(canvas);
    }

    /**
     * 初始化半径
     */
    private void initParams() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mRotationRadius = getMeasuredWidth() / 4;
        mCircleRadius = mRotationRadius / 8;

        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;

        mDiagonalDist = (float) Math.sqrt(mCenterX * mCenterX + mCenterY * mCenterY);
        mInitParams = true;
    }

    /**
     * 消失
     */
    public void disappear() {
        //关闭旋转动画
        if (mLoadingState instanceof RotationState) {
            RotationState rotationState = (RotationState) this.mLoadingState;
            rotationState.cancel();
        }
        //开始聚合动画
        mLoadingState = new MergeState();

    }

    public abstract class LoadingState {
        public abstract void draw(Canvas canvas);
    }

    /**
     * 旋转动画
     */
    public class RotationState extends LoadingState {
        private ValueAnimator mAnimator;

        public RotationState() {
            //一个变量不断地去改变 采用属性动画 0-360
            if (mAnimator == null) {
                mAnimator = ObjectAnimator.ofFloat(0f, (float) (2 * Math.PI));
                mAnimator.setDuration(ROTATION_ANIMATION_TIME);
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mCurrentRotationAngle = (float) animation.getAnimatedValue();

                        //重新绘制
                        invalidate();
                    }
                });
                mAnimator.setInterpolator(new LinearInterpolator());
                mAnimator.setRepeatCount(-1);
                mAnimator.start();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            //画一个背景
            canvas.drawColor(mSplashColor);
            //画圆
            //每份的角度
            double percentAngle = 2 * Math.PI / mCircleColors.length;
            for (int i = 0; i < mCircleColors.length; i++) {
                //当前的角度 = 初始角度+旋转的角度
                double currentAngle = percentAngle * i + mCurrentRotationAngle;

                int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));

                mPaint.setColor(mCircleColors[i]);
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

        /**
         * 取消动画
         */
        public void cancel() {
            mAnimator.cancel();
        }
    }

    /**
     * 聚合动画
     */
    public class MergeState extends LoadingState {

        private ValueAnimator mAnimator;

        public MergeState() {
            mAnimator = ObjectAnimator.ofFloat(mRotationRadius, 0);
            mAnimator.setDuration(ROTATION_ANIMATION_TIME / 2);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();//最大半径到0

                    //重新绘制
                    invalidate();
                }
            });
            //开始的时候向后然后向前
            mAnimator.setInterpolator(new AnticipateInterpolator(3f));
            //等聚合动画完毕
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mLoadingState = new ExpendState();
                }
            });
            mAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            //画一个背景
            canvas.drawColor(mSplashColor);
            //画圆
            //每份的角度
            double percentAngle = 2 * Math.PI / mCircleColors.length;
            for (int i = 0; i < mCircleColors.length; i++) {
                //当前的角度 = 初始角度+旋转的角度
                double currentAngle = percentAngle * i + mCurrentRotationAngle;

                int cx = (int) (mCenterX + mCurrentRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mCurrentRotationRadius * Math.sin(currentAngle));

                mPaint.setColor(mCircleColors[i]);
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }

        }
    }

    /**
     * 展开动画
     */
    public class ExpendState extends LoadingState {

        private ValueAnimator mAnimator;

        public ExpendState() {
            mAnimator = ObjectAnimator.ofFloat(0, mDiagonalDist);
            mAnimator.setDuration(ROTATION_ANIMATION_TIME / 2);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();

                    //重新绘制
                    invalidate();
                }
            });
            mAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            //绘制一个圆
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);

            float radius = strokeWidth / 2 + mHoleRadius;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
        }
    }

}
