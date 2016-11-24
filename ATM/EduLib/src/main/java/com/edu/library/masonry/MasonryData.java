package com.edu.library.masonry;

/**
 * 对瀑布流中item数据的封装
 * 
 * @author lucher
 * 
 */
public class MasonryData {
	// 标题
	private String title;
	// 图片对应地址
	private String url;
	// 点击后跳转的url；
	private String targetUrl;

	public MasonryData(String url, String title, String targetUrl) {
		this.url = url;
		this.title = title;
		this.targetUrl = targetUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	@Override
	public String toString() {
		return title;
	}
}