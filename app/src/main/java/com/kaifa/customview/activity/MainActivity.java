package com.kaifa.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.BlurView;
import com.kaifa.customview.widget.QQNaviView;
import com.kaifa.customview.widget.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bv)
    BlurView bv;
    @BindView(R.id.iv)
    ImageView iv;
    private QQNaviView mBubbleView;
    private QQNaviView mPersonView;
    private QQNaviView mStarView;

    private SearchFragment searchFragment;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mBubbleView = (QQNaviView) findViewById(R.id.qq_view_bubble);
        mPersonView = (QQNaviView) findViewById(R.id.qq_view_person);
        mStarView = (QQNaviView) findViewById(R.id.qq_view_star);

    }

    @Override
    protected void initData() {
        searchFragment = SearchFragment.newInstance();
    }

    @Override
    protected void setData() {
        mBubbleView.setBigIcon(R.drawable.bubble_big);
        mBubbleView.setSmallIcon(R.drawable.bubble_small);
        bv.setBlurredView(iv);
    }

    @Override
    protected void setListener() {
    }

    private void resetIcon() {
        mBubbleView.setBigIcon(R.drawable.pre_bubble_big);
        mBubbleView.setSmallIcon(R.drawable.pre_bubble_small);

        mPersonView.setBigIcon(R.drawable.pre_person_big);
        mPersonView.setSmallIcon(R.drawable.pre_person_small);

        mStarView.setBigIcon(R.drawable.pre_star_big);
        mStarView.setSmallIcon(R.drawable.pre_star_small);
    }

    @OnClick({R.id.point, R.id.search, R.id.qq_view_bubble, R.id.qq_view_person, R.id.md, R.id.qq_view_star, R.id.bt_01,R.id.bt_15,R.id.bt_16,R.id.bt_17,
            R.id.bt_02, R.id.bt_03, R.id.bt_04, R.id.bt_05, R.id.bt_06, R.id.bt_07, R.id.vp, R.id.loading, R.id.qqtoolbar,
            R.id.bt_09, R.id.bt_10, R.id.bt_11, R.id.bt_12, R.id.bt_13, R.id.bt_14, R.id.behavior_main, R.id.recycler_main, R.id.recycler_drag_main})
    public void onViewClicked(View view) {
        resetIcon();
        switch (view.getId()) {
            case R.id.bt_01:
                startActivity(new Intent(this, ViewDay01Activity.class));

                break;
            case R.id.bt_02:
                startActivity(new Intent(this, ViewDay02Activity.class));

                break;
            case R.id.bt_03:
                startActivity(new Intent(this, ViewDay03Activity.class));

                break;
            case R.id.bt_04:
                startActivity(new Intent(this, ViewDay04Activity.class));

                break;
            case R.id.bt_05:
                startActivity(new Intent(this, ViewDay05Activity.class));

                break;
            case R.id.bt_06:
                startActivity(new Intent(this, ViewDay06Activity.class));

                break;
            case R.id.bt_07:
                startActivity(new Intent(this, ViewDay07Activity.class));

                break;
            case R.id.bt_09:
                startActivity(new Intent(this, ViewDay09Activity.class));

                break;
            case R.id.bt_10:
                startActivity(new Intent(this, ViewDay10Activity.class));

                break;
            case R.id.bt_11:
                startActivity(new Intent(this, ViewDay11Activity.class));

                break;
            case R.id.bt_12:
                startActivity(new Intent(this, ViewDay12Activity.class));

                break;
            case R.id.vp:
                startActivity(new Intent(this, ViewPagerActivity.class));

                break;
            case R.id.search:
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);

                break;
            case R.id.point:
                startActivity(new Intent(this, PointActivity.class));

                break;
            case R.id.qqtoolbar:
                startActivity(new Intent(this, QQToolbarActivity.class));

                break;

            case R.id.qq_view_bubble:
                mBubbleView.setBigIcon(R.drawable.bubble_big);
                mBubbleView.setSmallIcon(R.drawable.bubble_small);
                break;
            case R.id.qq_view_person:
                mPersonView.setBigIcon(R.drawable.person_big);
                mPersonView.setSmallIcon(R.drawable.person_small);
                break;
            case R.id.qq_view_star:
                mStarView.setBigIcon(R.drawable.star_big);
                mStarView.setSmallIcon(R.drawable.star_small);
                break;
            case R.id.md:
                startActivity(new Intent(this, MeterialDesignActivity.class));

                break;
            case R.id.loading:
                startActivity(new Intent(this, LoadingViewActivity.class));

                break;
            case R.id.behavior_main:
                startActivity(new Intent(this, BehaviorActivity.class));

                break;
            case R.id.recycler_main:
                startActivity(new Intent(this, RecycleViewActivity.class));

                break;
            case R.id.recycler_drag_main:
                startActivity(new Intent(this, DragItemAnimatorActivity.class));

                break;
            case R.id.bt_13:
                startActivity(new Intent(this, ViewDay13Activity.class));

                break;
            case R.id.bt_14:
                startActivity(new Intent(this, ViewDay14Activity.class));

                break;
            case R.id.bt_15:
                startActivity(new Intent(this, ViewDay15Activity.class));

                break;
            case R.id.bt_16:
                startActivity(new Intent(this, ViewDay16Activity.class));

                break; case R.id.bt_17:
                startActivity(new Intent(this, ViewDay17Activity.class));

                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
