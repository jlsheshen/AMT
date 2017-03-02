package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Administrator on 2017/3/1.
 */

public class NoRvScrollManager extends GridLayoutManager {
    public NoRvScrollManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
