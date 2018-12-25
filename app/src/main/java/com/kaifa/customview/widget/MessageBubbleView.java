package com.kaifa.customview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.kaifa.customview.util.BubbleUtils;

/**
 * Created by zhb on 2018/12/22.
 * 仿qq消息拖拽
 */
public class MessageBubbleView extends View {


    // 2个圆的圆心
    private PointF mFixationPoint, mDragPoint;
    // 画笔
    private Paint mPaint;
    // 拖拽圆的半径
    private int mDragRadius = 10;
    // 固定圆最大半径，即就是初始半径
    private int mFixationRadiusMax = 7;
    // 固定圆最小半径
    private int mFixationRadiusMin = 3;
    private int mFixationRadius;
    // 可拖拽的
    private Bitmap mDragBitmap;

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 拖拽圆的半径
        mDragRadius = dip2px(mDragRadius);
        // 固定圆最大半径
        mFixationRadiusMax = dip2px(mFixationRadiusMax);
        // 固定圆最小半径
        mFixationRadiusMin = dip2px(mFixationRadiusMin);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
        // 设置仿抖动
        mPaint.setDither(true);

    }


//    /**
//     * 手指触摸屏幕，会触发onTouchEvent方法
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                 // 按下的时候指定当前的位置，即就是圆心位置
//                 float downX = event.getX() ;
//                 float downY = event.getY() ;
//                 initPoint(downX , downY);
//                 break;
//                 // 根据手指移动，不断的更新当前位置
//            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX() ;
//                float moveY = event.getY() ;
//                 updateDragPoint(moveX , moveY) ;
//                 break;
//            case MotionEvent.ACTION_UP:
//                 break;
//        }
//        // 只要调用invalidate()，就会调用onDraw()方法，会去画两个圆
//        invalidate();
//        return true;
//    }


    /**
     * 更新当前拖拽点的位置
     *
     * @param moveX
     * @param moveY
     */
    public void updateDragPoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
        // 重新绘制
        invalidate();
    }


    /**
     * 初始化圆心位置
     *
     * @param downX
     * @param downY
     */
    public void initPoint(float downX, float downY) {
        // 可拖拽圆的圆心位置
        mDragPoint = new PointF(downX, downY);
        // 固定圆的圆心位置
        mFixationPoint = new PointF(downX, downY);
        // 重新绘制
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }
        // 画2个圆

        // 画拖拽圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);

        // 画固定圆
        // 有一个初始化的大小 而且半径是随着距离的增大而减小 并且小到一定程度就不见了，意思就是不画了
        // 计算小圆半径，就是计算两个点的距离
        double distance = getDistance(mDragPoint, mFixationPoint);
        mFixationRadius = (int) (mFixationRadiusMax - distance / 14);


        Path bezeierPath = getBezeierPath();
        if (bezeierPath != null) {
            // 画固定圆
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
            // 画贝塞尔曲线
            canvas.drawPath(bezeierPath, mPaint);
        }


        // 获取那个没有动的 View，然后去画
        // 画图片  位置也是手指移动的位置，中心位置才是手指拖动的位置
        if (mDragBitmap != null) {
            // 搞一个渐变动画
            canvas.drawBitmap(mDragBitmap, mDragPoint.x - mDragBitmap.getWidth() / 2, mDragPoint.y - mDragBitmap.getHeight() / 2, null);
        }


    }


    /**
     * 获取两个圆之间的距离
     *
     * @param point1
     * @param point2
     * @return
     */
    private double getDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));

    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    /**
     * 获取贝塞尔的路径
     *
     * @return
     */
    public Path getBezeierPath() {
        double distance = getDistance(mDragPoint, mFixationPoint);

        // 固定圆的半径
        // 表示 distance越大 distance/14就越大 ， mFixationRadiusMax - distance/14这个差值就越小
        mFixationRadius = (int) (mFixationRadiusMax - distance / 14);
        if (mFixationRadius < mFixationRadiusMin) {
            // 超过一定距离 贝塞尔和固定圆都不要画了
            return null;
        }

        Path bezeierPath = new Path();

        // 求角 a
        // 求斜率
        float dy = (mDragPoint.y - mFixationPoint.y);
        float dx = (mDragPoint.x - mFixationPoint.x);
        float tanA = dy / dx;
        // 求角a
        double arcTanA = Math.atan(tanA);

        // p0
        float p0x = (float) (mFixationPoint.x + mFixationRadius * Math.sin(arcTanA));
        float p0y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(arcTanA));

        // p1
        float p1x = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTanA));
        float p1y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTanA));

        // p2
        float p2x = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTanA));
        float p2y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTanA));

        // p3
        float p3x = (float) (mFixationPoint.x - mFixationRadius * Math.sin(arcTanA));
        float p3y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(arcTanA));

        // 拼装 贝塞尔的曲线路径
        bezeierPath.moveTo(p0x, p0y); // 移动
        // 两个点
        PointF controlPoint = getControlPoint();
        // 画了第一条  第一个点（控制点,两个圆心的中心点），终点
        bezeierPath.quadTo(controlPoint.x, controlPoint.y, p1x, p1y);

        // 画第二条
        bezeierPath.lineTo(p2x, p2y); // 链接到
        bezeierPath.quadTo(controlPoint.x, controlPoint.y, p3x, p3y);
        bezeierPath.close();

        return bezeierPath;
    }


    /**
     * 获取控制点
     *
     * @return
     */
    public PointF getControlPoint() {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
    }


    /**
     * 绑定可以拖拽的控件
     *
     * @param view
     * @param listener 消失的监听listener
     */
    public static void attach(View view, BubbleMessageTouchListener.BubbleDisappearListener listener) {
        view.setOnTouchListener(new BubbleMessageTouchListener(view, view.getContext(), listener));
    }

    public void setDragBitmap(Bitmap dragBitamp) {
        this.mDragBitmap = dragBitamp;

    }


    /**
     * 处理手指松开的时候，回弹的效果
     */
    public void handleActionUp() {
        if (mFixationRadius > mFixationRadiusMin) {
            // 回弹的动画效果   值动画，从0变化到1
            ValueAnimator animator = ObjectAnimator.ofFloat(1);
            // 时长
            animator.setDuration(300);
            final PointF start = new PointF(mDragPoint.x, mDragPoint.y);
            final PointF end = new PointF(mFixationPoint.x, mFixationPoint.y);

            // 设置监听
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float percent = (float) animation.getAnimatedValue(); //
                    PointF pointF = BubbleUtils.getPointByPercent(start, end, percent);
                    // 更新当前的拖拽点
                    updateDragPoint(pointF.x, pointF.y);
                }
            });

            // 设置差值器 ， 在结束的时候回弹一下
            animator.setInterpolator(new OvershootInterpolator(3f));
            animator.start();

            // 还要通知 TouchListener移除当前的View，然后显示静态的View
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mListener != null) {
                        mListener.restore();
                    }
                }
            });


        } else {
            // 爆炸
            if (mListener != null) {
                mListener.dismiss(mDragPoint);
            }
        }
    }


    private MessageBubbleListener mListener;

    public void setMessBubbleListener(MessageBubbleListener listener) {
        this.mListener = listener;
    }

    /**
     * 监听气泡消失
     */
    public interface MessageBubbleListener {
        // 还原
        public void restore();

        // 消失爆炸
        public void dismiss(PointF pointF);
    }
}
