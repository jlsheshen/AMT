package com.edu.subject.common.rich;

import com.alibaba.fastjson.JSON;
import com.edu.library.data.BaseData;

import java.util.List;

/**
 * 富文本对象数据类
 * @author lucher
 *
 */
public class RichTextData extends BaseData {

	//对应的图片
	private List<String> imgSrc;
	//对应的富文本
	private String text;

	public List<String> getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(List<String> imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * 转为json字符串
	 * @return
	 */
	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	@Override
	public String toString() {
		return text;
	}
}
