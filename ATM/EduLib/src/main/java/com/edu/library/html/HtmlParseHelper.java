package com.edu.library.html;

import com.edu.library.html.EduTagHandler.ImageClickListener;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

/**
 * html解析帮助类,用于解析html到textview中显示
 * 
 * @author lucher
 * 
 */
public class HtmlParseHelper {

	private Context mContext;
	// 显示html容器的宽度
	private int mContentWidth;
	// 是否完成初始化
	private boolean init;

	// 用于控制在还未初始化控件的情况下进行解析操作
	// 待显示内容
	private String mContent;
	// 是否需要在初始化完成后进行解析内容
	private boolean parseOnInit;

	private EduTagHandler tagHandler;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param textView
	 *            容器宽度
	 * @param view
	 *            显示查看大图时popupwindow的容器，若为空则不能查看大图
	 */
	public HtmlParseHelper(Context context, final TextView textView, View view) {
		mContext = context;
		tagHandler = new EduTagHandler(mContext, view);

		// 要实现图片的点击事件需要加入下面方法
		textView.setClickable(true);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		if (textView.getWidth() <= 0) {
			ViewTreeObserver observer = textView.getViewTreeObserver();
			observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {// 刚初始化时获取不到tvLocal的宽度，需要等待其布局完成后获取
					textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					mContentWidth = textView.getWidth();
					init = true;

					if (parseOnInit) {
						textView.setText(parse(mContent));
					}
				}
			});
		} else {
			init = true;
		}
	}

	/**
	 * 解析指定html内容
	 * 
	 * @param content
	 *            待解析内容
	 * @return 解析过后的内容
	 */
	public Spanned parse(String content) {
		if (!init) {
			parseOnInit = true;
			mContent = content;
			return null;
		}
		return Html.fromHtml(content, new EduImageGetter(mContext, mContentWidth), tagHandler);
	}

	/**
	 * 设置图片点击监听
	 * 
	 * @param listener
	 */
	public void setImageClickListener(ImageClickListener listener) {
		tagHandler.setImageClickListener(listener);
	}
	
	/**
	 * 查看大图窗口是否显示
	 * @return
	 */
	public boolean isImageShowing() {
		return tagHandler.isWindowShowing();
	}
	
	/**
	 * 关闭大图窗口
	 */
	public void hideImageWindow() {
		tagHandler.hideWindow();
	}
}
