package com.edu.library.html;

import org.xml.sax.XMLReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.edu.library.popupwindow.ShowPicWindow;
import com.edu.library.util.ImageUtil;
import com.edu.library.util.ToastUtil;

/**
 * 标签处理
 * 
 * @author lucher
 * 
 */
public class EduTagHandler implements TagHandler {

	private Context mContext;
	private View mView;

	private ImageClickListener mListener;
	private ShowPicWindow window;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param view
	 *            显示查看大图popupwindow需要的容器
	 */
	public EduTagHandler(Context context, View view) {
		mContext = context;
		mView = view;
		window = new ShowPicWindow(mContext);
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
		// 处理标签<img>
		if (tag.toLowerCase().equals("img")) {
			// 获取长度
			int len = output.length();
			// 获取图片地址
			ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
			String source = images[0].getSource();
			// 使图片可点击并监听点击事件
			output.setSpan(new ImageClick(source), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	private class ImageClick extends ClickableSpan {

		String imgName;

		public ImageClick(String source) {
			if (source.startsWith(EduImageGetter.IMG_RES)) {
				String tmp[] = source.split("_");
				imgName = source.substring(tmp[0].length() + "_".length(), source.length() - 4);
				// imgName = source.split("_")[1];
				// imgName = imgName.substring(0, imgName.length() - 4);
			} else if (source.startsWith(EduImageGetter.IMG_ASSETS)) {
			} else if (source.startsWith(EduImageGetter.IMG_FILE)) {
			} else if (source.startsWith(EduImageGetter.IMG_HTTP)) {
			} else {
			}
		}

		@Override
		public void onClick(View widget) {
			if (mListener != null) {
				mListener.onImageClicked();
			}
			Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
					ImageUtil.getImageResId(mContext, imgName));
			window.setBitmap(bitmap);
			if (mView != null) {
				window.showAtLocation(mView, Gravity.NO_GRAVITY, 0, 0);
			} else {
				ToastUtil.showToast(mContext, "该模式不能显示大图，需要初始化mView-EduTagHandler");
			}
		}
	}

	/**
	 * 设置图片点击监听
	 * 
	 * @param listener
	 */
	public void setImageClickListener(ImageClickListener listener) {
		mListener = listener;
	}

	/**
	 * 窗口是否显示
	 * 
	 * @return
	 */
	public boolean isWindowShowing() {
		return window.isShowing();
	}

	/**
	 * 关闭窗口
	 */
	public void hideWindow() {
		window.dismiss();
	}

	/**
	 * 图片点击监听
	 * 
	 * @author lucher
	 * 
	 */
	public interface ImageClickListener {
		/**
		 * 图片点击
		 */
		void onImageClicked();
	}
}
