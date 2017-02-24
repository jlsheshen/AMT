package com.edu.subject.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.edu.library.util.ToastUtil;
import com.edu.subject.R;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.data.SubjectEntryData;
import com.edu.subject.data.TestEntryData;
import com.edu.subject.data.answer.EntryAnswerData;
import com.edu.subject.entry.EntryAnswerHandler;
import com.edu.subject.entry.view.EntryEditText;
import com.edu.subject.entry.view.EntryItemView;
import com.edu.subject.entry.view.EntryItemView.EntryItemListener;
import com.edu.subject.entry.view.GroupEntryItemData;
import com.edu.subject.entry.view.GroupEntryItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 分录题型控件
 * @author lucher
 *
 */
public class EntryView extends BasicSubjectView implements OnClickListener, EntryItemListener {

	private static final String TAG = "EntryView";

	/**
	 * 支持分录最大组数
	 */
	public static int MAX_GROUP_COUNT = 10;
	//添加,移除按钮
	private Button btnAdd, btnRemove;

	//借贷视图容器
	private LinearLayout entryContainer;
	//借贷分录组id
	private int groupId;
	//分录题答案判分处理类
	private EntryAnswerHandler mAnswerHandler;

	public EntryView(Context context, TestEntryData data) {
		super(context, data, "分录题");
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mAnswerHandler = new EntryAnswerHandler();
	}

	@Override
	public void saveAnswer() {
		if (inited) {
			//获取每个组里每个条目的答案
			int groupCount = entryContainer.getChildCount();
			EntryAnswerData entryAnswer = new EntryAnswerData();
			List<GroupEntryItemData> groupAnswer = new ArrayList<GroupEntryItemData>(groupCount);
			entryAnswer.setAnswers(groupAnswer);
			for (int i = 0; i < groupCount; i++) {
				GroupEntryItemView groupEntry = (GroupEntryItemView) entryContainer.getChildAt(i);
				GroupEntryItemData entryGroupAnswer = groupEntry.getUserAnswer();
				entryGroupAnswer.setOrder(i);
				groupAnswer.add(entryGroupAnswer);
			}
			((TestEntryData) mTestData).setUAnswerData(entryAnswer);
			Log.d(TAG, "uanswer:" + mTestData.getUAnswer());
		}
	}

	@Override
	protected void initBody(RelativeLayout layoutContent) {
		//组件初始化
		View view = View.inflate(mContext, R.layout.view_subject_entry, null);
		entryContainer = (LinearLayout) view.findViewById(R.id.entryContainer);
		btnAdd = (Button) view.findViewById(R.id.btnAdd);
		btnRemove = (Button) view.findViewById(R.id.btnRemove);
		btnAdd.setOnClickListener(this);
		btnRemove.setOnClickListener(this);

		initDefaultEntry();
		layoutContent.addView(view);
	}

	/**
	 * 初始化默认的分录
	 */
	private void initDefaultEntry() {
		clearEntry();
		addGroupEntry();
	}

	/**
	 * 清空分录内容
	 */
	private void clearEntry() {
		groupId = 0;
		entryContainer.removeAllViews();
	}

	@Override
	public void disableSubject() {
		if (inited) {
			setEnabled(false);
		}
	}

	@Override
	public void initUAnswer(boolean judge) {
		if (inited) {
			EntryAnswerData answer = ((TestEntryData) mTestData).getUAnswerData();
			if (answer != null) {
				clearEntry();
				List<GroupEntryItemData> groupDatas = answer.getAnswers();
				for (GroupEntryItemData groupItemData : groupDatas) {
					GroupEntryItemView groupEntry = new GroupEntryItemView(getContext(), groupId, this);
					groupEntry.initUAnswer(groupItemData, judge);
					addGroupEntry(groupEntry);
				}
			}
		}
	}

	@Override
	protected void judgeAnswer() {
		mAnswerHandler.judgeAnswer((TestEntryData) mTestData);
		//是否需要判断是否答对，答对或者答错对应不同的状态
		boolean judge = mTestMode == TestMode.MODE_PRACTICE;
		if (judge) {
			refreshUserAnswer(judge);
		}
		if (mTestData.getuScore() == mTestData.getSubjectData().getScore()) {
			mTestData.setState(SubjectState.STATE_CORRECT);
		} else {
			mTestData.setState(SubjectState.STATE_WRONG);
		}
	}

	/**
	 * 刷新用户答案
	 * @param judge
	 */
	private void refreshUserAnswer(boolean judge) {
		//刷新用户答案状态
		EntryAnswerData answerData = ((TestEntryData) mTestData).getUAnswerData();
		if (answerData != null) {
			int groupCount = answerData.getAnswers().size();
			for (int i = 0; i < groupCount; i++) {
				GroupEntryItemView groupEntry = (GroupEntryItemView) entryContainer.getChildAt(i);
				groupEntry.initUAnswer(answerData.getAnswers().get(i), judge);
			}
		}
	}

	/**
	 * 刷新正确答案
	 */
	protected void refreshAnswer() {
		String answer = "空";

		List<GroupEntryItemData> groups = ((SubjectEntryData) mTestData.getSubjectData()).getEntryBody().getGroups();
		if (groups != null) {
			answer = mAnswerHandler.getFormatedAnswer(groups);
		}
		tvAnswer.setText(getJudgeResult() + ",正确答案是\n" + answer);
	}

	@Override
	public void reset() {
		super.reset();
		if (inited) {
			initDefaultEntry();
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		//所有分录组设置是否可用
		int groupCount = entryContainer.getChildCount();
		for (int i = 0; i < groupCount; i++) {
			GroupEntryItemView groupEntry = (GroupEntryItemView) entryContainer.getChildAt(i);
			groupEntry.setEnabled(enabled);
		}
		//增加，移除按钮设置是否可用
		if (enabled) {
			refreshButtonState();
		} else {
			btnAdd.setVisibility(View.GONE);
			btnRemove.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnAdd) {
			addGroupEntry();
		} else if (v.getId() == R.id.btnRemove) {
			removeGroupEntry();
		}
	}

	/**
	 * 加入一组新的分录
	 */
	private void addGroupEntry() {
		GroupEntryItemView groupEntry = new GroupEntryItemView(mContext, groupId, this);
		addGroupEntry(groupEntry);
	}

	/**
	 * 加入一组指定分录
	 * @param groupEntry
	 */
	private void addGroupEntry(GroupEntryItemView groupEntry) {
		entryContainer.addView(groupEntry);
		groupId++;
		entryContainer.setId(groupId);
		refreshButtonState();
	}

	/**
	 * 移除一组分录，从最后一组开始移除
	 */
	private void removeGroupEntry() {
		entryContainer.removeViewAt(entryContainer.getChildCount() - 1);
		groupId--;
		refreshButtonState();
	}

	/**
	 * 刷新增加和减少按钮状态，如果当前只有一组借贷时不能减少,大于或等于MAX_GROUP_COUNT组借贷时不能添加
	 */
	private void refreshButtonState() {
		if (entryContainer.getChildCount() <= 1) {
			btnRemove.setVisibility(View.GONE);
		} else {
			btnRemove.setVisibility(View.VISIBLE);
		}
		if (entryContainer.getChildCount() >= MAX_GROUP_COUNT) {
			btnAdd.setVisibility(View.GONE);
		} else {
			btnAdd.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemAdd(int type, EntryItemView entryItem) {
		GroupEntryItemView groupEntry = (GroupEntryItemView) entryItem.getParent().getParent().getParent();
		groupEntry.addEntryItem(type, false);
	}

	@Override
	public void onItemRemove(int type, EntryItemView entryItem) {
		GroupEntryItemView groupEntry = (GroupEntryItemView) entryItem.getParent().getParent().getParent();
		groupEntry.removeEntryItem(type, entryItem);
	}

	@Override
	public void onPSubjectClicked(EntryEditText editText) {
		ToastUtil.showToast(mContext, editText.getText().toString());
	}

	@Override
	public void onSSubjectClicked(EntryEditText editText) {
		ToastUtil.showToast(mContext, editText.getText().toString());
	}

}
