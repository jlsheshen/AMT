package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2017/1/25.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener{
    private static final int REFRESH_DONE = 0;//下拉刷新完成
    private static final int PULL_TO_REFRESH = 1;//下拉中（下拉高度未超出headview高度）
    private static final int RELEASE_TO_REFRESH = 2;//下拉中（下拉高度超出headview高度）
    private static final int REFRESHING = 3;//刷新中
    private LinearLayout headerView;//headerView布局
    private int headerViewHeight;//headerView高度
    private int refreshstate;//下拉刷新状态
    private boolean isScrollFirst;//是否滑动到顶部
    private boolean isRefreshable;//是否启用下拉刷新
    private float REFRESH_RATIO = 2.0f;//下拉系数


    private static final int LOAD_DONE = 4;//上拉加载完成
    private static final int PULL_TO_LOAD = 5;//上拉中（上拉高度未超出footerview高度）
    private static final int RELEASE_TO_LOAD = 6;//上拉中（上拉高度超出footerview高度）
    private static final int LOADING = 7;//加载中
    private static final float LOAD_RATIO = 3;//上拉系数
    private LinearLayout footerView;//footerView布局
    private int footerViewHeight;//footerView高度
    private int loadstate;//上拉加载状态
    private boolean isScrollLast;//是否滑动到底部
    private boolean isLoadable;//是否启用上拉加载
    private int totalcount;//item总数量
    private TextView tv_load;//footview布局中显示的文字

    private float startY,//手指落点
            offsetY;//手指滑动的距离

    RefreshBgView refreshBgView;
    AnimationDrawable animationDrawableBg;
    /**
     * 刷新动画的实现
     */
    private RefreshAnimView refreshAnimView;
    private RefreshView refreshView;
    private AnimationDrawable animationDrawable;

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化View
     */
    private void initView(Context context) {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setOnScrollListener(this);
        headerView  = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_listview_head,null);
        footerView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_listview_foot,null);



    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    interface onMoveListener{
        void refreshView();
        void loadMoreView();

    }
}
