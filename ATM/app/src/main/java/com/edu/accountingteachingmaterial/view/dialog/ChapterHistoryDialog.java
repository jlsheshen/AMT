package com.edu.accountingteachingmaterial.view.dialog;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.HistoryPpwAdapter;
import com.edu.accountingteachingmaterial.bean.TaskDetailBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.HistoryListData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.net.SendJsonNetReqManager;
import com.lucher.net.req.RequestMethod;

import java.util.ArrayList;
import java.util.List;


/**
 * 显示历史记录dialog
 */
public class ChapterHistoryDialog extends BaseDialog implements AdapterView.OnItemClickListener {

	HistoryPpwAdapter ppwAdapter;
	ListView ppwList;
	OnDialogItemClick onDialogItemClick;


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
	public ChapterHistoryDialog(Context context) {
		super(context);
		setContentView(  R.layout.ppw_his);
		init();
	}

	public void setTitle(String title) {
		tvTitle.setText(title);

	}



	/**
	 * 初始化
	 */
	private void init() {
		ppwList = (ListView) findViewById(R.id.ppw_history_lv);
		tvTitle = (TextView) findViewById(R.id.tv_text);
		ppwList.setOnItemClickListener(this);
		ppwAdapter =  new HistoryPpwAdapter(mContext);
		loadHistoryDatas();

	}
	public void setOnDialogItemClick(OnDialogItemClick onDialogItemClick) {
		this.onDialogItemClick = onDialogItemClick;
	}

	private void loadHistoryDatas() {

		Log.d("LaunchActivity", NetUrlContstant.getFindHisUrl() + PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID));
		SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
		String url =  NetUrlContstant.getFindHisUrl() + PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.USER_ID) + "-" + PreferenceHelper.getInstance(mContext).getStringValue(PreferenceHelper.COURSE_ID);
		NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(mContext, RequestMethod.POST,url);
		sendJsonNetReqManager.sendRequest(netSendCodeEntity);
		sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				if (jsonObject.getString("success").equals("true")) {
					List<HistoryListData> hData = JSON.parseArray(jsonObject.getString("message"), HistoryListData.class);
					List<HistoryListData> tData, yData, aData;

					tData = new ArrayList<>();
					yData = new ArrayList<>();
					aData = new ArrayList<>();
					for (HistoryListData historyListData : hData) {
						historyListData.setUri(historyListData.getUri());
						switch (historyListData.getDate_diff()) {
							case ClassContstant.HISTORY_TODAY:
								tData.add(historyListData);
								break;
							case ClassContstant.HISTORY_YESTODAY:
								yData.add(historyListData);
								break;
							default:
								aData.add(historyListData);
								break;
						}
					}
					List<HistoryListData> datas = new ArrayList<HistoryListData>();
					if (tData.size() != 0) {
						datas.add(null);
						datas.addAll(tData);
					}
					if (yData.size() !=0 ) {
						datas.add(null);
						datas.addAll(yData);
					}
					if (aData.size() != 0) {
						datas.add(null);
						datas.addAll(aData);
					}
					ppwList.setAdapter(ppwAdapter);
					ppwAdapter.setDatas(datas);
					Log.d("LaunchActivity", "线程启动获取成功");
				}
			}

			@Override
			public void onFailure(String errorInfo) {
				Log.d("LaunchActivity", "线程启动获取失败" +errorInfo );

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		onDialogItemClick.onHistoryClick( position);
	}

	public interface OnDialogItemClick {
		void onHistoryClick(int position);

	}


		
}



