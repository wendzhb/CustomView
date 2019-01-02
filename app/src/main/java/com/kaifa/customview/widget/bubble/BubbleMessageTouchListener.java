package com.kaifa.customview.widget.bubble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2018/12/22.
 */
public class BubbleMessageTouchListener implements MessageBubbleView.MessageBubbleListener, View.OnTouchListener {

    // 在开始位置、需要拖动爆炸的View
    private View mStaticView;
    // 获取WindowManager
    private WindowManager mWindowManager;
    // 创建一个view 可拖拽的、贝塞尔曲线的View
    private MessageBubbleView mMessageBubbleView;
    // 布局参数
    private WindowManager.LayoutParams mParams;
    // 上下文
    private Context mContext;
    // 爆炸动画
    private FrameLayout mBombFrame;
    private ImageView mBombImage;
    private BubbleDisappearListener mDisappearListener;


    public BubbleMessageTouchListener(View view, Context context, BubbleDisappearListener disappearListener) {
        this.mStaticView = view;
        // WindowManager
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 可拖拽的、贝塞尔曲线的View
        mMessageBubbleView = new MessageBubbleView(context);
        mMessageBubbleView.setMessBubbleListener(this);


        // 布局参数
        mParams = new WindowManager.LayoutParams();
        // 背景要透明
        mParams.format = PixelFormat.TRANSPARENT;
        // 上下文
        this.mContext = context;


        // 爆炸动画
        mBombFrame = new FrameLayout(mContext);
        mBombImage = new ImageView(mContext);
        mBombImage.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // 把图片加到爆炸动画里边
        mBombFrame.addView(mBombImage);
        this.mDisappearListener = disappearListener;

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 在WindowManager上面要搞一个View，而这个View就是我们写好的 贝塞尔的View，然后把这个贝塞尔的View 添加到WindowManager上面来
                mWindowManager.addView(mMessageBubbleView, mParams);

                // 初始化 贝塞尔View上边的点
                int[] location = new int[2];
                mStaticView.getLocationOnScreen(location);
                Bitmap bitmap = getBitmapByView(mStaticView);

                // 初始化贝塞尔View 的 点 , 也就是手指按下的点  获取屏幕的位置
//                mMessageBubbleView.initPoint(location[0] + mStaticView.getWidth() / 2,
//                        location[1] + mStaticView.getWidth() / 2 - BubbleUtils.getStatusBarHeight(mContext));
                mMessageBubbleView.initPoint(location[0] + mStaticView.getWidth() / 2,
                        location[1] + mStaticView.getHeight() / 2- BubbleUtils.getStatusBarHeight(mContext) );
                // 给mMessageBubbleView 设置一个bitmap
                mMessageBubbleView.setDragBitmap(bitmap);


                break;
            // 在不断的触摸贝塞尔View上的点的时候，不断更新该拖拽点的位置    获取屏幕的位置
            case MotionEvent.ACTION_MOVE:
                // 按下屏幕上边该TextView的时候，就把这个TextView隐藏，这里通过构造方法获取TextView
                mStaticView.setVisibility(View.INVISIBLE);
                mMessageBubbleView.updateDragPoint(event.getRawX(), event.getRawY() - BubbleUtils.getStatusBarHeight(mContext));
                break;
            case MotionEvent.ACTION_UP:
                // 处理手指松开的时候，回弹的效果
                mMessageBubbleView.handleActionUp();
                break;
        }
        return true;
    }


    /**
     * 从一个View中获取 Bitmap
     *
     * @param view
     * @return
     */
    private Bitmap getBitmapByView(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    /**
     * 爆炸动画还原
     */
    @Override
    public void restore() {
        // 把消息的View 从 WindowManager移除
        mWindowManager.removeView(mMessageBubbleView);
        // 把原来的View显示
        mStaticView.setVisibility(View.VISIBLE);
    }


    /**
     * 爆炸动画消失
     *
     * @param pointF
     */
    @Override
    public void dismiss(PointF pointF) {
        // 要去执行爆炸动画 （帧动画）
        // 原来的View的View肯定要移除
        mWindowManager.removeView(mMessageBubbleView);
        // 要在 mWindowManager 添加一个爆炸动画
        mWindowManager.addView(mBombFrame, mParams);
        mBombImage.setBackgroundResource(R.drawable.anim_bubble_pop);

        AnimationDrawable drawable = (AnimationDrawable) mBombImage.getBackground();
        mBombImage.setX(pointF.x - drawable.getIntrinsicWidth() / 2);
        mBombImage.setY(pointF.y - drawable.getIntrinsicHeight() / 2);

        drawable.start();
        // 等它执行完之后我要移除掉这个 爆炸动画也就是 mBombFrame
        mBombImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(mBombFrame);
                // 通知一下外面该消失
                if (mDisappearListener != null) {
                    mDisappearListener.dismiss(mStaticView);
                }
            }
        }, getAnimationDrawableTime(drawable));
    }


    private long getAnimationDrawableTime(AnimationDrawable drawable) {
        int numberOfFrames = drawable.getNumberOfFrames();
        long time = 0;
        for (int i = 0; i < numberOfFrames; i++) {
            time += drawable.getDuration(i);
        }
        return time;
    }


    public interface BubbleDisappearListener {
        void dismiss(View view);
    }

}
