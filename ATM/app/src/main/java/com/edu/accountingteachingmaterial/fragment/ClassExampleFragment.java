package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ImageActivity;
import com.edu.accountingteachingmaterial.activity.MediaActivity;
import com.edu.accountingteachingmaterial.activity.PdfActivity;
import com.edu.accountingteachingmaterial.activity.WebActivity;
import com.edu.accountingteachingmaterial.adapter.ExampleGVAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.entity.ClassicCase;
import com.edu.accountingteachingmaterial.entity.StudyHistoryVO;
import com.edu.accountingteachingmaterial.util.HistoryClickManager;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 经典示例
 *
 * @author jilin
 */
public class ClassExampleFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    GridView gridView;
    ExampleGVAdapter exampleGVAdapter;
    List<ExampleBean> exampleBeans;
    List<ClassicCase> cData;
    ClassChapterData.SubChaptersBean data;
    TextView nothingTv;



    public void setData(ClassChapterData.SubChaptersBean data) {
        this.data = data;
    }
    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class_example;
    }

    @Override
    protected void initView(View view) {
        gridView = bindView(R.id.exmaple_gv);
        nothingTv = bindView(R.id.no_content_tv);
        // TODO Auto-generated method stub
        // start();
    }

    @Override
    protected void initData() {

        uploadInfo();
        exampleGVAdapter = new ExampleGVAdapter(context);
        gridView.setAdapter(exampleGVAdapter);
        gridView.setOnItemClickListener(this);

    }

    private void loadData() {
        exampleBeans = new ArrayList<>();
        for (int i = 0; i < cData.size(); i++) {
            if (cData.get(i).getFile_type() == 1) {//meadia
                ExampleBean exampleBean = new ExampleBean();
                exampleBean.setName(cData.get(i).getTitle());
                exampleBean.setUrl(String.valueOf(cData.get(i).getUri()));
                exampleBean.setType(ClassContstant.MEADIA_TYPE);
                exampleBeans.add(exampleBean);
            } else if (cData.get(i).getFile_type() == 3 || cData.get(i).getFile_type() == 4) {//pdf
                ExampleBean exampleBean1 = new ExampleBean();
                exampleBean1.setName(cData.get(i).getTitle());
                exampleBean1.setUrl(String.valueOf(cData.get(i).getUri()));
                exampleBean1.setType(ClassContstant.PDF_TYPE);
                exampleBeans.add(exampleBean1);
            }else if(cData.get(i).getFile_type() == 5){//html
                ExampleBean exampleBean1 = new ExampleBean();
                exampleBean1.setName(cData.get(i).getTitle());
                exampleBean1.setUrl(String.valueOf(cData.get(i).getUri()));
                exampleBean1.setType(ClassContstant.HTML_TYPE);
                exampleBeans.add(exampleBean1);

            }

        }
        exampleGVAdapter.setBeans(exampleBeans);
        exampleGVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        List<StudyHistoryVO> historyVOs = new ArrayList<>();
        historyVOs.add(cData.get(i).getUpLoadingData(context));
        HistoryClickManager.getHisInstance(context).setStudyHistoryVOList(historyVOs).sendHistory();

        switch (exampleBeans.get(i).getType()) {
            case ClassContstant.MEADIA_TYPE:
                Bundle bundle = new Bundle();
                bundle.putSerializable("exampleBeans", exampleBeans.get(i));
                startActivity(MediaActivity.class, bundle);
//                startActivity(MediaActivity.class);
                break;
            case ClassContstant.PDF_TYPE:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("exampleBeans", exampleBeans.get(i));
                startActivity(PdfActivity.class, bundle1);
//                startActivity(PdfActivity.class);
                break;
            case ClassContstant.DOC_TYPE:
                startActivity(ImageActivity.class);
                break;
            case ClassContstant.HTML_TYPE:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("exampleBeans", exampleBeans.get(i));
                startActivity(WebActivity.class, bundle2);
        }
    }


    /**
     * 根据用户id请求用户数据
     */

    private void uploadInfo() {
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        Log.d("ClassExampleFragment", NetUrlContstant.getClassicCaseUrl() + data.getId() + "-2");
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this.getContext(), RequestMethod.POST, NetUrlContstant.getClassicCaseUrl() + data.getId() + "-2");
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    cData = JSON.parseArray(jsonObject.getString("message"), ClassicCase.class);
                    loadData();

                }else {
                    nothingTv.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", errorInfo);
                nothingTv.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
            }
        });
    }

}
