package com.edu.subject.common;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.R;
import com.edu.library.R.color;
import com.edu.subject.SubjectState;
import com.edu.subject.TestMode;
import com.edu.subject.data.BaseTestData;

import java.util.List;

/**
 * 通用答题卡对话框
 * 
 * @author lucher
 * 
 */
public class SubjectCardAdapter extends BaseAdapter implements OnClickListener {

	private static final String TAG = "SubjectCardAdapter";
	private Context mContext;
	// 对应的数据
	private List<BaseTestData> mDatas;

	/**
	 * item点击监听
	 */
	private OnCardItemClickListener mListener;
	// 当前题id
	private int mCurrentId;

	public SubjectCardAdapter(Context context, List<BaseTestData> datas, int currentId) {
		mContext = context;
		mDatas = datas;
		mCurrentId = currentId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) View.inflate(mContext, R.layout.item_grid, null);
		view.setTag(mDatas.get(position));
		view.setOnClickListener(this);
		refreshState(position, view);

		return view;
	}

	/**
	 * 刷新题卡状态
	 * 
	 * @param position
	 * @param view
	 */
	private void refreshState(int position, TextView view) {
		BaseTestData testData = mDatas.get(position);
		view.setText(String.valueOf(testData.getSubjectIndex()));
		// 设置不同状态不同模式下item的背景色
		int backgroud = color.gray;

		if (mCurrentId == testData.getId()) {// 当前题
			backgroud = color.purple;
		} else {
			if (testData.getState() == SubjectState.STATE_INIT) {
				if (testData.getTestMode() == TestMode.MODE_SHOW_DETAILS) {// 测试模式不用区分正确或者错误
					backgroud = color.red;
				} else {
					backgroud = color.gray;
				}
			} else if (testData.getState() == SubjectState.STATE_UNFINISH) {
				if (testData.getTestMode() == TestMode.MODE_SHOW_DETAILS) {// 测试模式不用区分正确或者错误
					backgroud = color.red;
				} else {
					backgroud = color.yellow;
				}
			} else if (testData.getState() == SubjectState.STATE_CORRECT) {
				if (testData.getTestMode() == TestMode.MODE_EXAM) {// 测试模式不用区分正确或者错误
					backgroud = color.yellow;
				} else {
					backgroud = color.blue;
				}
			} else if (testData.getState() == SubjectState.STATE_WRONG) {
				if (testData.getTestMode() == TestMode.MODE_EXAM) {// 测试模式不用区分正确或者错误
					backgroud = color.yellow;
				} else {
					backgroud = color.red;
				}
			}
		}
		view.setBackgroundResource(backgroud);
	}

	@Override
	public void onClick(View v) {
		if (mListener != null) {
			mCurrentId = mListener.onItemClicked((BaseTestData) v.getTag());
			notifyDataSetChanged();
		}
	}

	/**
	 * 设置答题卡的item点击事件
	 * 
	 * @param listener
	 */
	public void setCardItemClickListener(OnCardItemClickListener listener) {
		mListener = listener;
	}

	/**
	 * item点击事件
	 * 
	 * @author lucher
	 * 
	 */
	public interface OnCardItemClickListener {
		/**
		 * item被点击
		 * 
		 * @param data
		 * @return 当前题目的题号
		 */
		int onItemClicked(BaseTestData data);

		/**
		 * 全部重做按钮点击
		 */
		void onRedoClicked();
	}
}
