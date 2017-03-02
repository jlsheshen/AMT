package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;

/**
 * 带刷新的ExpandableListView
 * Created by Administrator on 2017/1/25.
 */

public class RefreshExListView extends ExpandableListView implements AbsListView.OnScrollListener{
    private static final int REFRESH_DONE = 0;//下拉刷新完成
    private static final int PULL_TO_REFRESH = 1;//下拉中（下拉高度未超出headview高度）
    private static final int RELEASE_TO_REFRESH = 2;//下拉中（下拉高度超出headview高度）
    private static final int REFRESHING = 3;//刷新中
    private LinearLayout headerView;//headerView布局
    private int headerViewHeight;//headerView高度
    private int refreshState;//下拉刷新状态
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

    //监听接口
    private OnListMoveListener onListMoveListener;

    RefreshBgView refreshBgView;
    AnimationDrawable animationDrawableBg;
    /**
     * 刷新动画的实现
     */
    private RefreshAnimView refreshAnimView;
    private RefreshView refreshView;
    private AnimationDrawable animationDrawable;

    private ProgressBar refreshLoadingView;

    public RefreshExListView(Context context, AttributeSet attrs) {
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
        refreshLoadingView= (ProgressBar) headerView.findViewById(R.id.second_step_view);
        measureView(headerView);
        measureView(footerView);
        addHeaderView(headerView);
        addFooterView(footerView);
        headerViewHeight = headerView.getMeasuredHeight();
        footerViewHeight = footerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        footerView.setPadding(0, 0, 0, -footerViewHeight);
        refreshLoadingView = (ProgressBar) headerView.findViewById(R.id.second_step_view);

        //默认为加载完成,刷新完成
        refreshState = REFRESH_DONE;
        loadstate = LOAD_DONE;
        //默认可以刷新与加载
        isRefreshable = true;
        isLoadable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //手指滑动距离为当前高度减初始高度
                offsetY = ev.getY()-startY;
                //下拉刷新
                if (isRefreshable&&offsetY>0&&loadstate==LOAD_DONE&&isScrollFirst&& refreshState != REFRESHING){
                    float headViewShowHeight = offsetY/REFRESH_RATIO;//根据滑动距离,显示headView漏出高度
                    float currentProgress = headViewShowHeight / headerViewHeight;//漏出高度和实际高度的比例,在滑动时改变动图大小
                    if (currentProgress >= 1){
                        currentProgress =1;
                    }
                    //对下拉时的几个状态
                    // 当state=PULL_TO_REFRESH时，headerViewShowHeight<headerViewHeight,
                    // 当state=RELEASE_TO_REFRESH，headerViewShowHeight>headerViewHeight,
//                    Log.d("RefreshListView", "headViewShowHeight:" + headViewShowHeight + "headerViewHeight" + headerViewHeight);
                    switch (refreshState){
                        case REFRESH_DONE://刚开始下拉，则将状态置为PULL_TO_REFRESH
                            refreshState = PULL_TO_REFRESH;
                            break;
                        case PULL_TO_REFRESH:
                            //当state=PULL_TO_REFRESH时，如果headerViewShowHeight超过了headerViewHeight，那么此时已经达到可刷新状态了，
                            //意思是准备刷新中，如果此时用户松手，则执行刷新操作
                            if (headViewShowHeight -headerViewHeight >= 0){
                                refreshState = RELEASE_TO_REFRESH;
                                changeHeaderByState(refreshState);
                            }
                            break;
                        case RELEASE_TO_REFRESH:
                            setSelection(0);
                            //当state=RELEASE_TO_REFRESH时，已达到了可刷新状态，但如果用户此时未松手并又向上滑动，
                            //直到headerView缩了回去，那么此时的状态又回到PULL_TO_REFRESH
                            if (headViewShowHeight - headerViewHeight < 0) {
                                refreshState = PULL_TO_REFRESH;
                                changeHeaderByState(refreshState);
                            }
                            break;
                    }
                    //PULL_TO_REFRESH和RELEASE_TO_REFRESH都属于下拉中的状态，因此将根据手指滑动的距离动态去修改headview的paddingTop
                    if (refreshState == PULL_TO_REFRESH || refreshState == RELEASE_TO_REFRESH) {
                        headerView.setPadding(0, (int) (headViewShowHeight - headerViewHeight), 0, 0);
                    }
                    /**
                     * 上拉加载更多
                     */

                    if (isLoadable && offsetY < 0 && refreshState == REFRESH_DONE && isScrollLast && loadstate != LOADING) {
                        float footerViewShowHeight = -offsetY / LOAD_RATIO;
                        switch (loadstate) {
                            case LOAD_DONE:
                                loadstate = PULL_TO_LOAD;
                                break;
                            case PULL_TO_LOAD:
                                setSelection(totalcount);
                                if (footerViewShowHeight - footerViewHeight >= 0) {
                                    loadstate = RELEASE_TO_LOAD;
                                    changeFooterByState(loadstate);
                                }
                                break;
                            case RELEASE_TO_LOAD:
                                setSelection(totalcount);
                                if (footerViewShowHeight - footerViewHeight < 0) {
                                    loadstate = PULL_TO_LOAD;
                                    changeFooterByState(loadstate);
                                }
                                break;
                        }

                        if (loadstate == PULL_TO_LOAD || loadstate == RELEASE_TO_LOAD) {
                            footerView.setPadding(0, 0, 0, (int) (footerViewShowHeight - footerViewHeight));
                        }
                    }
                    break;
                }
            case MotionEvent.ACTION_UP:
                /**
                 * 下拉刷新
                 */
                if (isRefreshable) {//只有当启用下拉刷新时触发
                    if (refreshState == PULL_TO_REFRESH) {
                        refreshState = REFRESH_DONE;
                        changeHeaderByState(refreshState);
                    }
                    if (refreshState == RELEASE_TO_REFRESH) {
                        refreshState = REFRESHING;
                        changeHeaderByState(refreshState);
                        onListMoveListener.refreshView();
                    }
                }
                /**
                 * 上拉加载
                 */
                if (isLoadable) {//只有当启用上拉加载时触发
                    if (loadstate == PULL_TO_LOAD) {
                        loadstate = LOAD_DONE;
                        changeFooterByState(loadstate);
                    }
                    if (loadstate == RELEASE_TO_LOAD) {
                        loadstate = LOADING;
                        changeFooterByState(loadstate);
                        onListMoveListener.loadMoreView();
                    }
                }
                break;


        }
        return super.onTouchEvent(ev);
    }

    public void setOnListMoveListener(OnListMoveListener onListMoveListener) {
        this.onListMoveListener = onListMoveListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        totalcount = totalItemCount;
        if (firstVisibleItem == 0){
            //滑动到了顶部
            isScrollFirst = true;
        }else {
            isScrollFirst = false;
        }
        if ((firstVisibleItem + visibleItemCount == totalItemCount)){
            //滑动到了底部
            isScrollLast = true;
        }else {
            isScrollLast = false;
        }

    }
    /**
     * 下拉刷新完成
     */
    public void setOnRefreshComplete() {
        refreshState = REFRESH_DONE;
        changeHeaderByState(refreshState);
    }

    /**
     * 加载更多完成
     */
    public void setOnLoadMoreComplete() {
        loadstate = LOAD_DONE;
        changeFooterByState(loadstate);
    }

    /**
     * 改变headerView状态
     * @param state
     */
    private void changeHeaderByState(int state) {
        switch (refreshState){
            case REFRESH_DONE:
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                Log.d("RefreshListView", "下拉完成状态");
                refreshLoadingView.setVisibility(View.GONE);
                break;
            case RELEASE_TO_REFRESH:
                Log.d("RefreshListView", "RELEASE_TO_REFRESH");
                break;
            case RELEASE_TO_LOAD:
                Log.d("RefreshListView", "RELEASE_TO_LOAD");
                break;
            case REFRESHING:
                Log.d("RefreshListView", "REFRESHING");
                refreshLoadingView.setVisibility(View.VISIBLE);

                headerView.setPadding(0,0,0,0);
                break;
            default:

                break;
        }
    }
    /**
     * 改变footerview状态
     *
     * @param loadstate
     */
    private void changeFooterByState(int loadstate) {
        switch (loadstate) {
            case LOAD_DONE:
                footerView.setPadding(0, 0, 0, -footerViewHeight);
                tv_load.setText("上拉加载更多");
                break;
            case RELEASE_TO_LOAD:
                tv_load.setText("松开加载更多");
                break;
            case PULL_TO_LOAD:
                tv_load.setText("上拉加载更多");
                break;
            case LOADING:
                tv_load.setText("正在加载...");
                footerView.setPadding(0, 0, 0, 0);
                break;
            default:
                break;
        }
    }
        /**
         * 计算控件宽高
         * @param childView
         */
    private void measureView(View childView){
        //获取子视图宽高
        ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
        if (layoutParams == null){
            //如果没有,则初始化
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int chileWidthSpec =getChildMeasureSpec(0, 0 ,layoutParams.width);
        int childHeightSpec ;
        if (layoutParams.height >0){
           childHeightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height,MeasureSpec.EXACTLY);
        }else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        childView.measure(chileWidthSpec,childHeightSpec);
    }

    public interface OnListMoveListener {
        void refreshView();
        void loadMoreView();

    }
}
