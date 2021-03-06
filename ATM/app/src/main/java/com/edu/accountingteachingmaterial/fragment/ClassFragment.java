package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.ClassDetailActivity;
import com.edu.accountingteachingmaterial.adapter.ClassChapterExLvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ClassChapterBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.util.ToastUtil;
import com.lucher.net.req.RequestMethod;

import java.util.List;

public class ClassFragment  extends BaseFragment{

	ExpandableListView expandableListView;
	List<ClassChapterBean> datas;
	ClassChapterExLvAdapter chapterExLvAdapter;
//	List<ChapterData> chapterData;
	@Override
	protected int initLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_class;
	}

	@Override
	protected void initView(View view) {
		expandableListView = (ExpandableListView) bindView(R.id.class_classchapter_exlv);

	}

	@Override
	protected void initData() {

//		loadData();
		chapterExLvAdapter = new ClassChapterExLvAdapter(context);
		uploadChapter();
		expandableListView.setAdapter(chapterExLvAdapter);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				String id1 = datas.get(groupPosition).getNodes().get(childPosition).getNodeId();
				Log.e("www", id1);
				startActivity(ClassDetailActivity.class);
				// TODO Auto-generated method stub
				return false;
			}
		});

		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = expandableListView .getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                    	expandableListView.collapseGroup(i);
                    }
                }
            }
        });
		}
//	private void loadData() {
//		uploadChapter();
//		datas = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			ClassChapterBean chapterBean = new ClassChapterBean();
//			List<NodeBean> nodes = new ArrayList<>();
//			chapterBean.setChapterId(i);
//			chapterBean.setChapterNum("第" + i + "章");
//			chapterBean.setTitle("章节标题" + i);
//			for (int j = 0; j < 10; j++) {
//				NodeBean node = new NodeBean();
//				node.setNodeNum("第" + j + "节");
//				node.setNodeId(i + "--" + j);
//				node.setTitle("小节编号" + node.getNodeId());
//				nodes.add(node);
//
//			}
//			chapterBean.setNodes(nodes);
//			datas.add(chapterBean);
//
//		}
//
//	}
	private void uploadChapter() {
		int courseId = PreferenceHelper.getInstance(context).getIntValue(PreferenceHelper.COURSE_ID);
		SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();

		NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.chapterUrl + courseId);
		sendJsonNetReqManager.sendRequest(entity);
		sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				if (jsonObject.getString("success").equals("true")) {
					List<ClassChapterData> chapterData = JSON.parseArray(jsonObject.getString("message"), ClassChapterData.class);
					Log.d("UnitTestActivity", "uploadChapter" + "success" + chapterData);
					chapterExLvAdapter.setDatas(chapterData);
				}
			}

			@Override
			public void onFailure(String errorInfo) {
				ToastUtil.showToast(context, errorInfo + "请联网哟");
			}
		});
	}


	}


