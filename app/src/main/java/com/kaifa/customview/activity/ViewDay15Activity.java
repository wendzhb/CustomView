package com.kaifa.customview.activity;

import android.view.View;

import com.kaifa.customview.R;
import com.kaifa.customview.base.BaseActivity;
import com.kaifa.customview.widget.bubble.BubbleMessageTouchListener;
import com.kaifa.customview.widget.bubble.MessageBubbleView;

public class ViewDay15Activity extends BaseActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_day15);

    }

    @Override
    protected void initView() {
        MessageBubbleView.attach(findViewById(R.id.text_view), new BubbleMessageTouchListener.BubbleDisappearListener() {

            @Override
            public void dismiss(View view) {

            }
        }) ;
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
