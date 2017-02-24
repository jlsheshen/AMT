package com.edu.accountingteachingmaterial.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */

public class EntryViewAdapter extends PagerAdapter {

    // 对应的页面
    private List<EntryView> views;

    /**
     * 构造
     *
     * @param views
     */
    public EntryViewAdapter(List<EntryView> views) {
        this.views = views;
    }

    /**
     * @return
     */
    public List<EntryView> getDatas() {
        return views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        container.removeView(views.get(position));
        container.addView(views.get(position));

        return views.get(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
