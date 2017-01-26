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

public class RefreshView extends View {
    private Bitmap bitmap;

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec,bitmap.getWidth()),measureSize(heightMeasureSpec,bitmap.getHeight()));
    }

    /**i
     * 对宽度高度进行判断.
     *  MeasureSpec参数的值为int型，分为高32位和低16为，高32位保存的是specMode，低16位表示specSize，specMode分三种：

     1、MeasureSpec.UNSPECIFIED,父视图不对子视图施加任何限制，子视图可以得到任意想要的大小；

     2、MeasureSpec.EXACTLY，父视图希望子视图的大小是specSize中指定的大小；

     3、MeasureSpec.AT_MOST，子视图的大小最多是specSize中的大小。
     * @param widMeasureSpec
     * @return
     */
    private int measureSize(int widMeasureSpec,int bitmapSize) {
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
