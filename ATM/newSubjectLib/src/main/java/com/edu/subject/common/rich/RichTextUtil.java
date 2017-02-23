package com.edu.subject.common.rich;

import com.alibaba.fastjson.JSON;

/**
 * 富文本工具类
 * @author lucher
 *
 */
public class RichTextUtil {

	/**
	 * 将指定文本解析为RichTextData
	 * @param text
	 * @return
	 */
	public static RichTextData parse(String text) {
		RichTextData richData;
		try {
			if (text.contains("\"text\"")) {
				richData = JSON.parseObject(text, RichTextData.class);
			} else {//非富文本json格式
				richData = new RichTextData();
				richData.setText(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
			richData = new RichTextData();
			richData.setText(text);
		}

		return richData;
	}
}
