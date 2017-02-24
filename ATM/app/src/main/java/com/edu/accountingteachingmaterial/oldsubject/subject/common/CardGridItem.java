package com.edu.accountingteachingmaterial.oldsubject.subject.common;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.edu.R;
import com.edu.subject.SubjectType;
import com.edu.subject.common.CardGridView;
import com.edu.subject.common.SubjectCardAdapter.OnCardItemClickListener;
import com.edu.subject.data.BaseTestData;

import java.util.List;

/**
 * 答题卡中具体题型对应的表格
 * 
 * @author lucher
 * 
 */
public class CardGridItem {

	// 对应的视图
	private View mView;
	// 题型标签
	private TextView tvTitle;
	// 对应的表格
	private com.edu.subject.common.CardGridView cardGrid;

	private OnCardItemClickListener mListener;
	private List<BaseTestData> mDatas;

	public CardGridItem(Context context, List<BaseTestData> datas, OnCardItemClickListener listener) {
		mView = View.inflate(context, R.layout.card_grid_item, null);
		mDatas = datas;
		mListener = listener;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		tvTitle = (TextView) mView.findViewById(R.id.tvTitle);
		cardGrid = (CardGridView) mView.findViewById(R.id.cardGrid);
	}

	/**
	 * 将数据应用到该item上
	 * 
	 * @param type
	 * @param datas
	 * @param listener
	 * @param currentId
	 * @return
	 */
	public View apply(int type, int currentId) {
		switch (type) {
		case SubjectType.SUBJECT_BILL:
			tvTitle.setText("单据题");

			break;
		case SubjectType.SUBJECT_BLANK:
			tvTitle.setText("填空题");

			break;
		case SubjectType.SUBJECT_COMPLEX_ENTRY:
			tvTitle.setText("混合式分录题");

			break;
		case SubjectType.SUBJECT_ENTRY:
			tvTitle.setText("分录题");

			break;
		case SubjectType.SUBJECT_JUDGE:
			tvTitle.setText("判断题");

			break;
		case SubjectType.SUBJECT_MULTI:
			tvTitle.setText("多选题");

			break;
		case SubjectType.SUBJECT_SHORT_ANSWER:
			tvTitle.setText("简答题");

			break;
		case SubjectType.SUBJECT_SINGLE:
			tvTitle.setText("单选题");

			break;

		default:
			tvTitle.setText("其他题型");
			break;
		}
		refresh(currentId);

		return mView;
	}

	/**
	 * 刷新答题卡状态
	 * 
	 * @param currentId
	 */
	public void refresh(int currentId) {
		SubjectCardAdapter adapter = new SubjectCardAdapter(mView.getContext(), mDatas, currentId);
		cardGrid.setAdapter(adapter);
		adapter.setCardItemClickListener(mListener);
	}

}
