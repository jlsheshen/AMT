package com.edu.accountingteachingmaterial.fragment;

import android.view.View;
import android.widget.GridView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.TextBookGvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;

/**
 * 教材fragment
 * Created by Administrator on 2017/2/27.
 */

public class TextbookFragment extends BaseFragment {
    GridView gridView;//显示教材
    TextBookGvAdapter adapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_textbook;
    }

    @Override
    protected void initView(View view) {
        gridView = bindView(R.id.textboox_gv);

    }

    @Override
    protected void initData() {
        adapter = new TextBookGvAdapter();
        gridView.setAdapter(adapter);

    }
}
