package com.kaifa.customview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import com.kaifa.customview.R
import kotlinx.android.synthetic.main.activity_point.*

class PointActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        bt.setOnClickListener(View.OnClickListener {
            point.StartAnimation()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        point.stopAnimation()
    }
}
