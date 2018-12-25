package com.kaifa.customview.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import com.kaifa.customview.widget.MenuObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhb on 2018/12/14.
 * 筛选菜单的adapter
 */
public abstract class BaseMenuAdapter {

    private List<MenuObserver> observers = new ArrayList<>();

    public void registerDataSetObserver(MenuObserver observer) {
        observers.add(observer);
    }

    public void unregisterDataSetObserver(MenuObserver observer) {
        observers.remove(observer);
    }

    public void closeMenu(){
        if (observers!=null){
            for (MenuObserver observer : observers) {
                observer.closeMenu();
            }
        }
    }
    //获取总共有多少条
    public abstract int getCount();

    //获取当前的tabview
    public abstract View getTabView(int position, ViewGroup parent);

    //获取当前的菜单内容
    public abstract View getMenuView(int position, ViewGroup parent);

    /**
     * 菜单打开
     *
     * @param tabView
     */
    public abstract void menuOpen(View tabView);

    /**
     * 菜单关闭
     *
     * @param childAt
     */
    public abstract void closeMenu(View childAt);


}
