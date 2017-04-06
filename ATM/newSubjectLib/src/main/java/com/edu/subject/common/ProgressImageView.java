package com.edu.subject.common;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.edu.library.util.ToastUtil;
import com.edu.subject.R;
import com.edu.subject.util.BitmapParseUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 可显示加载进度的ImageView
 * 
 * @author lucher
 * 
 */
public class ProgressImageView extends ImageView implements ImageLoadingListener {

	private static final String TAG = "ProgressImageView";
	private Context mContext;
	private String mUri;
	private Paint mPaint;

	// 对应的图片
	protected Bitmap mBitmap;
	// 进度图片
	protected Bitmap mProgressBitmap;

	// 进度图片旋转角度
	private int mDegress = 360;

	// 图片加载状态
	enum Status {
		// 初始化，加载中，成功，失败，错误
		INIT, LOADING, SUCCESS, FAILURE, ERROR
	}

	// 当前图片加载状态
	private Status mStatus = Status.INIT;

	public ProgressImageView(Context context) {
		this(context, null);
	}

	public ProgressImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	/**
	 * 底图是否加载完毕
	 * 
	 * @return
	 */
	public boolean isBmLoaded() {
		return mStatus == Status.SUCCESS;
	}

	/**
	 * 初始化
	 */
	protected void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);// 抗锯齿
		try {
			mProgressBitmap = BitmapParseUtil.parse(BitmapParseUtil.FILE_RES + "ic_loading", mContext, true, this);
			mUri = BitmapParseUtil.FILE_RES + "ic_loading";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawProgress(canvas);
	}

	/**
	 * 获取bitmap
	 * 
	 * @return
	 */
	public Bitmap getBitmap() {
		return mBitmap;
	}

	/**
	 * 绘制进度
	 * 
	 * @param canvas
	 */
	private void drawProgress(Canvas canvas) {
		if (mStatus == Status.LOADING || mStatus == Status.INIT) {
			mDegress -= 30;
			if (mDegress < 0) {
				mDegress = 360;
			}

			Matrix matrix = new Matrix();
			// 计算图片居中时的left和top
			int left = (getWidth() - mProgressBitmap.getWidth()) / 2;
			int top = (getHeight() - mProgressBitmap.getHeight()) / 2;
			// 计算图片以中心旋转时的x和y方向的起始值
			int offsetX = mProgressBitmap.getWidth() / 2;
			int offsetY = mProgressBitmap.getHeight() / 2;
			matrix.postTranslate(-offsetX, -offsetY);
			matrix.postRotate(mDegress);
			matrix.postTranslate(left + offsetX, top + offsetY);
			canvas.drawBitmap(mProgressBitmap, matrix, mPaint);

			postInvalidateDelayed(100);
		}
	}

	@Override
	public void onLoadingStarted(String arg0, View arg1) {
		mStatus = Status.LOADING;
		setScaleType(ScaleType.CENTER);// 显示加载图片
		invalidate();
		Log.d(TAG, "onLoadingStarted:" + arg0);
	}

	@Override
	public void onLoadingCancelled(String arg0, View arg1) {
		Log.i(TAG, "onLoadingCancelled:" + arg0);
		mStatus = Status.FAILURE;
		loadImage(mUri);
		invalidate();
	}

	@Override
	public void onLoadingFailed(String arg0, View arg1, FailReason failReason) {
		Log.e(TAG, "onLoadingFailed:" + arg0);
		String reason;
		switch (failReason.getType()) {
		case IO_ERROR:
		case DECODING_ERROR:
		case NETWORK_DENIED:
		case OUT_OF_MEMORY:
			mStatus = Status.ERROR;
			reason = "图片加载出错：" + failReason.getType();
			handleParseError(reason, arg0);
			break;
		default:
			mStatus = Status.FAILURE;
			reason = "图片加载失败，正在重新加载";
			loadImage(mUri);
			break;
		}
		invalidate();
	}

	@Override
	public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
		mStatus = Status.SUCCESS;
		BitmapParseUtil.saveBitmap(mContext, imageUri, loadedImage);
		setImageBitmap(loadedImage);
		setScaleType(ScaleType.CENTER_INSIDE);
		mBitmap = loadedImage;
		Log.i(TAG, "onLoadingComplete:" + imageUri);
	}

	/**
	 * 加载指定uri的图片
	 * 
	 * @param uri
	 */
	public void loadImage(String uri) {
		try {
			mBitmap = BitmapParseUtil.parse(uri, mContext, true, this);
			setImageBitmap(mBitmap);
			if (mBitmap == null) {
				mStatus = Status.INIT;
			} else {
				mStatus = Status.SUCCESS;
			}
			mUri = uri;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			handleParseError("图片格式不支持", uri);
			Log.e(TAG, "图片格式不支持：" + uri);
			mStatus = Status.ERROR;
		} catch (IOException e) {
			e.printStackTrace();
			handleParseError("图片解析出错", uri);
			Log.e(TAG, "图片解析出错：" + uri);
			mStatus = Status.ERROR;
		}

		Log.d("lucher", getWidth() + "," + getHeight() + "," + mUri);
	}

	/**
	 * 图片解析出错处理
	 * 
	 * @param uri
	 */
	protected void handleParseError(String reason, String uri) {
		Log.e(TAG, reason + "," + uri);
		setImageResource(R.drawable.ic_error);
		setScaleType(ScaleType.CENTER);
		ToastUtil.showToast(mContext, reason + "," + uri);
	}
}
