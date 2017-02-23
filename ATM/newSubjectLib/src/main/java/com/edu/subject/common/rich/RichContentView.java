package com.edu.subject.common.rich;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.edu.library.picgrid.DownloadPicAdapter;
import com.edu.library.picgrid.PicBrowseActivity;
import com.edu.library.picgrid.TaskPicData;
import com.edu.library.view.FixedGridView;
import com.edu.subject.R;

/**
 * 富文本容器视图
 * @author lucher
 *
 */
public class RichContentView extends LinearLayout {

	//富文本控件，支持html文本以及base64图片
	private RichTextView richText;
	// 图片表格
	private FixedGridView imgGrid;
	private DownloadPicAdapter downloadAdapter;

	private Context mContext;

	public RichContentView(Context context) {
		this(context, null);
	}

	public RichContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		setOrientation(LinearLayout.VERTICAL);
		//富文本控件
		richText = new RichTextView(mContext);
		addView(richText);
		//图片表格
		imgGrid = (FixedGridView) View.inflate(mContext, R.layout.rich_grid_view, null);
		addView(imgGrid);
		imgGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TaskPicData pic = (TaskPicData) view.getTag();
				List<TaskPicData> datas = downloadAdapter.getPicDatas();
				browsePic(datas.indexOf(pic), datas);
			}
		});
	}

	/**
	 * 查看大图
	 * 
	 * @param index
	 * @param datas
	 */
	private void browsePic(int index, List<TaskPicData> datas) {
		Bundle bundle = new Bundle();
		bundle.putInt("index", index);
		bundle.putSerializable("datas", (Serializable) datas);
		Intent intent = new Intent(mContext, PicBrowseActivity.class);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}

	/**
	 * 设置富文本数据，格式必须为{@link RichTextData}的json字符串
	 * @param text
	 */
	public void setRichData(String text) {
		try {
			RichTextData subject = JSON.parseObject(text, RichTextData.class);
			setRichData(subject);
		} catch (Exception e) {
			e.printStackTrace();
			richText.setText(text);
		}
	}

	/**
	 * 设置富文本数据
	 * @param data
	 */
	public void setRichData(RichTextData data) {
		if(TextUtils.isEmpty(data.getText())) {
			richText.setVisibility(View.GONE);
		} else {
			richText.setVisibility(View.VISIBLE);
			richText.setRichText(data.getText());
		}
		
		//图片表格
		List<String> imgs = data.getImgSrc();
		if (imgs != null) {
			List<TaskPicData> datas = new ArrayList<TaskPicData>(imgs.size());
			for (String img : imgs) {
				datas.add(new TaskPicData(TaskPicData.TASK_TYPE_DOWNLOAD, null, img));
			}
			downloadAdapter = new DownloadPicAdapter(mContext);
			downloadAdapter.setDatas(datas);
			imgGrid.setAdapter(downloadAdapter);
			imgGrid.setVisibility(View.VISIBLE);
		} else {
			imgGrid.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置普通文本内容
	 * @param text
	 */
	public void setText(String text) {
		Log.d("lucher", "setText:" + text);
		richText.setText(text);
	}

	/**
	 * 设置字体大小
	 * @param size
	 */
	public void setTextSize(float size) {
		richText.setTextSize(size);
	}

	/**
	 * 设置字体大小
	 * @param unit
	 * @param size
	 */
	public void setTextSize(int unit, float size) {
		richText.setTextSize(unit, size);
	}

	/**
	 * 设置字体颜色
	 * @param color
	 */
	public void setTextColor(int color) {
		richText.setTextColor(color);
	}

	/**
	 * 获取富文本视图控件
	 * @return
	 */
	public RichTextView getRichTextView() {
		return richText;
	}
}
