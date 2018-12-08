package com.kaifa.customview.activity;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.refresh.QQRefreshHeader;
import com.kaifa.customview.widget.refresh.RefreshLayout;

public class ViewDay12Activity extends BaseActivity {

//    private RefreshLayout refreshLayout;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day12);

    }

    @Override
    protected void initView() {
//        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_main_fragment);
//        QQRefreshHeader header = new QQRefreshHeader(this);
//        refreshLayout.setRefreshHeader(header);
//
//        // 刷新状态的回调
//        refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 延迟1.5秒后刷新成功,重新发送请求
//                refreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.refreshComplete();
//                    }
//                }, 1500);
//            }
//        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {

    }
}
