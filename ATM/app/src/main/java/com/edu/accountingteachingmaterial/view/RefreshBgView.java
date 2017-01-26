package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2017/1/25.
 */
public class RefreshBgView  extends View{
    private Bitmap bitmap;
    public RefreshBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec,bitmap.getWidth()),measureSize(heightMeasureSpec,bitmap.getHeight()));

    }

    private int measureSize(int widMeasureSpec, int bitmapSize) {
        int request = 0;
        int size = MeasureSpec.getSize(widMeasureSpec);
        int mode = MeasureSpec.getMode(widMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            request = size;
        } else {
            request = bitmapSize;
            if (mode == MeasureSpec.AT_MOST) {
                request = Math.min(size, request);
            }
        }
        return request;
    }
}
