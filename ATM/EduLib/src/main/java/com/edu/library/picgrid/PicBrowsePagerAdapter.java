package com.edu.library.picgrid;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

/**
 * 查看大图viewpager用的adapter
 * 
 * @author lucher
 * 
 */
public class PicBrowsePagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = null;
	// 所有图片数据
	private List<TaskPicData> mDatas;
	// 对应的页面
	private ArrayList<PicBrowsePagerFragment> mPagerList;
	// 图片点击监听
	private OnPhotoTapListener mListener;

	/**
	 * 构造
	 * 
	 * @param childFragmentManager
	 * @param datas
	 *            所有数据
	 * @param listener
	 *            图片点击监听
	 */
	public PicBrowsePagerAdapter(FragmentManager childFragmentManager, List<TaskPicData> datas, OnPhotoTapListener listener) {
		super(childFragmentManager);
		mDatas = datas;
		mListener = listener;
		mPagerList = new ArrayList<PicBrowsePagerFragment>(mDatas.size());
		for (int i = 0; i < mDatas.size(); i++) {
			mPagerList.add(null);
		}
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "getItem:" + position);
		if (mPagerList.get(position) == null) {
			mPagerList.set(position, PicBrowsePagerFragment.newInstance(mDatas.get(position), mListener));
		}
		return mPagerList.get(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		try {
			Log.e(TAG, "destroyItem:" + position);
			// 销毁fragment
			PicBrowsePagerFragment fragment = mPagerList.get(position);
			fragment = null;
			mPagerList.set(position, fragment);
			FragmentManager manager = ((Fragment) object).getFragmentManager();
			FragmentTransaction trans = manager.beginTransaction();
			trans.remove((Fragment) object);
			trans.commit();
			// 销毁视图结构
			super.destroyItem(container, position, object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
