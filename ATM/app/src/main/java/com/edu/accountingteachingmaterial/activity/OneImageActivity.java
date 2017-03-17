package com.edu.accountingteachingmaterial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.library.imageloader.EduImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2017/3/17.
 */

public class OneImageActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_image);
        imageView = (ImageView) findViewById(R.id.one_image);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(ClassContstant.DATA);
        ImageLoader.getInstance().displayImage(url,imageView, EduImageLoader.getInstance().getDefaultBuilder().build());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
