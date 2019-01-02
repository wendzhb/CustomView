package com.kaifa.customview.widget.listscreenmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by zhb on 2018/12/14.
 * 多条目菜单
 */
public class ListDataScreenView extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    //1.1创建头部用来存放tab
    private LinearLayout mMenuTabView;
    //1.2创建framelayout用来存放 = 阴影（view） + 菜单内容布局（framelayout）
    private FrameLayout mMenuMiddleView;
    //阴影
    private View mShadowView;
    //阴影的颜色
    private int mShadowColor = 0x88888888;
    //创建菜单用来存放菜单内容
    private FrameLayout mMenuContainerView;
    //筛选菜单的adapter
    private BaseMenuAdapter mAdapter;
    //内容菜单的高度
    private int mMenuContainerHeight;
    //当前打开的位置
    private int mCurrentPosition = -1;
    private long DURATION_TIME = 350;
    //动画是否在执行
    private boolean mAnimatorExecute = false;

    public ListDataScreenView(Context context) {
        this(context, null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
    }

    /**
     * 1.布局实例化（组合控件）
     */
    private void initLayout() {
        //1.先创建一个xml布局，在加载findviewbyid
        //2.简单的效果用代码去创建
        setOrientation(VERTICAL);
        //1.1创建头部用来存放tab
        mMenuTabView = new LinearLayout(mContext);
        mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);
        //1.2创建framelayout用来存放 = 阴影（view） + 菜单内容布局（framelayout）
        mMenuMiddleView = new FrameLayout(mContext);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mMenuMiddleView.setLayoutParams(params);
        addView(mMenuMiddleView);
        //创建阴影 可以不用设置layoutparams 默认就是match_parent
        mShadowView = new View(mContext);
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0f);
        mShadowView.setVisibility(GONE);
        mShadowView.setOnClickListener(this);
        mMenuMiddleView.addView(mShadowView);
        //创建菜单用来存放菜单内容
        mMenuContainerView = new FrameLayout(mContext);
        mMenuContainerView.setBackgroundColor(Color.WHITE);
        mMenuMiddleView.addView(mMenuContainerView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //内容的高度不是全部而是高度的75%
        if (mMenuContainerHeight == 0) {

            int height = MeasureSpec.getSize(heightMeasureSpec);
            mMenuContainerHeight = (int) (height * 75f / 100);
            ViewGroup.LayoutParams params = mMenuContainerView.getLayoutParams();
            params.height = mMenuContainerHeight;
            mMenuContainerView.setLayoutParams(params);
            //进来的时候阴影不显示，内容也不显示（移上去）
            mMenuContainerView.setTranslationY(-mMenuContainerHeight);
        }
    }

    public void setAdapter(BaseMenuAdapter adapter) {

        //观察者
        if (mAdapter != null && mMenuObserver != null) {
            mAdapter.unregisterDataSetObserver(mMenuObserver);
        }

        mAdapter = adapter;
        //注册观察者,具体的实例对象
        mMenuObserver = new AdapterDataSetObserver();
        mAdapter.registerDataSetObserver(mMenuObserver);

        //获取有多少条
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            //获取Tab
            View tabView = mAdapter.getTabView(i, mMenuTabView);
            mMenuTabView.addView(tabView);
            LinearLayout.LayoutParams params = (LayoutParams) tabView.getLayoutParams();
            params.weight = 1;
            tabView.setLayoutParams(params);
            //设计点击事件
            setTabClick(tabView, i);
            //获取菜单的内容
            View menuView = mAdapter.getMenuView(i, mMenuContainerView);
            mMenuContainerView.addView(menuView);
            menuView.setVisibility(GONE);

        }
    }

    /**
     * 设置tab点击事件
     *
     * @param tabView
     * @param position
     */
    private void setTabClick(final View tabView, final int position) {
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition == -1) {
                    //没打开的时候点击打开
                    openMenu(position, tabView);
                } else {
                    //打开了的时候
                    if (mCurrentPosition == position) {
                        //关闭
                        closeMenu();
                    } else {
                        //切换
                        View currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(INVISIBLE);
                        mCurrentPosition = position;
                        currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(VISIBLE);
                        mAdapter.menuOpen(mMenuTabView.getChildAt(mCurrentPosition));
                    }
                }
            }
        });
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        if (mAnimatorExecute) {
            return;
        }
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0, -mMenuContainerHeight);
        translationAnimator.setDuration(DURATION_TIME);
        translationAnimator.start();

        mShadowView.setVisibility(VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f);
        alphaAnimator.setDuration(DURATION_TIME);

        //关闭动画执行完去隐藏当前菜单
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAnimatorExecute = true;

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                View view = mMenuContainerView.getChildAt(mCurrentPosition);
                view.setVisibility(GONE);
                mCurrentPosition = -1;
                mShadowView.setVisibility(GONE);
                mAnimatorExecute = false;
                mAdapter.closeMenu(mMenuTabView.getChildAt(mCurrentPosition));


            }
        });
        alphaAnimator.start();

    }

    /**
     * 打开菜单
     *
     * @param position
     * @param tabView
     */
    private void openMenu(final int position, final View tabView) {
        if (mAnimatorExecute) {
            return;
        }
        //获取当前位置显示菜单
        View view = mMenuContainerView.getChildAt(position);
        view.setVisibility(VISIBLE);
        //位移 透明度动画
        mShadowView.setVisibility(VISIBLE);

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0);
        translationAnimator.setDuration(DURATION_TIME);
        translationAnimator.start();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f);
        alphaAnimator.setDuration(DURATION_TIME);

        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorExecute = false;
                mCurrentPosition = position;

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAnimatorExecute = true;
                //把当前的tab传到外面
                mAdapter.menuOpen(tabView);

            }
        });
        alphaAnimator.start();

    }

    @Override
    public void onClick(View v) {
        closeMenu();
    }

    private AdapterDataSetObserver mMenuObserver;

    /**
     * 具体的观察者
     */
    private class AdapterDataSetObserver extends MenuObserver {

        @Override
        public void closeMenu() {
            ListDataScreenView.this.closeMenu();
        }
    }
}
