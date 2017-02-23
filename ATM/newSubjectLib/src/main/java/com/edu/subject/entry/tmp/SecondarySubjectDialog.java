package com.edu.subject.entry.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.subject.R;

/**
 * 二级科目选择对话框
 * 
 * @author lucher
 * 
 */
public class SecondarySubjectDialog extends BaseDialog implements OnItemClickListener {

	/**
	 * 科目列表
	 */
	private ListView lvSubjects;
	/**
	 * lvSubjects使用的adapter
	 */
	private EntrySubjectAdapter adapter;

	/**
	 * 科目数据保存
	 */
	private List<EntrySubjectData> datas;

	/**
	 * 保存entryItem点击监听
	 */
	private HashMap<Integer, OnSItemClickListener> listeners;

	/**
	 * 当前对应的entryid
	 */
	private int currentEntryId;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param pId
	 *            一级科目id
	 */
	public SecondarySubjectDialog(Context context) {
		super(context);

		setContentView(R.layout.dialog_secondary_subject);

		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		lvSubjects = (ListView) this.findViewById(R.id.lv_subjects);
		adapter = new EntrySubjectAdapter(mContext, null);
		lvSubjects.setAdapter(adapter);
		lvSubjects.setOnItemClickListener(this);

		listeners = new HashMap<Integer, OnSItemClickListener>();
	}

	/**
	 * 设置item点击监听
	 * 
	 * @param item的id
	 * @param listener
	 */
	public void setOnSItemClickListener(int id, OnSItemClickListener listener) {
		listeners.put(id, listener);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		dismiss();
		OnSItemClickListener listener = listeners.get(currentEntryId);
		if (listener != null) {
			listener.onSItemClick(view.getId(), ((TextView) view).getText().toString());
		}
	}

	/**
	 * 显示
	 * 
	 * @param id
	 *            对应的entryid
	 * @param 对应一级科目id
	 */
	public void show(int entryId, int pId) {
		super.show();
		currentEntryId = entryId;
		refreshList(pId);
	}

	/**
	 * 刷新list
	 * @param pId 对应一级科目id
	 */
	private void refreshList(int pId) {
		datas = EntrySubjectDataDao.getInstance(mContext).getDatasByParentId(pId);
		if (datas == null || datas.size() == 0) {
			datas = new ArrayList<EntrySubjectData>();
			EntrySubjectData data = new EntrySubjectData();
			data.setId(-1);
			data.setName("空");
			datas.add(data);
		}
		adapter.setDatas(datas);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 科目选中监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface OnSItemClickListener {
		/**
		 * 科目点击回调
		 * 
		 * @param id
		 *            对应科目数据库id
		 * @param text
		 *            对应文本
		 */
		void onSItemClick(int id, String text);
	}
}
