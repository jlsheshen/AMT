package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PdfActivity extends BaseActivity {
    PDFView pdfView;
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
        File file = new File("/sdcard/EduResources/AccCourse/pdf/aaa.pdf");
        pdfView.fromFile(file)
                // pdfView.fromAsset(String)
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();

    }
}
