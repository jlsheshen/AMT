package com.edu.accountingteachingmaterial.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ImageActivity;
import com.edu.accountingteachingmaterial.activity.MediaActivity;
import com.edu.accountingteachingmaterial.activity.PdfActivity;
import com.edu.accountingteachingmaterial.adapter.ExampleGVAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.entity.ClassicCase;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 经典示例
 *
 * @author xd
 */
public class ClassExampleFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    GridView gridView;
    ExampleGVAdapter exampleGVAdapter;
    List<ExampleBean> exampleBeans;
    List<ClassicCase> cData;
    ClassChapterData.SubChaptersBean data;
//    private String mUrl = NetUrlContstant.BASE_URL + "interface/filedown/down/401-1";
//    private FinalHttp fHttp = new FinalHttp();
//    private HttpHandler<File> mHandler;


    public ClassExampleFragment(ClassChapterData.SubChaptersBean data) {
        this.data = data;
    }

    public ClassExampleFragment() {
    }

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class_example;
    }

    @Override
    protected void initView(View view) {

        gridView = bindView(R.id.exmaple_gv);

        // TODO Auto-generated method stub
//        start();
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
            if (cData.get(i).getFile_type() == 1) {
                ExampleBean exampleBean = new ExampleBean();
                exampleBean.setName(cData.get(i).getTitle());
                exampleBean.setUrl(String.valueOf(cData.get(i).getFile_id()));
                exampleBean.setType(ClassContstant.MEADIA_TYPE);
                exampleBeans.add(exampleBean);
            } else if (cData.get(i).getFile_type() == 3 || cData.get(i).getFile_type() == 4 || cData.get(i).getFile_type() == 5) {
                ExampleBean exampleBean1 = new ExampleBean();
                exampleBean1.setName(cData.get(i).getTitle());
                exampleBean1.setUrl(String.valueOf(cData.get(i).getFile_id()) + "-1");
                exampleBean1.setType(ClassContstant.PDF_TYPE);
                exampleBeans.add(exampleBean1);
            }

//        ExampleBean exampleBean = new ExampleBean();
//        exampleBean.setName("第一节视频");
//        exampleBean.setUrl("aaa.mp4");
//        exampleBean.setType(ClassContstant.MEADIA_TYPE);
//        exampleBeans.add(exampleBean);
//        ExampleBean exampleBean1 = new ExampleBean();
//        exampleBean1.setName("第二节图片");
//        exampleBean1.setUrl("aaa.pdf");
//        exampleBean1.setType(ClassContstant.PDF_TYPE);
//        exampleBeans.add(exampleBean1);
        }
        exampleGVAdapter.setBeans(exampleBeans);
        exampleGVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        }
    }


    /**
     * 根据用户id请求用户数据
     */

    private void uploadInfo() {
        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this.getContext(), RequestMethod.POST, NetUrlContstant.classicCaseUrl + data.getId() + "-2");
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    cData = JSON.parseArray(jsonObject.getString("message"), ClassicCase.class);
                    loadData();
                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", errorInfo);

            }
        });
    }

//    /**
//     * 开始下载
//     */
//    public void start() {
//        String path = SdcardPathUtil.getExternalSdCardPath() + "/EduResources/AccCourse/pdf/";
//        String[] tmp = mUrl.split("/");
//        String target = path + tmp[tmp.length - 1];
//        checkPath(path);
//        // 调用download方法开始下载
//        mHandler = fHttp.download(mUrl, new AjaxParams(), target, true, new AjaxCallBack<File>() {
//
//            public void onStart() {
//            }
//
//            public void onLoading(long count, long current) {
//                Log.d("", "下载进度：" + current + "/" + count);
//            }
//
//            public void onSuccess(File f) {
//                Log.d("", f == null ? "null" : f.getAbsoluteFile().toString());
//                ToastUtil.showToast(context, "下载成功：" + f.getAbsoluteFile().toString());
//            }
//
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//
//                Log.e("", "failure:" + strMsg + ",errorNo:" + errorNo);
//                if (errorNo == 0) {
//                } else if (errorNo == 416) {
//                    ToastUtil.showToast(context, "文件已存在");
//                } else {
//                    ToastUtil.showToast(context, "下载失败：" + strMsg);
//                }
//
//            }
//        });
//    }
//
//    /**
//     * 检查该路径是否存在，不存在则创建
//     *
//     * @param path
//     */
//    public void checkPath(String path) {
//        File f = new File(path);
//        if (!f.exists()) {
//            if (!f.getParentFile().exists()) {
//                if (f.getParentFile().mkdirs()) {
//                    f.mkdirs();
//                }
//            } else {
//                f.mkdirs();
//            }
//        }
//    }
}
