package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.constant.UriConstant;
import com.edu.library.util.SdcardPathUtil;
import com.edu.library.util.ToastUtil;
import com.github.barteksc.pdfviewer.PDFView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

import java.io.File;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PdfActivity extends BaseActivity {
    PDFView pdfView;
    ExampleBean exampleBeans;
    private FinalHttp fHttp = new FinalHttp();
    private HttpHandler<File> mHandler;

    private String mUrl = NetUrlContstant.getMediaorPdfUrl();

    @Override
    public int setLayout() {
        return R.layout.activity_pdfview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        pdfView = bindView(R.id.pdfView);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        exampleBeans = (ExampleBean) bundle.getSerializable("exampleBeans");
        Log.d("PdfActivity", "exampleBeans:" + exampleBeans);

        start();
    }

    private void show() {
//        String url = "/sdcard/EduResources/AccCourse/pdf/" + exampleBeans.getUrl();
        String url = UriConstant.PDF_PATH + exampleBeans.getUrl();
        Log.d("PdfActivity", url);
        File file = new File(url);
        pdfView.fromFile(file)
                // pdfView.fromAsset(String)
                //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();
    }

    /**
     * 开始下载
     */
    public void start() {
        String downUrl = mUrl + exampleBeans.getUrl();
        String path = SdcardPathUtil.getExternalSdCardPath() + "/EduResources/AccCourse/pdf/";
        //String path = UriConstant.PDF_PATH;
        String[] tmp = downUrl.split("/");
        String target = path + tmp[tmp.length - 1];
        checkPath(path);
        Log.d("PdfActivity", downUrl);

        // 调用download方法开始下载
        mHandler = fHttp.download(downUrl, new AjaxParams(), target, true, new AjaxCallBack<File>() {

            public void onStart() {
            }

            public void onLoading(long count, long current) {
                Log.d("", "下载进度：" + current + "/" + count);
            }

            public void onSuccess(File f) {
                Log.d("", f == null ? "null" : f.getAbsoluteFile().toString());
                show();
                ToastUtil.showToast(PdfActivity.this, "下载成功");
            }

            public void onFailure(Throwable t, int errorNo, String strMsg) {

                Log.e("", "failure:" + strMsg + ",errorNo:" + errorNo);
                if (errorNo == 0) {
                } else if (errorNo == 416) {
                    ToastUtil.showToast(PdfActivity.this, "文件已存在");
                } else {
                    ToastUtil.showToast(PdfActivity.this, "下载失败：" + strMsg);
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