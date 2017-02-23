package com.edu.library.html;

import java.io.InputStream;

import com.edu.library.util.SdcardPathUtil;
import com.edu.library.util.ToastUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.Log;

/**
 * html图片资源提取，目前支持：保存在res，保存在assets，保存在本地文件，从网络获取的图片
 * 对于本地图片，需要按照指定的规则录入图片名，可根据图片名进行动态布局，详情查看readme.txt
 * 
 * @author lucher
 * 
 */
public class EduImageGetter implements ImageGetter {

	private static final String TAG = "EduImageGetter";

	private Context mContext;

	// 显示html容器的宽度
	private int mContentWidth;

	// 保存在res下的图片
	public static String IMG_RES = "res:";
	// 保存在assets里的图片
	public static String IMG_ASSETS = "assets:";
	// 保存在本地文件里的图片
	public static String IMG_FILE = "file:";
	// 从网络获取的图片
	public static String IMG_HTTP = "http";

	// 图片位置信息：索引，图片总数
	private int index, total;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param width
	 *            容器宽度
	 */
	public EduImageGetter(Context context, int width) {
		this.mContentWidth = width;
		this.mContext = context;
	}

	@Override
	public Drawable getDrawable(String source) {
		return parseImg(source);
	}

	/**
	 * 解析图片
	 * 
	 * @param source
	 * @return
	 */
	private Drawable parseImg(String source) {
		// 根据不同的方式解析出图片
		Drawable drawable = null;
		String imgName = null;
		if (source.startsWith(IMG_RES)) {
			imgName = source.substring(IMG_RES.length(), source.length());
			drawable = parseResDrawable(imgName);
		} else if (source.startsWith(IMG_ASSETS)) {
			ToastUtil.showToastWithLog(mContext, "目前不支持该格式的图片：" + source);
		} else if (source.startsWith(IMG_FILE)) {
			imgName = source.substring(IMG_FILE.length(), source.length());
			String filePath = SdcardPathUtil.getExternalSdCardPath() + "/EduChineseClassQuality";
			drawable =DrawableUtils.getInstance(mContext).getInSDBy(filePath+"/"+imgName);
//			ToastUtil.showToastWithLog(mContext, "目前不支持该格式的图片：" + source);
		} else if (source.startsWith(IMG_HTTP)) {
			ToastUtil.showToastWithLog(mContext, "目前不支持该格式的图片：" + source);
		} else {
			ToastUtil.showToastWithLog(mContext, "目前不支持该格式的图片：" + source);
		}

		if (drawable != null) {
			// 按照规则对图片进行初始化和布局
			smartInitDrawable(imgName.split("_")[0], drawable);
		}

		return drawable;
	}

	/**
	 * 解析res里的图片文件,文件名类似于：1'2_img01.png 0_img02.png
	 * 
	 * @param imgName
	 * @return
	 */
	private Drawable parseResDrawable(String imgName) {
		Drawable drawable = null;
		// 判断合法性
		if (!isLegalName(imgName)) {
			return drawable;
		}

		String tmp[] = imgName.split("_");
		String drawbleName = imgName.substring(tmp[0].length() + "_".length(), imgName.length());
		try {
			if (drawbleName.endsWith(".png") || drawbleName.endsWith(".jpg")) {
				drawbleName = drawbleName.substring(0, drawbleName.length() - 4);

				InputStream in = mContext.getResources().openRawResource(getImageResId(mContext, drawbleName));
				drawable = InputStream2Drawable(in);
//				drawable = mContext.getResources().getDrawable(ImageUtil.getImageResId(mContext, drawbleName));
			} else {
				ToastUtil.showToastWithLog(mContext, "res图片资源只支持png，jpg格式：" + drawbleName);
			}
		} catch (Exception e) {
			ToastUtil.showToastWithLog(mContext, "res图片资源未找到：" + drawbleName);
			drawable = null;
			e.printStackTrace();
		}
		return drawable;
	}

	public static int getImageResId(Context context, String name) {
		return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
	}

	// InputStream转换成Drawable
	public Drawable InputStream2Drawable(InputStream is) {
		Bitmap bitmap = this.InputStream2Bitmap(is);
		return this.bitmap2Drawable(bitmap);
	}

	// Bitmap转换成Drawable
	public Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

	// 将InputStream转换成Bitmap
	public Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * 解析assets里的图片文件
	 * 
	 * @param imgName
	 * @return
	 */
	private Drawable parseAssetsDrawable(String imgName) {
		return null;
	}

	/**
	 * 解析sdcard里的图片文件
	 * 
	 * @param imgName
	 * @return
	 */
	private Drawable parseFileDrawable(String imgName) {
		return null;
	}

	/**
	 * 指定图片名称是否合法
	 * 
	 * @param imgName
	 * @return
	 */
	private boolean isLegalName(String imgName) {
		String tmp[] = imgName.split("_");
		if (tmp.length <= 1) {// 判断图片是否符合规则：是否包含下划线
			ToastUtil.showToastWithLog(mContext, "图片命名不符合规则：" + imgName + ",必须包含_");
			return false;
		}
		// 图片的位置信息，如果只有一张图片为0，否则为n'm，m为图片总数，n为第几张图片，n必须小于或等于m
		String position = tmp[0];
		if (position.equals("0")) {
			return true;
		} else {
			String[] posTmp = position.split("'");
			if (posTmp.length <= 1) {
				ToastUtil.showToastWithLog(mContext, "图片命名不符合规则：" + imgName + "，位置信息需包含'");
				return false;
			} else if (posTmp.length > 2) {
				ToastUtil.showToastWithLog(mContext, "图片命名不符合规则：" + imgName + "，只能包含一个'");
				return false;
			}
			// 位置信息判断
			try {
				index = Integer.parseInt(posTmp[0]);
				total = Integer.parseInt(posTmp[1]);
				if (index <= 0 || total <= 0) {
					ToastUtil.showToastWithLog(mContext, "图片命名不符合规则：" + imgName + "，该图位置信息只能为正数");
					return false;
				}
				if (index > total) {
					ToastUtil.showToastWithLog(mContext, "图片命名不符合规则：" + imgName + "，位置信息分子不能大于分母");
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.showToastWithLog(mContext, "图片命名不符合规则：" + imgName + "，位置信息只能包含数字");
				return false;
			}
		}

		return true;
	}

	/**
	 * 智能的初始化从html里解析出来的图片，包括初始化图片的大小以及其布局参数
	 * 
	 * @param position
	 *            位置信息
	 * @param drawable
	 */
	private void smartInitDrawable(String position, Drawable drawable) {
		int left = 0;// 图片内容左间距
		int right = 0;// 图片内容右间距
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();

		int margin = 2;// 图片默认间距
		if (position.equals("0")) {// 让图片居中，如果图片宽于控件宽度，则进行缩放
			if (width > mContentWidth) {
				// 縮放图片
				float scale = (float) width / mContentWidth;
				width = mContentWidth;
				height = (int) (height / scale);
			}
			left = (mContentWidth - width) / 2 + margin;
			right = left + width - margin * 2;
		} else {// 多张图片，整体居中，每张图片间距相同
			// 该图片允许的最大宽度
			int maxWidth = mContentWidth / total - margin * total;
			if (width > maxWidth) {
				// 縮放图片
				float scale = (float) width / maxWidth;
				width = maxWidth;
				height = (int) (height / scale);
			}
			// 除去间距后该图片可使用的最大宽度
			int maxAvaliableWidth = (mContentWidth - (mContentWidth - width * total) / (total + 1)) / total
					+ margin / total;

			left = (maxAvaliableWidth - width) + margin;
			right = maxAvaliableWidth - margin * 2;
			Log.i(TAG, "maxWidth:" + maxWidth + ",maxAvaliableWidth:" + maxAvaliableWidth);
		}

		drawable.setBounds(left, margin, right, height + margin);
		Log.d(TAG, position + "===" + "" + width + ":" + left + "," + right);
		// drawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY); //test
	}
}
