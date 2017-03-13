package com.edu.accountingteachingmaterial.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.AddGroupAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.GroupsListBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 添加小组界面
 * Created by Administrator on 2017/2/28.
 */

public class AddGroupActivity extends BaseActivity {
    private ListView listView;
    private AddGroupAdapter adapter;
    private TextView titleTv;
    Bitmap bitmap = null;
    Drawable drawable = null;
   Html.ImageGetter imageGetter;


    @Override
    public int setLayout() {
        return R.layout.activity_addgroup;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        listView = bindView(R.id.addgroup_lv);
        titleTv = bindView(R.id.addgroup_tv);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        List<GroupsListBean> datas = (List<GroupsListBean>) bundle.getSerializable(ClassContstant.GROUPS);
        final String title = bundle.getString(ClassContstant.TASK_TITLE);
         imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(final String source) {
//                http://d.lanrentuku.com/down/png/1702/50-restaurant/tray.png
                Log.d("AddGroupActivity", "+++++++++++++++source" + source);

                        Log.d("AddGroupActivity", "线程开始");

                        bitmap = ImageLoader.getInstance().loadImageSync(source);
                        drawable = new BitmapDrawable(bitmap);


                return drawable;
            }
        };
        titleTv.setText(Html.fromHtml(title,imageGetter,null));
        adapter = new AddGroupAdapter(this);
        adapter.setDatas(datas);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
