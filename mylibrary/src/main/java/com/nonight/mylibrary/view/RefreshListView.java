package com.nonight.mylibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.nonight.mylibrary.R;

/**
 * Created by 祈愿星痕 on 2017/5/3.
 */

public class RefreshListView extends SwipeRefreshLayout {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    public RefreshListView(Context context) {
        super(context);
        initview(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public void initview(Context context){
        LayoutInflater.from(context).inflate(R.layout.myrefreshlistview, this, true);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl);
        listView= (ListView) findViewById(R.id.lv);

    }
    public void setOnRefresh(OnRefreshListener onRefreshListener){
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }
    public void setAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);
    }
    public void setDivider(Drawable drawable){
        listView.setDivider(drawable);
    }

}
