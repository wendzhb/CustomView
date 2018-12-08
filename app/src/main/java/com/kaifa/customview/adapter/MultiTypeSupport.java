package com.kaifa.customview.adapter;

/**
 * Created by zhb on 2018/12/7.
 * Description:  多布局支持接口
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    public int getLayoutId(T item, int position);
}
