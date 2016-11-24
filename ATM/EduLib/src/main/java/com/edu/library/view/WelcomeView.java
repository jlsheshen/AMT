package com.edu.library.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.edu.library.EduConstant;

/**
 * 欢迎界面
 * 
 * @author lucher
 */
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback {// 实现生命周期回调接口

	private static final String TAG = "WelcomeView";

	private WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
	// 画笔
	private Paint paint;
	// 当前的不透明值
	private int currentAlpha = 0;
	// 屏幕宽度
	private int screenWidth = wm.getDefaultDisplay().getWidth();
	// 屏幕高度
	private int screenHeight = wm.getDefaultDisplay().getHeight();

	// 动画的时延ms
	private int sleepSpan = 50;
	// logo图片数组
	private Bitmap[] logos = new Bitmap[1];
	// 当前logo图片引用
	private Bitmap currentLogo;
	// 图片位置
	private int currentX;
	private int currentY;

	private Handler mHandler;

	/**
	 * @param activity
	 * @param res 显示图片资源id
	 * @param handler 处理跳转消息的handler
	 */
	public WelcomeView(Activity activity, int res, Handler handler) {
		super(activity);
		this.getHolder().addCallback(this); // 设置生命周期回调接口的实现者
		paint = new Paint(); // 创建画笔
		paint.setAntiAlias(true); // 打开抗锯齿
		// 加载图片
		logos[0] = BitmapFactory.decodeResource(activity.getResources(), res);
		Log.e(TAG, logos[0].getWidth() + "*" + logos[0].getHeight());

		mHandler = handler;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// 绘制黑填充矩形清背景
		paint.setColor(Color.BLACK);// 设置画笔颜色
		paint.setAlpha(255);// 设置不透明度为255
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
		// 进行平面贴图
		if (currentLogo == null)
			return;
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) // 创建时被调用
	{
		new Thread() {
			@SuppressLint("WrongCall")
			public void run() {
				for (Bitmap bm : logos) {
					currentLogo = bm;
					Log.e(TAG, currentLogo.getWidth() + "***" + currentLogo.getHeight());

//					currentX = screenWidth / 2 - bm.getWidth() / 2;// 图片位置
//					currentY = screenHeight / 2 - bm.getHeight() / 2;
					
					currentLogo = Bitmap.createScaledBitmap(bm, screenWidth, screenHeight, true);
					
					for (int i = 255; i > -10; i = i - 20) {// 动态更改图片的透明度值并不断重绘
						currentAlpha = i;
						if (currentAlpha < 0)// 如果当前不透明度小于零
						{
							currentAlpha = 0;// 将不透明度置为零
						}
						SurfaceHolder myholder = WelcomeView.this.getHolder();// 获取回调接口
						Canvas canvas = myholder.lockCanvas();// 获取画布
						try {
							synchronized (myholder)// 同步
							{
								onDraw(canvas);// 进行绘制绘制
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (canvas != null)// 如果当前画布不为空
							{
								myholder.unlockCanvasAndPost(canvas);// 解锁画布
							}
						}
						try {
							if (i == 255)// 若是新图片，多等待一会
							{
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						} catch (Exception e)// 抛出异常
						{
							e.printStackTrace();
						}
					}
				}
				mHandler.sendEmptyMessage(EduConstant.MSG_GOTO_MAIN_VIEW);
				// // 发送消息进入主界面
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {// 销毁时被调用
	}
}