package com.edu.accountingteachingmaterial.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ImageActivity;
import com.edu.accountingteachingmaterial.activity.MediaActivity;
import com.edu.accountingteachingmaterial.activity.PdfActivity;
import com.edu.accountingteachingmaterial.adapter.ExampleGVAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 经典示例
 *
 * @author xd
 *
 */
public class ClassExampleFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    GridView gridView;
    ExampleGVAdapter exampleGVAdapter;
    List<ExampleBean> exampleBeans;

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class_example;
    }

    @Override
    protected void initView(View view) {

        gridView = bindView(R.id.exmaple_gv);

        // TODO Auto-generated method stub

    }

    @Override
    protected void initData() {

        loadData();
        exampleGVAdapter = new ExampleGVAdapter(context);
        exampleGVAdapter.setBeans(exampleBeans);
        gridView.setAdapter(exampleGVAdapter);
        gridView.setOnItemClickListener(this);

    }

    private void loadData() {
        exampleBeans = new ArrayList<>();
        ExampleBean exampleBean = new ExampleBean();
        exampleBean.setName("第一节视频");
        exampleBean.setUrl("aaa.mp4");
        exampleBean.setType(ClassContstant.MEADIA_TYPE);
        exampleBeans.add(exampleBean);
        ExampleBean exampleBean1 = new ExampleBean();
        exampleBean1.setName("第二节图片");
        exampleBean1.setUrl("aaa.pdf");
        exampleBean1.setType( ClassContstant.PDF_TYPE);

        exampleBeans.add(exampleBean1);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (exampleBeans.get(i).getType()){
            case ClassContstant.MEADIA_TYPE:
                startActivity(MediaActivity.class);
                break;
            case ClassContstant.PDF_TYPE:
                startActivity(PdfActivity.class);
                break;
            case ClassContstant.DOC_TYPE:
                startActivity(ImageActivity.class);
                break;


        }


    }
}
