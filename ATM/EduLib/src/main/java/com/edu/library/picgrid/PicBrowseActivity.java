package com.edu.library.picgrid;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.edu.library.R;
import com.edu.library.picselect.HackyViewPager;

/**
 * 显示图片的页面,多张图片可滑动查看，可缩放查看大图
 * 
 * @author lucher
 * 
 */
public class PicBrowseActivity extends FragmentActivity implements OnPageChangeListener, OnPhotoTapListener {

	// 使用的翻页控件
	private HackyViewPager mViewPager;

	// 显示的页数
	private int mPageCount;

	// 适配器
	private PicBrowsePagerAdapter mAdapter;
	// 存放引导圆点数组
	private ImageView[] mIndicatorImgs;

	private Context mContext;
	// 所有图片数据
	private List<TaskPicData> mDatas;
	// 默认选择图片索引
	private int mDefaultIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_pic_browse);

		mContext = this;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mDefaultIndex = bundle.getInt("index");
			mDatas = (List<TaskPicData>) bundle.getSerializable("datas");
			mPageCount = mDatas.size();
		}

		initPages();

		initIndicator();

		// 初始化mDefaultIndex为选中状态
		mViewPager.setCurrentItem(mDefaultIndex);
		mIndicatorImgs[mDefaultIndex].setImageResource(R.drawable.iv_indicator_current);
	}

	/**
	 * 初始化要显示的页面
	 */
	private void initPages() {
		// 创建适配器
		mAdapter = new PicBrowsePagerAdapter(getSupportFragmentManager(), mDatas, this);
		mViewPager = (HackyViewPager) findViewById(R.id.vp_pics);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * 初始化引导图标 动态创建多个小圆点
	 */
	private void initIndicator() {
		mIndicatorImgs = new ImageView[mPageCount];
		LinearLayout layout = (LinearLayout) findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标

		for (int i = 0; i < mPageCount; i++) {
			ImageView imgView = new ImageView(mContext);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			mIndicatorImgs[i] = imgView;
			imgView.setImageResource(R.drawable.iv_indicator);
			layout.addView(mIndicatorImgs[i]);
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
			mIndicatorImgs[i].setImageResource(R.drawable.iv_indicator);
		}

		// 改变当前背景图片为：选中
		mIndicatorImgs[position].setImageResource(R.drawable.iv_indicator_current);
	}

	@Override
	public void onPhotoTap(View arg0, float arg1, float arg2) {
		finish();
	}

	@Override
	public void onOutsidePhotoTap() {
		finish();
	}
}
