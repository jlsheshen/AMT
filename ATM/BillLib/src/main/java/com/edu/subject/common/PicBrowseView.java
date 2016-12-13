package com.edu.subject.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.edu.R;
import com.edu.library.picselect.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片查看控件
 * 
 * @author lucher
 * 
 */
public class PicBrowseView extends RelativeLayout implements OnPageChangeListener, OnClickListener {

	// 显示的图片资源
	private String[] mPicResIds;
	// 使用的翻页控件
	private HackyViewPager mViewPager;
	// 显示的页数
	private int mPageCount;
	// 适配器
	private PicBrowseAdapter mAdapter;
	// 存放引导圆点数组
	private ImageView[] mIndicatorImgs;
	private Context mContext;
	// 关闭按钮
	private ImageButton ibtnClose;
	private CloseListener mListener;

	// 对应的问题
	private String mQuestion;

	public PicBrowseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View.inflate(context, R.layout.layout_picture_browse, this);
		mContext = context;
	}

	/**
	 * 设置图片资源
	 * 
	 * @param question
	 *            对应的问题
	 * @param pics
	 *            显示的图片资源
	 * @return 是否需要显示
	 */
	public boolean setResources(String question, String[] pics) {
		if (question != null && !question.equals("")) {
			mPageCount++;
			mQuestion = question;
		}
		if (pics != null && pics.length > 0) {
			mPageCount += pics.length;
			mPicResIds = pics;
		}
		init();
		return mPageCount > 0;
	}

	/**
	 * 初始化
	 */
	private void init() {
		mIndicatorImgs = new ImageView[mPageCount];

		initPages();

		initIndicator();

		mViewPager = (HackyViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		ibtnClose = (ImageButton) findViewById(R.id.ibtnClose);
		ibtnClose.setOnClickListener(this);
	}

	/**
	 * 初始化要显示的页面
	 */
	private void initPages() {
		List<View> list = new ArrayList<View>();
		
		int count = mPageCount;
		if(mQuestion != null && !mQuestion.equals("")) {
			AutoSplitTextView textView = (AutoSplitTextView) View.inflate(mContext, R.layout.view_bill_question, null);
//			textView.setMovementMethod(ScrollingMovementMethod.getInstance());
			textView.setText(mQuestion);
			list.add(textView);
			count--;
		}
		for (int i = 0; i < count; i++) {
			 BrowseImageView imageView = new BrowseImageView(mContext);
			 imageView.loadImage(mPicResIds[i]);
			 list.add(imageView);
		
		}
		// 创建适配器
		mAdapter = new PicBrowseAdapter(list);
	}

	/**
	 * 初始化引导图标 动态创建多个小圆点
	 */
	private void initIndicator() {
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
		for (int i = 0; i < mPageCount; i++) {
			ImageView imgView = new ImageView(mContext);

			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			mIndicatorImgs[i] = imgView;
			if (i == 0) { // 初始化第一个为选中状态
				mIndicatorImgs[i].setBackgroundResource(R.drawable.iv_indicator_current);
			} else {
				mIndicatorImgs[i].setBackgroundResource(R.drawable.iv_indicator);
			}
			((ViewGroup) v).addView(mIndicatorImgs[i]);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		// 改变所有导航的背景图片为：未选中
		for (int i = 0; i < mIndicatorImgs.length; i++) {
			mIndicatorImgs[i].setBackgroundResource(R.drawable.iv_indicator);
		}

		// 改变当前背景图片为：选中
		mIndicatorImgs[position].setBackgroundResource(R.drawable.iv_indicator_current);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ibtnClose) {
			if (mListener != null) {
				mListener.onClose();
			}
		}
	}

	/**
	 * 设置关闭监听
	 * 
	 * @param listener
	 */
	public void setOnCloseListener(CloseListener listener) {
		mListener = listener;
	}

	/**
	 * 关闭监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface CloseListener {
		/**
		 * 关闭图片查看控件
		 */
		void onClose();
	}

	/**
	 * 支持缩放的进度imageview，直接使用imageview，网络图片加载后可能导致图片显示太小的问题，需要在图片加载后加入缩放才正常
	 * 
	 * @author lucher
	 * 
	 */
	public class BrowseImageView extends ProgressImageView {

		public BrowseImageView(Context context) {
			super(context);
		}

		@Override
		public void loadImage(String uri) {
			super.loadImage(uri);
			if (mBitmap != null) {
				new PhotoViewAttacher(this, true);
			}
		}

		@Override
		public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
			super.onLoadingComplete(imageUri, arg1, loadedImage);
			new PhotoViewAttacher(this, true);
		}
	}
}
