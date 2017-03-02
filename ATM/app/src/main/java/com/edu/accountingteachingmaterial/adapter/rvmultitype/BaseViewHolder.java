package com.edu.accountingteachingmaterial.adapter.rvmultitype;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.edu.accountingteachingmaterial.adapter.RvMultiTypeAdapter;

/**
 * recycleview 的ViewHolder基类
 * Created by Administrator on 2017/3/1.
 */

public abstract class BaseViewHolder<T>extends RecyclerView.ViewHolder {
    private SparseArray<View> views;//item中的子view
    private View itemView;//本view
    public BaseViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        this.itemView = itemView;
    }

    /**
     * 绑定子view
     * @param resId
     * @return
     */
    public View getView(int resId){
        View view = views.get(resId);
        if (view == null){
            view = itemView.findViewById(resId);
            views.put(resId,view);
        }
        return view;
    }
    public abstract void setUpView(T model, int position, RvMultiTypeAdapter adapter);

}
