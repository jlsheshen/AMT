package com.edu.accountingteachingmaterial.bean;

import android.view.View;
import android.widget.LinearLayout;

import com.edu.library.data.BaseData;

/**
 * 借贷 tag实体类
 */
public class EntryEntity extends BaseData {
    //当前所在view
    private View view;
    //	当前视图
    private LinearLayout llLayout;
    //	添加模式
    private boolean add;

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public LinearLayout getLlLayout() {
        return llLayout;
    }

    public void setLlLayout(LinearLayout llLayout) {
        this.llLayout = llLayout;
    }

}
