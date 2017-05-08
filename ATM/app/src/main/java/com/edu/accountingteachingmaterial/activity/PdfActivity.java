package com.edu.accountingteachingmaterial.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.view.dialog.SelectPictureDialog;
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
    String target;
    SelectPictureDialog pictureDialog;//上传图片
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 37422;



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
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck ==  PackageManager.PERMISSION_GRANTED){
            start();
        }else {
            Log.d("MainActivity", "没有权限");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    private void show() {
//        String url = "/sdcard/EduResources/AccCourse/pdf/" + exampleBeans.getUrl();
//        String url = UriConstant.PDF_PATH + exampleBeans.getUrl();
//        String[] tmp = exampleBeans.getUrl().split("/");
//        String url = UriConstant.PDF_PATH  + tmp[tmp.length - 1];
        Log.d("PdfActivity1", target);
        File file = new File(target);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS){
            start();
        }


    }

    /**
     * 开始下载
     */
    public void start() {
        String path = SdcardPathUtil.getExternalSdCardPath() + "/EduResources/AccCourse/pdf/";
        checkPath(path);
        fileIsExists(path);
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
    /**
     * 检查文件是否存在，存在直接读取，不进行下载
     */
    public void fileIsExists(String path) {
        String downUrl = mUrl + exampleBeans.getUrl() + "-1";
        String[] tmp = downUrl.split("/");
        target = path + tmp[tmp.length - 1];
        Log.d("PdfActivity2", downUrl);
        Log.d("PdfActivity3", target);
        File f = new File(target);
        if (!f.exists()) {
            down();
        } else {
            show();
        }
    }

    /**
     * 开始下载
     */
    public void down() {
        String downUrl = mUrl + exampleBeans.getUrl() + "-1";
        String path = SdcardPathUtil.getExternalSdCardPath() + "/EduResources/AccCourse/pdf/";
        String[] tmp = downUrl.split("/");
        target = path + tmp[tmp.length - 1];
        Log.d("PdfActivity2", downUrl);
        Log.d("PdfActivity3", target);

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
}