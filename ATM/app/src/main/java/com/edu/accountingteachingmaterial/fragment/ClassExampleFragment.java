package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
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
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.library.util.SdcardPathUtil;
import com.edu.library.util.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

import java.io.File;
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
    private String mUrl = NetUrlContstant.BASE_URL + "interface/filedown/down/401-1";
    private FinalHttp fHttp = new FinalHttp();
    private HttpHandler<File> mHandler;

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class_example;
    }

    @Override
    protected void initView(View view) {

        gridView = bindView(R.id.exmaple_gv);

        // TODO Auto-generated method stub
        start();
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
        exampleBean1.setType(ClassContstant.PDF_TYPE);

        exampleBeans.add(exampleBean1);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (exampleBeans.get(i).getType()) {
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

    /**
     * 开始下载
     */
    public void start() {
        String path = SdcardPathUtil.getExternalSdCardPath() + "/EduResources/AccCourse/pdf/";
        String[] tmp = mUrl.split("/");
        String target = path + tmp[tmp.length - 1];
        checkPath(path);
        // 调用download方法开始下载
        mHandler = fHttp.download(mUrl, new AjaxParams(), target, true, new AjaxCallBack<File>() {

            public void onStart() {
            }

            public void onLoading(long count, long current) {
                Log.d("", "下载进度：" + current + "/" + count);
            }

            public void onSuccess(File f) {
                Log.d("", f == null ? "null" : f.getAbsoluteFile().toString());
                ToastUtil.showToast(context, "下载成功：" + f.getAbsoluteFile().toString());
            }

            public void onFailure(Throwable t, int errorNo, String strMsg) {

                Log.e("", "failure:" + strMsg + ",errorNo:" + errorNo);
                if (errorNo == 0) {
                } else if (errorNo == 416) {
                    ToastUtil.showToast(context, "文件已存在");
                } else {
                    ToastUtil.showToast(context, "下载失败：" + strMsg);
                }

            }
        });
    }

    /**
     * 检查该路径是否存在，不存在则创建
     *
     * @param path
     */
    public void checkPath(String path) {
        File f = new File(path);
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                if (f.getParentFile().mkdirs()) {
                    f.mkdirs();
                }
            } else {
                f.mkdirs();
            }
        }
    }
}
