package com.kaifa.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kaifa.customview.R;
import com.kaifa.customview.adapter.ListScreenMenuAdapter;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.ListDataScreenView;

public class ViewDay13Activity extends BaseActivity {

    private ListDataScreenView listDataScreenView;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day13);

    }

    @Override
    protected void initView() {
        listDataScreenView = (ListDataScreenView) findViewById(R.id.list_data_screen_view);
        listDataScreenView.setAdapter(new ListScreenMenuAdapter(this));
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
