package com.edu.subject.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.edu.library.util.ToastUtil;
import com.edu.subject.ISubject;
import com.edu.subject.R;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.bill.SignData;
import com.edu.subject.bill.element.info.SignInfo;
import com.edu.subject.bill.listener.BillZoomListener;
import com.edu.subject.bill.listener.SignViewListener;
import com.edu.subject.bill.view.SignView;
import com.edu.subject.bill.view.ZoomableBillView;
import com.edu.subject.common.PicBrowseView;
import com.edu.subject.common.PicBrowseView.CloseListener;
import com.edu.subject.common.SlidingDragLayout;
import com.edu.subject.common.SlidingDragLayout.DragListener;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.util.SoundPoolUtil;

/**
 * 单据题型视图
 * 
 * @author lucher
 * 
 */
public class BillView extends RelativeLayout implements ISubject, BillZoomListener, SignViewListener, OnClickListener, CloseListener {

	// 缩放按钮
	private ImageButton btnZoomIn, btnZoomOut;
	private View switchAnswer;
	private Button btnShowUser, btnShowRight;

	private Context mContext;
	public ZoomableBillView billView;
	// 显示图片的小角标
	private ImageButton ibtnPic;
	// 滑动菜单控件
	private SlidingDragLayout slidingLayout;
	// 图片查看控件
	private PicBrowseView picBrowseView;

	// 单据相关数据
	private TestBillData mData;
	// 是否用于分组单据
	private boolean isGroup;
	// 是否初始化
	private boolean inited;
	private SubjectListener mListener;

	public BillView(Context context, TestBillData data) {
		super(context);

		View.inflate(context, R.layout.loading_layout, this);
		mContext = context;
		mData = data;
	}

	/**
	 * 用于延迟加载，当界面显示时再加载
	 */
	@Override
	public void onVisible() {
		if (!inited) {
			removeAllViews();
			View.inflate(mContext, R.layout.view_subject_bill_view, this);
			init();
			billView.setSubjectListener(mListener);
		}
		requestDefaultFocus();
		inited = true;
	}

	/**
	 * 初始化
	 */
	private void init() {
		btnZoomIn = (ImageButton) findViewById(R.id.ibtnZoomIn);
		btnZoomOut = (ImageButton) findViewById(R.id.ibtnZoomOut);
		btnZoomIn.setOnClickListener(this);
		btnZoomOut.setOnClickListener(this);
		billView = (ZoomableBillView) findViewById(R.id.billView);
		billView.setBillZoomListener(this);
		billView.setSignListener(this);
		ibtnPic = (ImageButton) findViewById(R.id.ibtnPic);

		btnShowUser = (Button) findViewById(R.id.btnShowUser);
		btnShowRight = (Button) findViewById(R.id.btnShowRight);
		switchAnswer = findViewById(R.id.switchView);
		btnShowUser.setOnClickListener(this);
		btnShowRight.setOnClickListener(this);
		if (mData.getTestMode() == TestMode.MODE_SHOW_DETAILS
				|| (mData.getTestMode() == TestMode.MODE_PRACTICE && (mData.getState() == SubjectState.STATE_CORRECT || mData.getState() == SubjectState.STATE_WRONG))) {
			showSwitch(true);
		}

		applyData(mData);
		slidingLayout = (SlidingDragLayout) findViewById(R.id.sliding_menu);
		slidingLayout.setBadgeView(ibtnPic);
		slidingLayout.setDragListener(new DragListener() {

			@Override
			public void onOpen() {
			}

			@Override
			public void onDrag(float percent) {
			}

			@Override
			public void onClose() {
				checkScrollBorder();
			}
		});

	}

	/**
	 * 切换按钮
	 */
	private void showSwitch(boolean show) {
		if (show) {
			switchAnswer.setVisibility(View.VISIBLE);
			btnShowUser.setBackgroundResource(R.drawable.btn_switch_green);
			btnShowRight.setBackgroundResource(R.drawable.btn_transparent);
		} else {
			switchAnswer.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置为组控件
	 */
	public void setGroup() {
		isGroup = true;
	}

	/**
	 * 检测单据内容是否出界
	 */
	public void checkScrollBorder() {
		billView.checkScrollBorder(false);
	}

	/**
	 * 获取测试数据
	 * 
	 * @return
	 */
	public TestBillData getTestData() {
		return mData;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.ibtnZoomIn) {
			billView.zoomIn();
		} else if (view.getId() == R.id.ibtnZoomOut) {
			billView.zoomOut();
		} else if (view.getId() == R.id.ibtnPic) {
			if (billView.loaded()) {
				slidingLayout.openMenu();
			} else {
				ToastUtil.showToast(mContext, "底图加载完成后才能打开菜单，请稍后重试");
			}
		} else if (view.getId() == R.id.btnShowRight) {
			if (billView.loaded()) {
				showDetails();
				btnShowUser.setBackgroundResource(R.drawable.btn_transparent);
				btnShowRight.setBackgroundResource(R.drawable.btn_switch_green);
			} else {
				ToastUtil.showToast(mContext, "底图加载完成后才能打开菜单，请稍后重试");
			}
		} else if (view.getId() == R.id.btnShowUser) {
			if (billView.loaded()) {
				showUserAnswer();
				btnShowUser.setBackgroundResource(R.drawable.btn_switch_green);
				btnShowRight.setBackgroundResource(R.drawable.btn_transparent);
			} else {
				ToastUtil.showToast(mContext, "底图加载完成后才能打开菜单，请稍后重试");
			}
		}
	}

	/**
	 * 盖章操作
	 * 
	 * @param signData
	 */
	public void sign(SignData signData) {
		if (slidingLayout.isBillContract()) {
			ToastUtil.showToast(mContext, "请关闭图片菜单后再进行盖章操作");
			return;
		}
		SignInfo signInfo = new SignInfo();
		signInfo.setId(signData.getId());
		signInfo.setBitmap(signData.getPic());
		boolean result = billView.addSignView(signInfo);
		if (!result) {
			// 添加失败处理
		}
	}

	/**
	 * 显示闪电符
	 */
	public void showFlashes() {
		billView.showFlashes();
	}

	/**
	 * 显示用户答案
	 */
	public void showUserAnswer() {
		billView.showUserAnswer();
	}

	/**
	 * 将数据应用到该视图
	 * @param data
	 */
	public void applyData(BaseTestData data) {
		billView.applyData(mData);
		if (isGroup) {
			ibtnPic.setVisibility(View.GONE);
		} else {
			ibtnPic.setOnClickListener(this);
			picBrowseView = (PicBrowseView) findViewById(R.id.picBrowseView);
			picBrowseView.setOnCloseListener(this);
			//附件初始化
			List<String> pics = mData.getSubjectData().getQuestion().getImgSrc();
			if (picBrowseView.setResources(mData.getSubjectData().getQuestion().getText(), pics)) {// 是否显示图片的小角标
				ibtnPic.setVisibility(View.VISIBLE);
			} else {
				ibtnPic.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void saveAnswer() {
		if (inited) {
			billView.saveAnswer();
		}
	}

	@Override
	public float submit() {
		if (inited) {
			if (mData.getTestMode() == TestMode.MODE_PRACTICE) {
				showSwitch(true);
			}
			return billView.submit();
		} else {
			return mData.getuScore();
		}
	}

	@Override
	public void showDetails() {
		if (inited) {
			billView.showDetails();
		}
	}

	@Override
	public void reset() {
		if (inited) {
			showSwitch(false);
			billView.reset();
		}
	}

	@Override
	public void setSubjectListener(SubjectListener listener) {
		mListener = listener;
	}

	/**
	 * 获取默认焦点
	 */
	public void requestDefaultFocus() {
		billView.requestDefaultFocus();
	}

	@Override
	public void onZoomInit(boolean zoomInEnable, boolean zoomOutEnable) {
		btnZoomIn.setEnabled(zoomInEnable);
		btnZoomOut.setEnabled(zoomOutEnable);
	}

	@Override
	public void onZoomInStart(int currentScaleTimes) {
	}

	@Override
	public void onZoomInEnd(int currentScaleTimes, boolean zoomInEnable, boolean zoomOutEnable) {
		btnZoomIn.setEnabled(zoomInEnable);
		btnZoomOut.setEnabled(zoomOutEnable);
	}

	@Override
	public void onZoomOutStart(int currentScaleTimes) {
	}

	@Override
	public void onZoomOutEnd(int currentScaleTimes, boolean zoomInEnable, boolean zoomOutEnable) {
		btnZoomIn.setEnabled(zoomInEnable);
		btnZoomOut.setEnabled(zoomOutEnable);
	}

	@Override
	public void onDragStart(SignView view) {
		// ToastUtil.showToast(mContext, "开始盖章了");
	}

	@Override
	public void onDragEnd(SignView view) {
		// ToastUtil.showToast(mContext, "盖章结束了");
		SoundPoolUtil.getInstance().play(SoundPoolUtil.SOUND_SEAL_SUCCESS_ID);
	}

	@Override
	public void onDragHint(SignView view, String msg) {
		ToastUtil.showToast(mContext, msg);
	}

	/**
	 * 调用单据控件的measure方法
	 */
	public void measureBill(int width, int height) {
		billView.measure(width, height);
	}

	@Override
	public void onClose() {
		slidingLayout.closeMenu();
	}
}
