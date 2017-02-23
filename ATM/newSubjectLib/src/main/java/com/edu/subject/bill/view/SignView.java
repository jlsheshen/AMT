package com.edu.subject.bill.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;

import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.element.info.SignInfo;
import com.edu.subject.bill.scale.IScaleable;
import com.edu.subject.bill.scale.ScaleUtil;
import com.edu.subject.common.ProgressImageView;

/**
 * 印章视图,支持缩放
 * 
 * @author lucher
 * 
 */
public class SignView extends ProgressImageView implements IScaleable {

	private static final String TAG = "SignView";
	// 印章对比允许的误差范围，xy周围35像素均可
	public static final int SIGN_XY_RANGE = 35;
	// 对应印章的信息
	private SignInfo mData;

	private DragListener mListener;

	// 缩放比例
	private float mScale;
	// 当前缩放倍数
	private int mCurrentScaleTimes = 0;

	// 当前测试模式
	private int mTestMode;
	// 答题状态
	private int mState;

	public SignView(Context context, int testMode, int state) {
		super(context);
		mTestMode = testMode;
		mState = state;
	}

	@Override
	protected void init() {
		setScaleType(ScaleType.FIT_XY);
	}

	/**
	 * 设置印章对应数据，并把数据应用到该印章上
	 * 
	 * @param data
	 *            数据
	 * @param scale
	 *            缩放比例
	 * @return 是否成功
	 */
	public boolean apply(SignInfo data, float scale) {
		mData = data;
		postScale(scale, 0);
		if (mTestMode == TestMode.MODE_SHOW_DETAILS) {// 查看详情
			judgeAnswer();
			showUAnswer(true);
		} else if (mTestMode == TestMode.MODE_PRACTICE) {// 练习
			// 状态初始化
			if (mState == SubjectState.STATE_CORRECT || mState == SubjectState.STATE_WRONG) {
				judgeAnswer();
				showUAnswer(true);
			} else {
				showUAnswer(true);
			}
		} else if (mTestMode == TestMode.MODE_EXAM) {// 测试模式
			showUAnswer(true);
		} else if (mTestMode == TestMode.MODE_SHOW_UANSWER) {//查看用户答案模式
			showUAnswer(true);
		}

		return isBmLoaded();
	}

	/**
	 * 设置为盖章时可拖拽的印章
	 * 
	 * @param bill
	 *            单据对象
	 * @param bitmap
	 *            对应的图片
	 * @param width
	 *            单据宽度
	 * @param height
	 *            单据高度
	 * @param scale
	 *            当前缩放比例
	 * @param top
	 *            当前单据上边缘被遮挡的距离
	 */
	public void setAsDragView(ZoomableBillView bill, SignInfo info, int width, int height, float scale, int top) {
		setOnDragListener(bill);
		setWaitingState();
		setOnTouchListener(bill);
		loadBitmap(info.getBitmap());
		if (mBitmap != null) {
			// 居中显示
			float x = (width - mBitmap.getWidth()) / 2 / scale;
			float y = (height - mBitmap.getHeight()) / 2 / scale + top / scale;
			info.setX(x);
			info.setY(y);
			info.setWidth(mBitmap.getWidth());
			info.setHeight(mBitmap.getHeight());
			info.setUser(true);
		}
	}

	/**
	 * 获取对应空数据
	 * 
	 * @return
	 */
	public SignInfo getData() {
		return mData;
	}

	@Override
	public void postScale(float scale, int scaleTimes) {
		mScale = scale;
		mCurrentScaleTimes = scaleTimes;
		// 布局参数缩放
		int scaledX = ScaleUtil.getScaledValue(mData.getX(), scale);
		int scaledY = ScaleUtil.getScaledValue(mData.getY(), scale);
		int scaledWidth = ScaleUtil.getScaledValue(mData.getWidth(), scale);
		int scaledHeight = ScaleUtil.getScaledValue(mData.getHeight(), scale);
		setLayoutParams(new ElementLayoutParams(mData.getType(), scaledX, scaledY, scaledWidth, scaledHeight));
	}

	/**
	 * 印章拖动
	 * 
	 * @param dx
	 *            x方向的距离
	 * @param dy
	 *            y方向的距离
	 * @param scale
	 *            缩放比例
	 */
	public void move(float dx, float dy, float scale) {
		ElementLayoutParams params = (ElementLayoutParams) getLayoutParams();
		params.setX(Math.round(params.getX() - dx));
		params.setY(Math.round(params.getY() - dy));

		mData.setX(mData.getX() - dx / scale);
		mData.setY(mData.getY() - dy / scale);
		Log.e(TAG, "x:" + mData.getX() + ",y:" + mData.getY());
		Log.d(TAG, "x:" + params.getX() + ",y:" + params.getY());

		requestLayout();
	}

	/**
	 * 设置为等待拖拽状态
	 */
	public void setWaitingState() {
		// 动画方式显示
		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(this, View.SCALE_X, 5f, 1f)).with(ObjectAnimator.ofFloat(this, View.SCALE_Y, 5f, 1f)).with(ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1f));
		set.setInterpolator(new BounceInterpolator());
		set.setDuration(300).start();
	}

	/**
	 * 开始拖拽，主要处理拖拽时印章的变化
	 */
	public void startDrag() {
		// 动画方式拖拽
		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(this, View.SCALE_X, 0.9f, 1.1f, 1.0f)).with(ObjectAnimator.ofFloat(this, View.SCALE_Y, 0.9f, 1.1f, 1.0f))
				.with(ObjectAnimator.ofFloat(this, View.ALPHA, 1, 0.5f));
		set.setInterpolator(new AnticipateInterpolator());
		set.setDuration(300).start();
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (mListener != null) {
					mListener.onDragStart();
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
	}

	/**
	 * 结束拖拽，主要是让印章恢复初始的状态
	 */
	public void endDrag() {
		setAlpha(0xff);
	}

	/**
	 * 动画的方式显示，模拟盖章动画
	 * 
	 * @param billView
	 *            单据视图，盖章的时候配合发生改变
	 */
	public void animateShow(View billView) {
		// 动画方式盖章
		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(this, View.SCALE_X, 1.1f, 0.9f, 1.0f).setDuration(300)).with(ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.1f, 0.9f, 1.0f).setDuration(300))
				.before(ObjectAnimator.ofFloat(billView, View.ALPHA, 0.8f, 0.9f, 1f).setDuration(200));
		set.setInterpolator(new AnticipateInterpolator());
		set.start();
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (mListener != null) {
					mListener.onDragEnd();
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
	}

	/**
	 * 提交
	 */
	public void submit() {
		// 暂时默认为正确
		mState = SubjectState.STATE_CORRECT;
		saveAnswer();
		judgeAnswer();
		showUAnswer(true);
	}

	/**
	 * 重置控件
	 */
	public void reset() {
		setVisibility(View.GONE);
	}

	/**
	 * 保存用户答案
	 */
	public void saveAnswer() {
		if (mState == SubjectState.STATE_INIT) {
			mState = SubjectState.STATE_UNFINISH;
		}
	}

	/**
	 * 判断用户答案
	 */
	public void judgeAnswer() {
		if (mData.isUser() && !mData.isCorrect()) {
			setGrayMode();
		}
	}

	/**
	 * 印章对比，相同印章，而且xy坐标范围在SIGN_XY_RANGE以内算对，否则算错
	 * 
	 * @param sign
	 * @return 两个印章是否为同一个印章
	 */
	public boolean compareSign(SignView sign) {
		SignInfo info = sign.getData();
		if (mData.getBitmap().equals(info.getBitmap())) {
			int dx = (int) Math.abs(info.getX() - mData.getX());
			int dy = (int) Math.abs(info.getY() - mData.getY());
			boolean correct = (dx <= SIGN_XY_RANGE && dy <= SIGN_XY_RANGE);
			if (correct) {
				mData.setCorrect(correct);
				info.setCorrect(correct);
			}
			Log.d(TAG, "compareSign dx:" + dx + ",dy:" + dy + ",correct:" + correct);
			return true;
		} else {
			Log.e(TAG, "compareSign ignore..");
			return false;
		}
	}

	/**
	 * 显示答案
	 * 
	 * @param user
	 *            true-显示用户答案，false-显示正确答案
	 */
	public void showUAnswer(boolean user) {
		if (user && mData.isUser()) {// 显示用户答案
			setVisibility(View.VISIBLE);
			loadBitmap(mData.getBitmap());
			postScale(mScale, mCurrentScaleTimes);
		} else if (!user && !mData.isUser()) {// 显示正确答案
			setVisibility(View.VISIBLE);
			loadBitmap(mData.getBitmap());
			postScale(mScale, mCurrentScaleTimes);
		} else {
			setVisibility(View.GONE);
		}
	}

	/**
	 * 设置拖拽监听
	 * 
	 * @param listener
	 */
	public void setOnDragListener(DragListener listener) {
		mListener = listener;
	}

	/**
	 * 加载bitmap
	 */
	public void loadBitmap(String bitmap) {
		if (!isBmLoaded()) {// 如果已经加载过则不需要加载
			loadImage(bitmap);
			if (mBitmap != null) {
				if (mData != null) {
					mData.setWidth(mBitmap.getWidth());
					mData.setHeight(mBitmap.getHeight());
				}
			}
		}
	}

	/**
	 * 图片设置为灰色模式，主要用于答错时的显示
	 */
	public void setGrayMode() {
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(0);// 饱和度 0灰色 100过度彩色，50正常
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		setColorFilter(filter);
	}

	@Override
	public String toString() {
		return mData.toString();
	}

	/**
	 * 拖拽接口
	 * 
	 * @author lucher
	 * 
	 */
	public interface DragListener {
		/**
		 * 拖拽开始，拖拽启动动画结束后调用
		 */
		void onDragStart();

		/**
		 * 拖拽结束，盖章动画结束后调用
		 */
		void onDragEnd();
	}

}
