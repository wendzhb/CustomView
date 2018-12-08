package com.kaifa.customview.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kaifa.customview.R;
import com.kaifa.customview.ViewHolder;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.recycleview.WrapRecyclerAdapter;
import com.kaifa.customview.recycleview.WrapRecyclerView;
import com.kaifa.customview.recycleview.decoration.GridLayoutItemDecoration;
import com.kaifa.customview.recycleview.refresh.DefaultLoadCreator;
import com.kaifa.customview.recycleview.refresh.DefaultRefreshCreator;
import com.kaifa.customview.recycleview.refresh.LoadRefreshRecyclerView;
import com.kaifa.customview.recycleview.refresh.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends BaseActivity implements RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {

    private LoadRefreshRecyclerView recyclerView;
    private List<Integer> mList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_recycle_view);
    }

    @Override
    protected void initView() {
        recyclerView = (LoadRefreshRecyclerView) findViewById(R.id.recycler);
    }

    @Override
    protected void initData() {

        mList = new ArrayList<>();

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.item_tag, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        };
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new RecycleItemDecoration());
//        recyclerView.addItemDecoration(new GridLayoutItemDecoration(this,R.drawable.item_divider));

//        recyclerView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_tag,null,false));
//        recyclerView.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_tag,null,false));

        // 添加头部和底部刷新效果
        recyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        recyclerView.addLoadViewCreator(new DefaultLoadCreator());
        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);

    }

    @Override
    protected void setData() {
        for (int i = 0; i < 20; i++) {
            mList.add(i);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //填充选项菜单（读取XML文件、解析、加载到Menu组件上）
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.line:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.grid:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.onStopRefresh();
            }
        }, 2000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setData();
                recyclerView.onStopLoad();
            }
        }, 2000);
    }

    /**
     * 添加分割线 10px的红色
     */
    private class RecycleItemDecoration extends RecyclerView.ItemDecoration {

        private Paint mPaint;

        public RecycleItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
        }

        /**
         * 绘制分割线
         *
         * @param c
         * @param parent
         * @param state
         */
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            //利用canvas绘制，想绘制什么绘制什么
            //在每一个item的头部
            int childCount = parent.getChildCount();
            //指定绘制区域
            Rect rect = new Rect();
            rect.left = parent.getPaddingLeft();
            rect.right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 1; i < childCount; i++) {
                rect.bottom = parent.getChildAt(i).getTop();
                rect.top = rect.bottom - 10;
                c.drawRect(rect, mPaint);
            }
        }

        /**
         * 留出分割线位置
         *
         * @param outRect
         * @param view
         * @param parent
         * @param state
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //代表在每个底部的位置留出10px来绘制分割线，最后一个位置不需要分割线
            int postion = parent.getChildAdapterPosition(view);
            //parent.getChildCount()是不断变化的，现在没有办法保证最后一条
            //保证第一条
            if (postion != 0) {
                outRect.top = 10;
            }
        }
    }
}
