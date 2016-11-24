package com.edu.subject.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.edu.R;
import com.edu.library.util.ToastUtil;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectListener;
import com.edu.subject.SubjectState;
import com.edu.subject.common.PicBrowseView;
import com.edu.subject.common.PicBrowseView.CloseListener;
import com.edu.subject.common.SlidingDragLayout;
import com.edu.subject.common.SlidingDragLayout.DragListener;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SignData;
import com.edu.subject.data.TestBillData;
import com.edu.subject.data.TestGroupBillData;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组单据题型视图
 * 
 * @author lucher
 * 
 */
public class GroupBillView extends RelativeLayout implements ISubject, OnCheckedChangeListener, OnClickListener, CloseListener {

	// 缩放按钮
	private Context mContext;
	private List<BillView> billViews;
	// 单据相关数据
	private TestGroupBillData mData;

	// 当前单据索引
	private int mCurrentIndex;
	// 标签容器
	private RadioGroup tabs;
	// 单据容器
	private RelativeLayout billContent;

	// 显示图片的小角标
	private ImageButton ibtnPic;
	// 滑动菜单控件
	private SlidingDragLayout slidingLayout;
	// 图片查看控件
	private PicBrowseView picBrowseView;
	// 是否初始化
	private boolean inited;
	private SubjectListener mListener;

	public GroupBillView(Context context, TestGroupBillData data) {
		super(context);

		View.inflate(context, R.layout.loading_layout, this);
		mContext = context;
		mData = data;
	}

	/**
	 * 用于延迟加载，当界面显示时再加载
	 */
	public void onVisible() {
		if (!inited) {
			removeAllViews();
			View.inflate(mContext, R.layout.layout_group_bill_view, this);
			init();
		}
		requestDefaultFocus();
		inited = true;
	}

	/**
	 * 初始化
	 */
	private void init() {
		tabs = (RadioGroup) findViewById(R.id.tabs);
		tabs.setOnCheckedChangeListener(this);
		billContent = (RelativeLayout) findViewById(R.id.billContent);
		billViews = new ArrayList<BillView>(mData.getTestDatas().size());
		ibtnPic = (ImageButton) findViewById(R.id.ibtnPic);
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
		applyData(mData);

		initContent();
	}

	/**
	 * 检测单据内容是否出界
	 */
	protected void checkScrollBorder() {
		billViews.get(mCurrentIndex).checkScrollBorder();
	}

	/**
	 * 初始化单据控件
	 */
	private void initContent() {
		for (int i = 0; i < mData.getTestDatas().size(); i++) {
			// 初始化标签
			RadioButton radio = (RadioButton) View.inflate(mContext, R.layout.radio_label, null);
			TestBillData data = mData.getTestDatas().get(i);
			radio.setId(i);
			radio.setText(data.getSubjectData().getLabel());
			tabs.addView(radio);
			// 初始化单据
			BillView billView = new BillView(mContext, data);
			billView.onVisible();
			billView.setGroup();
			billView.applyData(mData.getTestDatas().get(i));
			billViews.add(billView);
			// billView.measureBill(billContent.getWidth(),
			// billContent.getHeight());
		}
		// 手动调用单据的测量方法，让没有显示的单据也进行测量
		billContent.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				billContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				for (BillView billView : billViews) {
					billView.measureBill(billContent.getWidth(), billContent.getHeight());
				}
			}
		});

		((RadioButton) tabs.getChildAt(0)).setChecked(true);
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
		billViews.get(mCurrentIndex).sign(signData);
	}

	/**
	 * 显示闪电符
	 */
	public void showFlashes() {
		billViews.get(mCurrentIndex).showFlashes();
	}

	/**
	 * 显示用户答案
	 */
	public void showUserAnswer() {
		billViews.get(mCurrentIndex).showUserAnswer();
	}

	@Override
	public void applyData(BaseTestData data) {
		if (mData.getSubjectData().getPic() == null || mData.getSubjectData().getPic().equals("")) {// 是否显示图片的小角标
			ibtnPic.setVisibility(View.GONE);
		} else {
			ibtnPic.setVisibility(View.VISIBLE);
		}
		ibtnPic.setOnClickListener(this);
		picBrowseView = (PicBrowseView) findViewById(R.id.picBrowseView);
		picBrowseView = (PicBrowseView) findViewById(R.id.picBrowseView);
		picBrowseView.setOnCloseListener(this);
		String pic = mData.getSubjectData().getPic();
		if (pic != null) {
			String[] pics = pic.split(SubjectConstant.SEPARATOR_ITEM);
			picBrowseView.setResources(pics);
		}
	}

	@Override
	public void saveAnswer() {
		judge(false);
	}

	@Override
	public void showDetails() {
		billViews.get(mCurrentIndex).showDetails();
	}

	@Override
	public float submit() {
		judge(true);
		return mData.getuScore();
	}


	/**
	 * 判断答案等操作
	 * 
	 * @param submit
	 *            是否提交
	 */
	private void judge(boolean submit) {
		float totalScore = mData.getScore();// 总的得分，对单据进行算分之前把当前总分设置到单据中去进行计算，最后一张单据计算完便是总得分
		StringBuilder answerBuilder = new StringBuilder();
		StringBuilder signBuilder = new StringBuilder();
		int index = 0;
		// 拼接答案，累加分数
		for (int i = 0; i < billViews.size(); i++) {
			BillView billView = billViews.get(i);
			mData.getTestDatas().get(i).getSubjectData().setScore(totalScore);
			// 进行提交或保存操作
			if (submit) {
				billView.submit();
			} else {
				billView.saveAnswer();
			}
			// 用户答案印章拼接保存
			String uAnswer = billView.getTestData().getuAnswer();
			String uSign = billView.getTestData().getuSigns();
			if (uAnswer != null && uAnswer.equals("")) {
				uAnswer = SubjectConstant.FLAG_NULL_STRING;
			}
			if (uSign == null || uSign.equals("")) {
				uSign = SubjectConstant.FLAG_NULL_STRING;
			}
			if (index == 0) {
				answerBuilder.append(uAnswer);
				signBuilder.append(uSign);
			} else {
				answerBuilder.append(SubjectConstant.SEPARATOR_GROUP + uAnswer);
				signBuilder.append(SubjectConstant.SEPARATOR_GROUP + uSign);
			}
			totalScore = billView.getTestData().getuScore();
			index++;
			// 状态修改
			if (submit) {
				// 判断正误，在此之前需要把对应的题得分设置到data中
				if (mData.getTestDatas().get(i).getSubjectData().getScore() == mData.getTestDatas().get(i).getuScore()) {
					mData.getTestDatas().get(i).setState(SubjectState.STATE_CORRECT);
				} else {
					mData.getTestDatas().get(i).setState(SubjectState.STATE_WRONG);
				}
			} else {
				if (billView.getTestData().getState() == SubjectState.STATE_INIT) {
					billView.getTestData().setState(SubjectState.STATE_UNFINISH);
				}
			}

		}
		mData.setuAnswer(answerBuilder.toString());
		mData.setuSigns(signBuilder.toString());
		mData.setuScore(totalScore);
	}

	@Override
	public void reset() {
		for (int i = 0; i < billViews.size(); i++) {
			BillView billView = billViews.get(i);
			billView.reset();
		}
	}

	@Override
	public void setSubjectListener(SubjectListener listener) {
		mListener = listener;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, final int checkedId) {
		mCurrentIndex = checkedId;
		// billContent.bringChildToFront(billViews.get(checkedId));
		billContent.removeAllViews();
		billContent.addView(billViews.get(checkedId));
		if (!slidingLayout.isBillContract()) {
			// 手动调用单据的测量方法，让没有显示的单据也进行测量
			billViews.get(checkedId).getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					billViews.get(checkedId).getViewTreeObserver().removeGlobalOnLayoutListener(this);
					billViews.get(checkedId).checkScrollBorder();
				}
			});
		}
		billViews.get(checkedId).requestDefaultFocus();
	}

	/**
	 * 获取默认焦点
	 */
	public void requestDefaultFocus() {
		billViews.get(mCurrentIndex).requestDefaultFocus();
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.ibtnPic) {
			slidingLayout.openMenu();
		}
	}

	@Override
	public void onClose() {
		slidingLayout.closeMenu();
	}
}
