package com.edu.library.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.edu.library.EduConstant;
import com.edu.library.R;
import com.edu.library.adapter.GuidanceViewPagerAdapter;
import com.edu.library.common.PreferenceHelper;

/**
 * 应用指导页，ViewPager，带小圆点导航 支持正常模式和查看模式，正常模式显示按钮，查看模式不显示
 * 
 * 
 * @author lucher
 * 
 */
public class GuidanceView extends RelativeLayout implements OnPageChangeListener, OnClickListener {

	/**
	 * 正常模式
	 */
	public final static int MODE_NORMAL = 1;
	/**
	 * 查看模式
	 */
	public final static int MODE_VIEW = 2;

	// 显示导航图片资源
	private int[] mPicResIds;

	// 使用的翻页控件
	private ViewPager mViewPager;

	// 显示的页数
	private int mPageCount;

	// 适配器
	private GuidanceViewPagerAdapter mAdapter;
	// 存放引导圆点数组
	private ImageView[] mIndicatorImgs;

	private Context mContext;

	// 当前模式
	private int mode = MODE_NORMAL;
	private Handler mHandler;

	/**
	 * @param context
	 * @param mode
	 *            运行模式-MODE_NORMAL/MODE_VIEW
	 * @param picResIds
	 *            引导页显示的图片资源id数组
	 * @param handler
	 *            处理跳转消息的handler
	 */
	public GuidanceView(Context context, int mode, int[] picResIds, Handler handler) {
		super(context);

		mContext = context;
		this.mode = mode;
		mHandler = handler;
		View view = View.inflate(context, R.layout.activity_guidance_view, null);
		addView(view);

		mPicResIds = picResIds;
		mPageCount = picResIds.length;

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mIndicatorImgs = new ImageView[mPageCount];

		initPages();

		initIndicator();

		mViewPager = (ViewPager) findViewById(R.id.vp_guidance);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * 初始化要显示的页面
	 */
	private void initPages() {
		List<View> list = new ArrayList<View>();

		for (int i = 0; i < mPageCount; i++) {
			View item = View.inflate(mContext, R.layout.item_guidance_page, null);
			ImageView ivPic = (ImageView) item.findViewById(R.id.iv_pic);
			ivPic.setBackgroundResource(mPicResIds[i]);
			// 马上体验按钮iv
			Button btnExperience = (Button) item.findViewById(R.id.btn_experience);
			if (i == mPageCount - 1 && mode == MODE_NORMAL) {
				btnExperience.setVisibility(View.VISIBLE);
				btnExperience.setOnClickListener(this);
			}
			list.add(item);
		}
		// 创建适配器
		mAdapter = new GuidanceViewPagerAdapter(list);
	}

	/**
	 * 初始化引导图标 动态创建多个小圆点
	 */
	private void initIndicator() {

		ImageView imgView;
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标

		for (int i = 0; i < mPageCount; i++) {
			imgView = new ImageView(mContext);
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
		if (mode == MODE_NORMAL) {
			PreferenceHelper preference = PreferenceHelper.getInstance(mContext);
			preference.setFirstTimeFalse(PreferenceHelper.KEY_GUIDANCE_PAGE);
			mHandler.sendEmptyMessage(EduConstant.MSG_GOTO_WELCOME_VIEW);
		}
	}
}