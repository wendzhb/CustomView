package com.kaifa.customview.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.LetterSideBar;

public class ViewDay06Activity extends BaseActivity implements LetterSideBar.LetterTouchListener {

    private LetterSideBar letterSideBar;
    private TextView textView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view_day06);
    }

    @Override
    protected void initView() {
        letterSideBar = (LetterSideBar) findViewById(R.id.letter_bar);
        textView = (TextView) findViewById(R.id.tv_letter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        letterSideBar.setOnLetterTouchListener(this);
    }

    @Override
    public void touch(CharSequence letter) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(letter);
    }

    @Override
    public void dissmis() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
            }
        }, 500);

        textView.post(new Runnable() {
            //保存到Queue中,什么都没干，会在dispatchAttachedToWindow中在测量完毕之后执行，executeActions()
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
            }
        });
    }
}
