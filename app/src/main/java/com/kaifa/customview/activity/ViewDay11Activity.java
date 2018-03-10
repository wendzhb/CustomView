package com.kaifa.customview.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDay11Activity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;

    private List<String> mItems;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day11);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mItems.add("i-------->"+i);
        }
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mItems));
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
