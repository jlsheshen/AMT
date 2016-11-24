package com.edu.library.picgrid;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.edu.library.R;
import com.edu.library.loadingimage.SquareLoadingImageView;
import com.edu.library.util.ImageUtil;

/**
 * 上传图片表格的adapter,包含删除图片处理逻辑
 * 
 * @author lucher
 * 
 */
public class UploadPicAdapter extends BaseAdapter implements OnClickListener {
	/**
	 * context
	 */
	private Context context;
	/**
	 * 数据
	 */
	private List<TaskPicData> mDatas;
	// 相机图标对应的数据
	private TaskPicData camaraData;

	// 相机图标的序列号
	public final static String SEQ_CAMERA = "camera";
	// 新增图片标识
	public final static String SEQ_NEW_PIC = "new_pic";
	// 图片排序类
	private PicComparator comparator;

	// 图片宽度
	private int picWidth;
	private int maxCount;

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param maxCount
	 *            支持的最大图片数量，当照片达到最大数量后，移除添加图标
	 */
	public UploadPicAdapter(Context context, int maxCount) {
		this.context = context;
		this.maxCount = maxCount;

		mDatas = new ArrayList<TaskPicData>(1);
		// 加入相机图标
		camaraData = createCameraData();
		mDatas.add(camaraData);
		picWidth = context.getResources().getDrawable(R.drawable.btn_upload_pic).getMinimumWidth();

		comparator = new PicComparator();
	}

	/**
	 * 加入一张图片
	 * 
	 * @param img
	 */
	public void addPic(String img) {
		TaskPicData pic = new TaskPicData(TaskPicData.TASK_TYPE_UPLOAD);

		if (!new File(img).exists()) {
			img = "failure";
		}

		pic.setSeqId(SEQ_NEW_PIC);
		pic.setPicUrl(img);
		mDatas.add(pic);
		refreshState();
	}

	/**
	 * 加入多张图片
	 * 
	 * @param imgs
	 *            多张图片的本地地址
	 */
	public void addPics(List<String> imgs) {
		for (String img : imgs) {
			addPic(img);
		}
	}

	/**
	 * 移除图片
	 * 
	 * @param pic
	 */
	public void removePic(TaskPicData pic) {
		mDatas.remove(pic);
		refreshState();
	}

	/**
	 * 获取支持的最大图片数
	 * 
	 * @return
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * 获取图片数据
	 * 
	 * @return
	 */
	public List<TaskPicData> getPicDatas() {
		List<TaskPicData> datas = new ArrayList<TaskPicData>(mDatas.size());
		for (TaskPicData data : mDatas) {
			if (data != camaraData) {
				datas.add(data);
			}
		}
		return datas;
	}

	/**
	 * 获取最大剩余照片数量
	 * 
	 * @return
	 */
	public int getLeftCount() {
		return maxCount - getPicDatas().size();
	}

	/**
	 * 刷新显示状态，若图片大于最大数则移除添加图标
	 */
	private void refreshState() {
		if (getCount() > maxCount) {// maxCount张照片后移除相机图标
			mDatas.remove(camaraData);
		} else {
			if (!containsCamera()) {
				mDatas.add(camaraData);
			}
		}
		sortPics();
		notifyDataSetChanged();
	}

	/**
	 * 对图片进行排序
	 */
	private void sortPics() {
		Collections.sort(mDatas, comparator);
	}

	/**
	 * 创建相机/添加图标
	 * 
	 * @return
	 */
	private TaskPicData createCameraData() {
		TaskPicData camera = new TaskPicData(TaskPicData.TASK_TYPE_UPLOAD);
		camera.setSeqId(UploadPicAdapter.SEQ_CAMERA);
		return camera;
	}

	/**
	 * 当前是否包含相机/添加图标
	 * 
	 * @return
	 */
	private boolean containsCamera() {
		for (TaskPicData data : mDatas) {
			if (data.getSeqId() == UploadPicAdapter.SEQ_CAMERA) {
				return true;
			}
		}
		return false;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		String seqId = mDatas.get(position).getSeqId();
		SquareLoadingImageView loadingImageView;
		if (convertView == null) {
			loadingImageView = (SquareLoadingImageView) View.inflate(context, R.layout.layout_img_task, null);
			// loadingImageView.setLayoutParams(new
			// GridView.LayoutParams(picWidth, picWidth));// 设置ImageView对象布局
		} else {
			loadingImageView = (SquareLoadingImageView) convertView;
		}

		if (seqId.equals(SEQ_CAMERA)) {// 调用相机图标
			loadingImageView.imageView.setImageResource(R.drawable.btn_upload_pic);
			loadingImageView.btnDelete.setVisibility(View.GONE);
		} else if (seqId.equals(SEQ_NEW_PIC)) {// 新增图标
			loadingImageView.btnDelete.setVisibility(View.VISIBLE);
			loadingImageView.btnDelete.setOnClickListener(this);
			loadingImageView.getImageView().setImageBitmap(ImageUtil.getScaleBitmapWithBitmapFactory(mDatas.get(position).getPicUrl(), picWidth, picWidth));
		}

		convertView = loadingImageView;
		loadingImageView.btnDelete.setTag(mDatas.get(position));
		loadingImageView.imageView.setTag(mDatas.get(position));
		convertView.setTag(mDatas.get(position));

		return convertView;
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
		return position;
	}

	/**
	 * 对图片排序类
	 * 
	 * @author lucher
	 * 
	 * @param <TaskPicData>
	 */
	private class PicComparator implements Comparator<TaskPicData> {

		@Override
		public int compare(TaskPicData pic1, TaskPicData pic2) {
			// 拍照图标在最后
			if (pic1.getSeqId().equals(SEQ_CAMERA)) {
				return 1;
			} else if (pic2.getSeqId().equals(SEQ_CAMERA)) {
				return -1;
			}
			/*
			 * // 新增图标在次最后，多个新增图标使用url排序 if (pic1.getSeqId().equals(SEQ_NEW_PIC)
			 * && pic2.getSeqId().equals(SEQ_NEW_PIC)) { if
			 * (Integer.parseInt(pic1.getPicUrl()) >
			 * Integer.parseInt(pic2.getPicUrl())) { return 1; } else { return
			 * -1; } } else
			 */if (pic1.getSeqId().equals(SEQ_NEW_PIC)) {
				return 1;
			} else if (pic2.getSeqId().equals(SEQ_NEW_PIC)) {
				return -1;
			}
			// 否则按seqid进行升级排序
			if (Integer.parseInt(pic1.getSeqId()) > Integer.parseInt(pic2.getSeqId())) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public void onClick(View view) {
		TaskPicData pic = (TaskPicData) view.getTag();
		if (view instanceof Button) {
			removePic(pic);
		}
	}
}
