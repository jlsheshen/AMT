package com.edu.library.picgrid;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu.library.R;
import com.edu.library.imageloader.EduImageLoader;
import com.edu.library.loadingimage.SquareLoadingImageView;

/**
 * 下载图片表格的adapter
 * 
 * @author lucher
 * 
 */
public class DownloadPicAdapter extends BaseAdapter {
	private Context mContext;
	// 数据
	private List<TaskPicData> mDatas;

	// 图片加载类
	private EduImageLoader mImageLoader;

	/**
	 * 初始化
	 * @param context
	 */
	public DownloadPicAdapter(Context context) {
		mContext = context;
		mImageLoader = EduImageLoader.getInstance();
	}

	/**
	 * 设置图片数据
	 * 
	 * @param datas
	 */
	public void setDatas(List<TaskPicData> datas) {
		mDatas = datas;
		notifyDataSetChanged();
	}

	/**
	 * 获取图片数据
	 * 
	 * @return
	 */
	public List<TaskPicData> getPicDatas() {
		return mDatas;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SquareLoadingImageView loadingImageView;
		if (convertView == null) {
			loadingImageView = (SquareLoadingImageView) View.inflate(mContext, R.layout.layout_img_task, null);
//			loadingImageView.setLayoutParams(new GridView.LayoutParams(picWidth, picWidth));// 设置ImageView对象布局
			mImageLoader.displayImage(mDatas.get(position).getPicUrl(), loadingImageView);
		} else {
			loadingImageView = (SquareLoadingImageView) convertView;
		}

		convertView = loadingImageView;
		convertView.setTag(mDatas.get(position));

		return convertView;
	}

	@Override
	public int getCount() {
		if (mDatas == null)
			return 0;
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

}
