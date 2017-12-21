package com.kaifa.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.TagLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewDay07Activity extends BaseActivity {

    private TagLayout tagLayout;
    private List<String> mDatas;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day07);
    }

    @Override
    protected void initView() {
        tagLayout = (TagLayout) findViewById(R.id.taglayout);
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("TAT  `~` -11- " + (int) (Math.random() * 1000) % 35);
        }
    }

    @Override
    protected void setData() {
//        tagLayout.addTags(mDatas);

        tagLayout.setAdapter(new TagLayout.TagAdapter() {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView view = (TextView) LayoutInflater.from(ViewDay07Activity.this).inflate(R.layout.item_tag, parent, false);
                view.setText(mDatas.get(position));
                return view;
            }
        });
    }

    @Override
    protected void setListener() {

    }
}
