package com.edu.accountingteachingmaterial.view.dialog;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.TaskSubmitAdapter;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;

import java.util.List;


/**
 * 加入小组的dialog
 */
public class TaskSubmitHistoryDialog extends BaseDialog {

	public static TaskSubmitHistoryDialog intance;
	TaskSubmitAdapter adapter;


	// 标题
	private TextView tvTitle;

	//数据
	private List<TaskDetailBean.HistoryBean> datas;

	RecyclerView recyclerView;
	/**
	 * 构造方法
	 *
	 * @param context
	 *
	 */
	public TaskSubmitHistoryDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_task_submit_history);
		init();
	}

	public TaskSubmitHistoryDialog setDatas(List<TaskDetailBean.HistoryBean> datas) {
		this.datas = datas;
		adapter.setDatas(datas);
		return this;
	}

	public void setTitle(String title) {
		tvTitle.setText(title);

	}



	/**
	 * 初始化
	 */
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_text);
		recyclerView = (RecyclerView) findViewById(R.id.task_submit_list);
		adapter = new TaskSubmitAdapter(getContext());
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


	}

		
}



