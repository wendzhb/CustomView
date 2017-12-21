package com.kaifa.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;

public class ViewDay08Activity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day08);

    }

    @Override
    protected void initView() {
        View view = findViewById(R.id.touch);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("tag", "VIEW OnTouchListener" + event.getAction());
                return false;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "VIEW ONCLICK");
            }
        });
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
