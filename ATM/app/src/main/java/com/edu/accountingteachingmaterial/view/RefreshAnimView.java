package com.edu.accountingteachingmaterial.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.edu.accountingteachingmaterial.R;

/**
 * 此类用来实现下拉刷新的动画效果
 * Created by Administrator on 2017/1/25.
 */
public class RefreshAnimView  extends View{
    /**
     * 原图
     */
    private Bitmap bitmap;

    /**
     * 画笔
     */
    private Paint paint;

    private int measureWidth;

    private int measureHeight;

    private float progress;

    private int alpha;

    private Bitmap scaleBitmap;




    public RefreshAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //初始化画笔,模拟渐变效果
        paint = new Paint();
        //开始透明度设置为0
        paint.setAlpha(0);
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

    /**
     * 拿到控件宽高,对图片进行缩放
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measureWidth = w;
        measureHeight = h;
        //根据测量后的宽高进行缩放;
        scaleBitmap = Bitmap.createScaledBitmap(bitmap,measureWidth,measureHeight,true) ;
    }

    /**
     * 重写绘方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(progress,progress,measureWidth/2,measureHeight/2);
        paint.setAlpha(alpha);
        canvas.drawBitmap(scaleBitmap,0,0,paint);
    }

    /**
     * 根据进度对图片进行缩放
     * @param progress
     */
    public void setProgress(float progress) {
        this.progress = progress;
        alpha = (int) (progress*255);
    }
}
