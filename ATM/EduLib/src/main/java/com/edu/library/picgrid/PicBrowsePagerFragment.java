package com.edu.library.picgrid;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.edu.library.R;
import com.edu.library.imageloader.EduImageLoader;
import com.edu.library.loadingimage.ZoomableLoadingImageView;
import com.edu.library.loadingimage.ZoomableLoadingImageView.PhotoViewAttacherListener;

/**
 * 查看图片adapter中存放的fragment
 * 
 * @author lucher
 * 
 */
public class PicBrowsePagerFragment extends Fragment implements PhotoViewAttacherListener {

	private static final String TAG = "PicBrowsePagerFragment";
	/**
	 * 图片内容数据
	 */
	private TaskPicData mData;
	/**
	 * 对应的视图
	 */
	private View mView;
	/**
	 * photoview提供的图片点击监听
	 */
	private OnPhotoTapListener mListener;

	/**
	 * 新建实例
	 * 
	 * @param data
	 *            对应数据
	 * @param listener
	 *            图片点击监听
	 * @return
	 */
	public static PicBrowsePagerFragment newInstance(TaskPicData data, OnPhotoTapListener listener) {
		Log.d(TAG, "newInstance:" + data);
		PicBrowsePagerFragment fragment = new PicBrowsePagerFragment();
		fragment.mListener = listener;
		fragment.mData = data;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView:" + mData);
		Context context = container.getContext();
		mView = View.inflate(context, R.layout.item_show_pics_page, null);
		ZoomableLoadingImageView loadingImageView = (ZoomableLoadingImageView) mView.findViewById(R.id.iv_pic);
		loadingImageView.setPhotoViewAttacherListener(this);
		loadingImageView.setScaleType(ScaleType.FIT_CENTER);

		if (mData.getType() == TaskPicData.TASK_TYPE_DOWNLOAD) {
			EduImageLoader.getInstance().displayImage(mData.getPicUrl(), loadingImageView);
			loadingImageView.setProgressValueVisiblility(View.GONE);
		} else {
			EduImageLoader.getInstance().displayImage("file://" + mData.getPicUrl(), loadingImageView);
			PhotoViewAttacher attacher = new PhotoViewAttacher(loadingImageView.imageView);
			attacher.setOnPhotoTapListener(mListener);
		}

		return mView;
	}

	@Override
	public void onPhotoViewAttacherCreated(PhotoViewAttacher attacher) {
		attacher.setOnPhotoTapListener(mListener);
	}
}
