package com.kaifa.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaifa.customview.R;

/**
 * Created by zhb on 2018/12/14.
 */
public class ListScreenMenuAdapter extends BaseMenuAdapter {

    private Context mContext;

    public ListScreenMenuAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private String[] mItems = {"类型","品牌","价格","更多"};
    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView tabView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.ui_list_data_screen_tab, parent, false);
        tabView.setText(mItems[position]);
        return tabView;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView menuView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.ui_list_data_screen_menu,parent,false);
        menuView.setText(mItems[position]);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        return menuView;
    }

    @Override
    public void menuOpen(View tabView) {

    }

    @Override
    public void closeMenu(View childAt) {

    }
}
