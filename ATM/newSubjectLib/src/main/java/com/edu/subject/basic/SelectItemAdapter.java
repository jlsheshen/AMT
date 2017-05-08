package com.edu.subject.basic;

import java.util.List;

import net.tsz.afinal.core.Arrays;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;

/**
 * 选择题型选项adapter
 * 
 * @author lucher
 * 
 */
public class SelectItemAdapter extends BaseAdapter implements OnClickListener {

	private static final String TAG = "SelectItemAdapter";

	/**
	 * 选项字母
	 */
	public static String[] mLetters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

	/**
	 * 选择题支持的最大选项个数
	 */
	public static int MAX_ITEM_COUNT = mLetters.length;

	// 选项数据
	private List<OptionData> mDatas;
	// 是否多选模式
	private boolean isMulti;
	// true-选择题，false-判断题
	private boolean isSelect;
	private Context mContext;

	private SelectListener mListener;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param datas
	 * @param select
	 *            true-选择题，false-判断题
	 */
	public SelectItemAdapter(Context context, List<OptionData> datas, boolean select) {
		mContext = context;
		mDatas = datas;
		isSelect = select;
		initOptions();
	}

	/**
	 * 初始化选项
	 */
	private void initOptions() {
		for (int index = 0; index < mDatas.size(); index++) {
			OptionData data = mDatas.get(index);
			if (isSelect) {// 选择题
				if (index > mLetters.length - 1) {
					Log.e(TAG, "不支持" + index + "位的选项");
				}
				data.setFlag(mLetters[index]);
			} else {// 判断题
				if (index == 0 || index == 1) {
					data.setFlag(data.getOption().getText());
				} else {
					Log.e(TAG, "不支持" + index + "位的选项");
				}
			}
		}
	}

	/**
	 * 设置是否多选
	 * 
	 * @param multi
	 */
	public SelectItemAdapter setMulti(boolean multi) {
		this.isMulti = multi;
		return this;
	}

	/**
	 * 设置选项点击监听
	 * 
	 * @param listener
	 */
	public void setSelectListener(SelectListener listener) {
		mListener = listener;
	}

	@Override
	public int getCount() {
		if (mDatas == null)
			return 0;
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		if (mDatas == null)
			return null;
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = new SelectItemView(mContext);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			convertView.setOnClickListener(this);
			viewHolder.selectItem = (SelectItemView) convertView;
			//此处修改.讲选项设置padding
			viewHolder.selectItem.setPadding(10,10,10,10);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.selectItem.attach(mDatas.get(position), isSelect, isMulti);

		return convertView;
	}

	/**
	 * 清除所有选中状态
	 */
	public void clearSelect() {
		for (OptionData data : mDatas) {
			data.setEnabled(true);
			data.setSelected(false);
		}
		notifyDataSetChanged();
	}

	/**
	 * 设置是否可用
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		for (OptionData data : mDatas) {
			data.setEnabled(enabled);
		}
		notifyDataSetChanged();
	}

	/**
	 * 获取用户选择的答案,保存选项id,多个id逗号隔开
	 * 
	 * @return
	 */
	public String getUAnswer() {
		StringBuilder builder = new StringBuilder();
		int index = 0;
		for (OptionData data : mDatas) {
			if (data.isSelected()) {
				if (index > 0) {
					builder.append("," + data.getOption().getId());
				} else {
					builder.append(data.getOption().getId());
				}
				index++;
			}
		}
		return builder.toString();
	}

	/**
	 * 设置用户答案
	 * 
	 * @param answer.
	 */
	public void setUAnswer(String answer) {
		for (OptionData data : mDatas) {
			if (answer != null && Arrays.asList(answer.split(",")).contains(String.valueOf(data.getOption().getId()))) {
				data.setSelected(true);
			} else {
				data.setSelected(false);
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * 获取答案对应的label
	 * @param answer
	 * @return
	 */
	public String getAnswerLabel(String answer) {
		StringBuilder builder = new StringBuilder();
		if (answer != null) {
			String[] answers = answer.split(",");
			int length = answers.length;
			for (int i = 0; i < length; i++) {
				for (OptionData data : mDatas) {
					if (answers[i].equals(String.valueOf(data.getOption().getId()))) {
						builder.append(data.getFlag());
					}
				}
			}
		}

		return builder.toString();
	}

	@Override
	public void onClick(View v) {
		if (!isMulti) {// 非多选题，需要清除其它选择状态
			clearSelect();
		}
		((SelectItemView) v).handleSelect();
		if (mListener != null) {
			mListener.onItemSelected(((SelectItemView) v).getOptionData());
		}
	}

	public class ViewHolder {
		public SelectItemView selectItem;
	}

	/**
	 * 选项点击监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface SelectListener {
		/**
		 * 选项点击
		 * 
		 * @param data
		 */
		void onItemSelected(OptionData data);
	}
}
