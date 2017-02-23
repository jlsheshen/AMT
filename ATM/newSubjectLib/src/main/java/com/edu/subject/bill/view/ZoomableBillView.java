package com.edu.subject.bill.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.bill.BatchOperateHelper;
import com.edu.subject.bill.BillAnswerHandler;
import com.edu.subject.bill.DragState;
import com.edu.subject.bill.FocusHandler;
import com.edu.subject.bill.element.ElementLayoutParams;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BaseElementInfo;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.bill.element.info.FlashInfo;
import com.edu.subject.bill.element.info.SignInfo;
import com.edu.subject.bill.listener.BillZoomListener;
import com.edu.subject.bill.listener.SignViewListener;
import com.edu.subject.bill.template.BillTemplate;
import com.edu.subject.bill.template.BillTemplateDao;
import com.edu.subject.bill.view.BackgroudView.ImageLoadListener;
import com.edu.subject.bill.view.SignView.DragListener;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.answer.BillAnswerData;
import com.edu.subject.data.answer.BillAnswerData.SignResult;

/**
 * 支持缩放，自由滚动，智能滚动等功能的单据视图
 * 
 * @author lucher
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class ZoomableBillView extends ViewGroup implements OnTouchListener, DragListener, ImageLoadListener {

	private static final String TAG = ZoomableBillView.class.getSimpleName();

	// 支持的最大缩放倍数
	public static int MAX_SCALE_TIMES = 2;
	// 初始化缩放比例，根据图片和当前控件的实际尺寸计算出，如果图片宽大于实际尺寸，此值将小于0
	private float mInitScale = -1;
	// 当前缩放比例
	private float mScale = mInitScale;
	// 缩放比重
	private float mScaleWeight = 1.2f;
	// 缩放监听
	private BillZoomListener mZoomListener;
	// 当前是否可放大，缩小
	private boolean mZoomInEnable, mZoomOutEnable;
	// 缩放标识，true：放大，false：缩小
	private boolean mZoomFlag;
	// 当前缩放倍数
	private int mCurrentScaleTimes = 0;

	// 控件的宽度
	private int mWidth;
	// 控件的高度
	private int mHeight;
	// 单据内容区的宽度
	private int mBillWidth;
	// 单据内容区的高度
	private int mBillHeight;

	// Test数据
	private TestBillData mTestData;
	// 单据模板
	private BillTemplate mTemplate;

	// 滑动相关
	private Scroller mScroller;
	// 定义手势检测器实例
	private GestureDetectorCompat mDetector;
	// 阻力系数基数，值越大，阻力越大（如果单据出界，手指滑动距离与单据滑动距离比例系数基数，用于模拟滑动阻力）
	private float mScrollRadio = 5;

	// 背景图
	private BackgroudView mBackgroud;
	private Context mContext;

	// 所有空对应的编辑框
	private List<BlankEditText> mEtBlanks;
	// 所有印章对应的视图
	private List<SignView> mSignViews;
	// 所有闪电符对应的视图
	private List<FlashView> mFlashViews;

	// 盖章状态
	private DragState mDragState = DragState.INITIAL;
	// 拖拽用坐标
	private float oldX = 0;
	private float oldY = 0;
	// 当前正在拖拽的印章视图
	private SignView mDraggingSignView;
	// 半透明视图
	private TranslucentView mTransView;
	// 盖章监听
	private SignViewListener mSignListener;

	// 焦点处理事件
	private FocusHandler mFocusHandler;
	// 答案处理类
	private BillAnswerHandler mAnswerHandler;
	// 批量处理帮助类
	private BatchOperateHelper mBatchOpeHelper;

	public ZoomableBillView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		init();
	}

	/**
	 * 底图是否加载完毕
	 * @return
	 */
	public boolean loaded() {
		return mBackgroud.isBmLoaded();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mScroller = new Scroller(mContext);
		mBackgroud = new BackgroudView(mContext);
		mBackgroud.setImageLoadListener(this);
		addView(mBackgroud);
		// 创建手势检测器
		mDetector = new GestureDetectorCompat(mContext, new BillScrollDetector());
		mEtBlanks = new ArrayList<BlankEditText>();
		mSignViews = new ArrayList<SignView>();
		mFlashViews = new ArrayList<FlashView>(1);

		setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
		setFocusableInTouchMode(true);
		setFocusable(true);
		// 初始化焦点事件处理类
		mFocusHandler = new FocusHandler(mContext, this);
		mAnswerHandler = new BillAnswerHandler();
		mBatchOpeHelper = new BatchOperateHelper();
	}

	/**
	 * 设置单据模板
	 * 
	 * @param template
	 */
	private void setBillTempate(BillTemplate template) {
		mTemplate = template;
		// 初始化底图
		mBackgroud.loadImage(mTemplate.getBitmap());
		mBillWidth = mBackgroud.getBmWidth();
		mBillHeight = mBackgroud.getBmHeight();
		if (!mBackgroud.isBmLoaded()) {
			Log.e(TAG, "底图为空或者在加载中，无法继续");
		}
		Log.i(TAG, String.format("init mBillWidth:%s,mBillHeight:%s", mBillWidth, mBillHeight));
	}

	/**
	 * 初始化缩放比例，加入所有空等操作
	 */
	private void initContent() {
		if (mBillWidth <= 0)
			return;
		mScale = mInitScale = (float) mWidth / mBillWidth;
		refreshZoomState();
		if (mZoomListener != null) {
			mZoomListener.onZoomInit(mZoomInEnable, mZoomOutEnable);
		}
		// 初始化背景图
		mBackgroud.postScale(mScale, mCurrentScaleTimes);
		// 初始化所有空
		List<BaseElementInfo> elements = mTemplate.getElementDatas();
		if (elements != null) {
			for (BaseElementInfo element : elements) {
				if (element instanceof BlankInfo) {
					BlankEditText etBlank;
					if (element.getType() == ElementType.TYPE_AMOUNT_LOWER_GROUP) {// 小写金额组合空
						etBlank = new BlankGroupAmountEditText(mContext, mTestData.getTestMode(), mTestData.getState());
					} else {
						etBlank = new BlankEditText(mContext, mTestData.getTestMode(), mTestData.getState());
					}
					etBlank.apply((BlankInfo) element, mScale, mScaleWeight);
					addView(etBlank);
					mEtBlanks.add(etBlank);
					mFocusHandler.add(etBlank);
					mAnswerHandler.addBlank(etBlank);
				} else if (element instanceof SignInfo) {
					SignView signView = new SignView(mContext, mTestData.getTestMode(), mTestData.getState());
					signView.setVisibility(View.GONE);
					signView.apply((SignInfo) element, mScale);
					addView(signView);
					mSignViews.add(signView);
				} else if (element instanceof FlashInfo) {
					// 根据后期逻辑判断是否需要显示出闪电符
					FlashView flashView = new FlashView(mContext);
					flashView.setAlpha(0);
					flashView.apply((FlashInfo) element, mScale);
					addView(flashView);
					mFlashViews.add(flashView);
				}
			}
			// 初始化用户印章
			BillAnswerData answer = mTestData.getUAnswerData();
			if (answer != null && answer.getSigns() != null) {
				List<SignResult> uSigns = answer.getSigns();
				List<SignInfo> signs = BillTemplateDao.getInstance(mContext).loadSigns(uSigns);
				for (SignInfo sign : signs) {
					SignView signView = new SignView(mContext, mTestData.getTestMode(), mTestData.getState());
					signView.setVisibility(View.GONE);
					signView.apply(sign, mScale);
					addView(signView);
					mSignViews.add(signView);
				}
			}

			mAnswerHandler.setSignViews(mSignViews);
			mBatchOpeHelper.setElements(mEtBlanks, mSignViews, mFlashViews);
		}
	}

	/**
	 * 添加新印章，添加印章时，先把单据缩放到初始状态，并且让印章居中显示
	 * 
	 * @param signData
	 * @return 返回是否可添加
	 */
	public boolean addSignView(SignInfo signData) {
		if (!mBackgroud.isBmLoaded()) {
			dragHint("底图还在拼命加载中，请稍后重试");
			return false;
		} else if (mDraggingSignView != null) {
			dragHint("先把当前印章盖完才能继续盖别的印章");
			return false;
		} else if (mTestData.getState() == SubjectState.STATE_CORRECT || mTestData.getState() == SubjectState.STATE_WRONG) {
			dragHint("现在不能执行盖章操作");
			return false;
		} else if (mAnswerHandler.existSignView(signData)) {
			dragHint("该印章已经存在，不能重复盖章");
			return false;
		}
		mDragState = DragState.WAITING;
		// 盖章时缩放到原始大小
		zoomToInit();
		setTransBackgroud();
		// 加入待盖的印章
		mDraggingSignView = new SignView(mContext, mTestData.getTestMode(), mTestData.getState());
		mDraggingSignView.setAsDragView(this, signData, mWidth, mHeight, mInitScale, Math.abs(getScrollBorder()[1]));
		if (mDraggingSignView.apply(signData, mScale)) {
			addView(mDraggingSignView);
			return true;
		} else {
			dragHint("印章图片还在加载中，暂时无法添加，请稍后重试");
			cancelSign();
			return false;
		}
	}

	/**
	 * 初始化半透明视图，目前提供给盖章用
	 */
	private void setTransBackgroud() {
		mTransView = new TranslucentView(mContext, mBackgroud.getWidth(), mBackgroud.getHeight());
		mTransView.setSignListener(mSignListener);
		mTransView.setSignView(mDraggingSignView);
		// 置顶
		removeView(mTransView);
		addView(mTransView);
	}

	/**
	 * 显示闪电符
	 * 
	 */
	public void showFlashes() {
		// 闪电符缩放
		for (FlashView flashView : mFlashViews) {
			flashView.show(true);
		}
	}

	/**
	 * 设置单据缩放监听
	 * 
	 * @param listener
	 */
	public void setBillZoomListener(BillZoomListener listener) {
		mZoomListener = listener;
	}

	/**
	 * 设置盖章监听
	 * 
	 * @param listener
	 */
	public void setSignListener(SignViewListener listener) {
		mSignListener = listener;
	}

	/**
	 * 放大
	 */
	public void zoomIn() {
		if (!mBackgroud.isBmLoaded()) {
			dragHint("底图还在拼命加载中，请稍后重试");
			return;
		} else if (!mZoomInEnable || mDragState == DragState.WAITING) {
			if (mDragState == DragState.WAITING) {
				dragHint("当前正在进行盖章操作，不能缩放");
			}
			return;
		}
		mZoomFlag = true;
		mCurrentScaleTimes++;
		mScale *= mScaleWeight;
		zoom();
	}

	/**
	 * 缩小
	 */
	public void zoomOut() {
		if (!mBackgroud.isBmLoaded()) {
			dragHint("底图还在拼命加载中，请稍后重试");
			return;
		}
		if (!mZoomOutEnable || mDragState == DragState.WAITING) {
			if (mDragState == DragState.WAITING) {
				dragHint("当前正在进行盖章操作，不能缩放");
			}
			return;
		}
		mZoomFlag = false;
		mCurrentScaleTimes--;
		mScale /= mScaleWeight;
		zoom();
	}

	/**
	 * 缩放到初始缩放状态
	 */
	private void zoomToInit() {
		if (!mZoomOutEnable || mCurrentScaleTimes == 0) {
			Log.d(TAG, "can't or dont need zoomOut");
			return;
		}
		mZoomFlag = false;
		mCurrentScaleTimes = 0;
		mScale = mInitScale;
		zoom();
	}

	/**
	 * 缩放具体实现方法
	 */
	private void zoom() {
		if (mZoomListener != null) {
			if (mZoomFlag) {
				mZoomListener.onZoomInStart(mCurrentScaleTimes);
			} else {
				mZoomListener.onZoomOutStart(mCurrentScaleTimes);
			}
		}
		refreshZoomState();
		// 底图缩放
		mBackgroud.postScale(mScale, mCurrentScaleTimes);
		// 印章缩放
		if (mDraggingSignView != null) {
			mDraggingSignView.postScale(mScale, mCurrentScaleTimes);
		}
		mBatchOpeHelper.zoom(mScale, mCurrentScaleTimes);
		requestLayout();
		checkScrollBorder(false);

		if (mZoomListener != null) {
			if (mZoomFlag) {
				mZoomListener.onZoomInEnd(mCurrentScaleTimes, mZoomInEnable, mZoomOutEnable);
			} else {
				mZoomListener.onZoomOutEnd(mCurrentScaleTimes, mZoomInEnable, mZoomOutEnable);
			}
		}
		Log.i(TAG, String.format("zoom mScale:%s", mScale));
	}

	/**
	 * 刷新缩放状态
	 */
	private void refreshZoomState() {
		mZoomInEnable = mCurrentScaleTimes >= MAX_SCALE_TIMES ? false : true;
		mZoomOutEnable = mCurrentScaleTimes < 1 ? false : true;
		Log.i(TAG, "currentScaleTimes:" + mCurrentScaleTimes + ",zoomInEnable:" + mZoomInEnable + ",zoomOutEnable:" + mZoomOutEnable);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		//如果底图加载成功，则用底图的高度作为控件高度，否则直接使用控件高度
		if (mBackgroud.isBmLoaded()) {
			mHeight = Math.round(Math.min(MeasureSpec.getSize(heightMeasureSpec), mBillHeight * mInitScale));
		} else {
			mHeight = Math.round(Math.max(MeasureSpec.getSize(heightMeasureSpec), mBillHeight * mInitScale));
		}

		if (mInitScale == -1) {// 初始化初始缩放比例
			initContent();
		}

		// 计算子控件的尺寸
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			ElementLayoutParams params = (ElementLayoutParams) child.getLayoutParams();
			if (params.getType() == ElementType.TYPE_BG) {
				// 如果是背景图片，宽根据父控件的宽定，高取缩放后背景图片高以及父控件高的最小值
				int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (mBillWidth * mScale), MeasureSpec.AT_MOST);
				int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (mBillHeight * mScale), MeasureSpec.AT_MOST);
				child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			} else {
				if (child.getVisibility() != GONE) {
					child.measure(params.getWidth(), params.getHeight());
				}
			}
		}

		setMeasuredDimension(mWidth, mHeight);
		// Log.d(TAG, String.format("onMeasure mWidth:%s,mHeight:%s", mWidth,
		// mHeight));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				ElementLayoutParams params = (ElementLayoutParams) child.getLayoutParams();
				int left = params.getX();
				int top = params.getY();
				int right = params.getX() + params.getWidth();
				int bottom = params.getY() + params.getHeight();

				child.layout(left, top, right, bottom);

				if (!mBackgroud.isBmLoaded()) {//底图没加载成功之前，让加载背景图铺满屏幕
					child.layout(left, top, getWidth(), getHeight());
				}
				// Log.i(TAG,
				// String.format("onLayout index:%s, left:%s,top:%s,right:%s,bottom:%s",
				// i, left, top, right, bottom));
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		startDragSignView();

		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (!mBackgroud.isBmLoaded()) {
			return false;
		}
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
			break;
		}
		return mDragState == DragState.DRAGGING;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mBackgroud.isBmLoaded()) {
			return true;
		}
		if (mDragState == DragState.INITIAL || mDragState == DragState.DONE) {
			mDetector.onTouchEvent(event);
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
				checkScrollBorder(true);
			}
		} else if (mDragState == DragState.DRAGGING) {
			handleDragEvent(event);
		}

		return true;
	}

	/**
	 * 盖章提示信息
	 * 
	 * @param msg
	 */
	private void dragHint(String msg) {
		if (mSignListener != null) {
			mSignListener.onDragHint(mDraggingSignView, msg);
		}
	}

	/**
	 * 开始拖拽印章视图
	 */
	private void startDragSignView() {
		removeView(mTransView);

		if (mDraggingSignView == null)
			return;
		mDraggingSignView.startDrag();
	}

	/**
	 * 结束拖拽印章视图
	 */
	private void endDragSignView() {
		if (mDraggingSignView == null)
			return;

		mDraggingSignView.endDrag();
		mDraggingSignView.setOnTouchListener(null);
		mDragState = DragState.DONE;
		// 添加印章到视图里
		SignView signView = new SignView(mContext, mTestData.getTestMode(), mTestData.getState());
		signView.setOnDragListener(this);
		signView.loadBitmap(mDraggingSignView.getData().getBitmap());
		if (signView.apply(mDraggingSignView.getData(), mScale)) {
			// signView.showAnswer(true);
			addView(signView);
			mSignViews.add(signView);
			signView.animateShow(this);
		}
		// 移除临时印章图标
		removeView(mDraggingSignView);
		mDraggingSignView = null;
	}

	/**
	 * 处理印章拖拽
	 * 
	 * @param event
	 */
	private void handleDragEvent(MotionEvent event) {
		Log.i("lucher", "handleDragEvent:" + event.getAction() + "," + event.getX() + "," + event.getY());
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_MOVE:
			float dx = oldX - event.getX();// x方向手指移动的距离
			float dy = oldY - event.getY();// y方向手指移动的距离
			handleSignMove(dx, dy);
			oldX = event.getX();
			oldY = event.getY();

			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			endDragSignView();
			break;
		}

	}

	/**
	 * 处理印章移动事件，包括单据滚动边界判断逻辑，目前只考虑单据上下滚动情况，因为盖章的时候不能缩放
	 * 
	 * @param dx
	 *            x方向滑动距离
	 * @param dy
	 *            y方向滑动距离
	 */
	private void handleSignMove(float dx, float dy) {
		if (mDraggingSignView == null)
			return;

		// 获取印章视图本身可见的坐标区域，坐标以自己的左上角为原点（0，0）
		Rect rect = new Rect();
		mDraggingSignView.getLocalVisibleRect(rect);
		Log.w(TAG, "rect:" + rect);
		// 获取单据滚动范围边界值
		int[] border = getScrollBorder();

		if (rect.top > 0) {// 上边出界
			Log.e(TAG, "out ...top");
			if (border[1] > 0) {// 单据上边未显示全,可以滑动
				if (dy > 0) {// 手指往上滑动时，向下滑动单据
					dy = Math.min(dy * 10, border[1]);// 加大滚动的距离,同时需要考虑是否出界
					scrollBy(0, (int) -dy);
				}
				Log.d(TAG, "dy:" + dy);
			} else {// 不可滑动，则不动
				if (dy > 0) {
					dy = 0;
				}
			}
		} else if (rect.bottom < mDraggingSignView.getHeight()) {// 下边出界
			Log.e(TAG, "out ...bottom");
			if (border[3] > 0) {// 单据下边未显示全,可以滑动
				if (dy < 0) {// 手指往下滑动时，向上滑动单据
					dy = Math.max(dy * 10, -border[3]);// 加大滚动的距离,同时需要考虑是否出界
					scrollBy(0, (int) -dy);
				}
				Log.d(TAG, "dy:" + dy);
			} else {// 不可滑动，则不动
				if (dy < 0) {
					dy = 0;
				}
			}
		}

		if (rect.left > 0) {// 左边出界
			Log.e(TAG, "out ...left");
			if (dx > 0) {// 手指往左滑动时，印章不动
				dx = 0;
			}
			Log.d(TAG, "dx:" + dx);
		} else if (rect.right < mDraggingSignView.getWidth()) {// 右边出界
			Log.e(TAG, "out ...bottom");
			if (dx < 0) {// 手指往右滑动时，印章不动
				dx = 0;
			}
			Log.d(TAG, "dx:" + dx);
		}

		mDraggingSignView.move(dx, dy, mScale);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// Log.i(TAG, "computeScroll " + mScroller.getCurrX() + " " +
			// mScroller.getCurrY());
			invalidate();
		}
	}

	/**
	 * 界面滚动时检测单据内容是否出界
	 * 
	 * @param animate
	 *            是否以动画方式调整位置
	 */
	public void checkScrollBorder(boolean animate) {
		int[] border = getScrollBorder();
		// 单据的边界
		int left = border[0];
		int top = border[1];
		int right = border[2];
		int bottom = border[3];
		// x，y方向上出界的距离
		int dx = 0;
		int dy = 0;

		if (top < 0) {
			dy = -top;
		} else if (bottom <= 0) {
			dy = bottom;
		}
		if (left < 0) {
			dx = -left;
		} else if (right <= 0) {
			dx = right;
		}
		if (dx != 0 || dy != 0) {
			if (animate) {
				mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 300);
				invalidate();
			} else {
				scrollBy(dx, dy);
			}
		}

	}

	/**
	 * 获取单据滚动范围边界值
	 * 
	 * @return 返回4位长度的int数组[left, top, right, bottom]，分别表示各个方向离边界值的距离，小于0代表超出边界
	 */
	private int[] getScrollBorder() {
		// 单据的边界
		int left = getScrollX();
		int top = getScrollY();
		int right = Math.round(mBillWidth * mScale - mWidth - left);
		int bottom = Math.round(mBillHeight * mScale - mHeight - top);
		Log.d(TAG, String.format("getBorder left:%s,top:%s,right:%s,bottom:%s", left, top, right, bottom));

		int[] border = new int[] { left, top, right, bottom };
		return border;
	}

	/**
	 * 单据手势监听
	 * 
	 * @author lucher
	 * 
	 */
	class BillScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
			// 界面滚动
			int[] border = getScrollBorder();
			int left = border[0];
			int top = border[1];
			int right = border[2];
			int bottom = border[3];

			// 出界时加入滑动阻力
			if ((left <= 0 && dx < 0) || (right <= 0 && dx > 0)) {// left出界时，往右滑动或者right出界时，往左滑动
				// 根据滑动距离动态计算阻力
				float xScrollRadio = mScrollRadio + (float) e2.getX() / mWidth * mScrollRadio;
				dx = dx / xScrollRadio;
			}
			if ((top <= 0 && dy < 0) || (bottom <= 0 && dy > 0)) {// top出界时，往下滑动或者bottom出界时，往上滑动
				// 根据滑动距离动态计算阻力
				float yScrollRadio = mScrollRadio + (float) e2.getY() / mHeight * mScrollRadio;
				dy = dy / yScrollRadio;
			}

			scrollBy((int) dx, (int) dy);

			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mZoomInEnable) {
				zoomIn();
			} else {
				zoomToInit();
			}
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (!mBackgroud.isBmLoaded()) {
				return true;
			}
			mFocusHandler.handleSingleTab(e, getScrollBorder());
			return true;
		}
	}

	/**
	 * 如果获取焦点的空显示不全，则滚动单据界面让该空完全显示
	 * 
	 * @param view
	 *            当前空视图
	 */
	public void scrollToWrapBlank(View view) {
		if (view == null || !(view instanceof BlankEditText))
			return;
		// 获取当前空在屏幕的位置
		int[] blankLocation = new int[2];
		view.getLocationInWindow(blankLocation);
		int blankX = blankLocation[0];// 当前空在窗口中的x坐标
		int blankY = blankLocation[1];// 当前空在窗口中的y坐标
		// 获取单据在屏幕的位置
		int[] billLocation = new int[2];
		getLocationInWindow(billLocation);
		int billX = billLocation[0];// 单据在窗口中的x坐标
		int billY = billLocation[1];// 单据在窗口中的y坐标

		int extra = 50;// 通过坐标计算处理的距离只能刚好显示出控件，需要加一个额外的距离，可以显示出空旁边的内容
		int dx = 0;// 单据在x轴上需要滚动的距离
		int dy = 0;// 单据在y轴上需要滚动的距离
		int[] border = getScrollBorder();// 用于边界值判断
		if (blankX < billX) {// 控件超出屏幕左边界
			dx = blankX - billX - extra;
			dx = Math.max(dx, -border[0]);
		} else if (blankX + view.getWidth() > billX + getWidth()) {// 控件超出屏幕右边界
			dx = blankX + view.getWidth() - billX - getWidth() + extra;
			dx = Math.min(dx, border[2]);
		}
		if (blankY < billY) {// 控件超出屏幕上边界
			dy = blankY - billY - extra;
			dy = Math.max(dy, -border[1]);
		} else if (blankY + view.getHeight() > billY + getHeight()) {// 控件超出屏幕下边界
			dy = blankY + view.getHeight() - getHeight() - billY + extra;
			dy = Math.min(dy, border[3]);
		}

		if (dx != 0 || dy != 0) {
			Log.d(TAG, "scroll by :" + dx + "," + dy);
			mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 300);
			invalidate();
		}
	}

	/**
	 * 是否正在盖章
	 * 
	 * @return
	 */
	public boolean isSigning() {
		return mDraggingSignView != null;
	}

	/**
	 * 取消盖章
	 */
	public void cancelSign() {
		// 移除临时印章图标
		removeView(mDraggingSignView);
		mDraggingSignView = null;
		removeView(mTransView);
		mDragState = DragState.DONE;
	}

	@Override
	public void onDragStart() {
		if (mSignListener != null) {
			mSignListener.onDragStart(mDraggingSignView);
		}
		mDragState = DragState.DRAGGING;
	}

	@Override
	public void onDragEnd() {
		if (mSignListener != null) {
			mSignListener.onDragEnd(mDraggingSignView);
		}
	}

	/**
	 * 从object恢复视图状态
	 * 
	 * @param data
	 */
	public void applyData(BaseTestData data) {
		mTestData = (TestBillData) data;
		mAnswerHandler.setTestData(mTestData);
		setBillTempate(mTestData.getTemplate());
	}

	/**
	 * 提交
	 * 
	 * @return
	 */
	public float submit() {
		if (isSigning()) {
			cancelSign();
		}
		mAnswerHandler.submit();
		return mTestData.getuScore();
	}

	/**
	 * 保存答案
	 */
	public void saveAnswer() {
		if (mTestData.getState() != SubjectState.STATE_INIT && mTestData.getState() != SubjectState.STATE_UNFINISH)
			return;
		mAnswerHandler.save();
	}

	/**
	 * 显示用户答案
	 */
	public void showUserAnswer() {
		switchAnswer(true);
	}

	/**
	 * 显示详情
	 */
	public void showDetails() {
		switchAnswer(false);
	}

	/**
	 * 切换用户/正确答案
	 * 
	 * @param user
	 */
	private void switchAnswer(boolean user) {
		mBatchOpeHelper.switchAnswer(user);
	}

	/**
	 * 重置
	 */
	public void reset() {
		mBatchOpeHelper.reset(this);
		requestDefaultFocus();
	}

	/**
	 * 设置填制完毕监听
	 * 
	 * @param listener
	 */
	public void setSubjectListener(SubjectListener listener) {
		mFocusHandler.setSubjectListener(listener);
	}

	/**
	 * 获取默认焦点
	 */
	public void requestDefaultFocus() {
		mFocusHandler.requestDefaultFocus();
	}

	@Override
	public void onLoadStart() {

	}

	@Override
	public void onLoadComplete() {
		mBillWidth = mBackgroud.getBmWidth();
		mBillHeight = mBackgroud.getBmHeight();
		requestLayout();
	}
}
