package com.edu.library.masonry;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.library.R;
import com.edu.library.imageloader.EduImageLoader;

/**
 * 瀑布流用adapter
 * 
 * @author lucher
 * 
 */
public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {

	// 对应数据
	private List<MasonryData> mDatas;
	private static OnClickListener mListener;

	// 图片加载类
	private EduImageLoader imageLoader;

	/**
	 * 构造
	 * 
	 * @param datas
	 *            对应数据
	 * @param listener
	 *            item点击监听
	 */
	public MasonryAdapter(List<MasonryData> datas, OnClickListener listener) {
		mDatas = datas;
		mListener = listener;
		imageLoader = EduImageLoader.getInstance();
	}

	@Override
	public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_masonry, viewGroup, false);

		return new MasonryView(view);
	}

	@Override
	public void onBindViewHolder(MasonryView masonryView, int position) {
		imageLoader.displayImage(mDatas.get(position).getUrl(), masonryView.imageView, null, null);
		masonryView.textView.setText(mDatas.get(position).getTitle());
		masonryView.itemView.setTag(mDatas.get(position));
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	public static class MasonryView extends RecyclerView.ViewHolder {

		ImageView imageView;
		TextView textView;

		public MasonryView(View itemView) {
			super(itemView);
			itemView.setOnClickListener(mListener);
			imageView = (ImageView) itemView.findViewById(R.id.masonry_item_img);
			textView = (TextView) itemView.findViewById(R.id.masonry_item_title);
		}

	}

}