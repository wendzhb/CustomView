package com.kaifa.customview.widget;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by zhb on 2018/12/22.
 * 自定义路径属性动画
 */
public class LoveTypeEvaluator implements TypeEvaluator<PointF> {

    private PointF point1, point2;

    public LoveTypeEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * B(t) = P0(1-t)^3 + 3P1t(1-t)^2 + 3P2t^2(1-t) + P3t^3
     *
     * @param t  t 0-1
     * @param p0 p0
     * @param p3 p3
     * @return
     */
    @Override
    public PointF evaluate(float t, PointF p0, PointF p3) {
        PointF pointF = new PointF();
        pointF.x = p0.x * (1 - t) * (1 - t) * (1 - t)
                + 3 * point1.x * t * (1 - t) * (1 - t)
                + 3 * point2.x * t * t * (1 - t)
                + p3.x * t * t * t;
        pointF.y = p0.y * (1 - t) * (1 - t) * (1 - t)
                + 3 * point1.y * t * (1 - t) * (1 - t)
                + 3 * point2.y * t * t * (1 - t)
                + p3.y * t * t * t;
        return pointF;

    }
}
