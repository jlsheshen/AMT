package com.edu.subject.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.edu.R;
import com.edu.subject.SubjectType;
import com.edu.subject.common.SubjectCardAdapter.OnCardItemClickListener;
import com.edu.subject.data.BaseTestData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 答题卡对话框
 * 
 * @author lucher
 * 
 */
public class SubjectCardDialog extends Dialog implements View.OnClickListener {

	private Context mContext;
	// 题目数据
	private List<BaseTestData> mDatas;
	// 把各种题型分类
	private HashMap<Integer, List<BaseTestData>> mSubjectMap;

	// 全部重做
	private Button btnReDo;
	private LinearLayout cardContent;
	private OnCardItemClickListener mListener;
	// 所有题型表格
	private List<CardGridItem> mGrids;

	public SubjectCardDialog(Context context, List<BaseTestData> datas, OnCardItemClickListener listener, int currentId) {
		super(context);

		this.mDatas = datas;
		mContext = context;
		mListener = listener;

		init();
		refreshState(currentId);
	}

	/**
	 * 初始化
	 */
	private void init() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_subject_card);
		// 窗口全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 设置窗口弹出动画
		getWindow().setWindowAnimations(R.style.TranAnimation);
		// 设置对话框的位置
		getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);

		btnReDo = (Button) findViewById(R.id.btnReDo);
		cardContent = (LinearLayout) findViewById(R.id.cardContent);
		btnReDo.setOnClickListener(this);

		mGrids = new ArrayList<CardGridItem>(8);
		// 题型分组
		mSubjectMap = new LinkedHashMap<Integer, List<BaseTestData>>(8);
		for (BaseTestData data : mDatas) {
			int type = data.getSubjectType();
			if(type == SubjectType.SUBJECT_GROUP_BILL) {
				type = SubjectType.SUBJECT_BILL;
			}
			if (mSubjectMap.containsKey(type)) {
				mSubjectMap.get(type).add(data);
			} else {
				List<BaseTestData> list = new ArrayList<BaseTestData>(1);
				list.add(data);
				mSubjectMap.put(type, list);
			}
		}
	}

	/**
	 * 显示全部重做按钮
	 */
	public void showRedo() {
		btnReDo.setVisibility(View.VISIBLE);
	}

	/**
	 * 刷新状态
	 * 
	 * @param currentId
	 */
	private void refreshState(int currentId) {
		cardContent.removeAllViews();
		Iterator<Integer> iterator = mSubjectMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer type = iterator.next();
			List<BaseTestData> datas = mSubjectMap.get(type);
			cardContent.addView(createGrid(type, datas, currentId));
		}
	}

	/**
	 * 创建题型对应表格
	 * 
	 * @param type
	 * @param datas
	 * @param currentId
	 * @return
	 */
	private View createGrid(int type, List<BaseTestData> datas, int currentId) {
		CardGridItem item = new CardGridItem(mContext, datas, mListener);
		mGrids.add(item);
		return item.apply(type, currentId);
	}

	@Override
	public void onClick(View v) {
		if (mListener != null) {
			mListener.onRedoClicked();
		}
	}

	/**
	 * 显示
	 * 
	 * @param currentId
	 */
	public void show(int currentId) {
		super.show();
		for (CardGridItem grid : mGrids) {
			grid.refresh(currentId);
		}
	}
}
