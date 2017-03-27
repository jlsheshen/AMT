package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassCatalogActivity;
import com.edu.accountingteachingmaterial.adapter.TextBookGvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.TextbookBean;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.TextBookListManager;

import java.util.List;

/**
 * 教材fragment
 * Created by Administrator on 2017/2/27.
 */

public class TextbookFragment extends BaseFragment implements AdapterView.OnItemClickListener, TextBookListManager.TextbookListener {
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
        TextBookListManager.getSingleton(getContext()).getTextBookList(this);
        adapter = new TextBookGvAdapter();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(PreferenceHelper.CLASS_ID, "" + adapter.getData(position).getId());
        PreferenceHelper.getInstance(getContext()).setBooleanValue(PreferenceHelper.IS_TEXKBOOK,true);
        PreferenceHelper.getInstance(getContext()).setStringValue(PreferenceHelper.COURSE_ID,"" + adapter.getData(position).getId());
        startActivity(ClassCatalogActivity.class,bundle);
    }

    @Override
    public void onSuccess(List<TextbookBean> books) {
        adapter.setData(books);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onFailure(String message) {
//        /resources/upload/cover/2017-3-23/1490237816221.png
//        \\\\/resources\\\\/upload\\\\/cover\\\\/temp\\\\/1490335417078.png\
    }
}
