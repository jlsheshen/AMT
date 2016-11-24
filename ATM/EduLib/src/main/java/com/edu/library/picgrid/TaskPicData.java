package com.edu.library.picgrid;

import java.io.Serializable;

/**
 * 上传/下载图片类封装
 * 
 * @author lucher
 * 
 */
public class TaskPicData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232342234L;
	/**
	 * 任务类型:上传
	 */
	public static final int TASK_TYPE_UPLOAD = 1;
	/**
	 * 任务类型:下载
	 */
	public static final int TASK_TYPE_DOWNLOAD = 2;

	// 任务类型
	private int type;
	// 用于上传类型
	private String seqId;
	// 上传类型为图片序列号，下载类型为图片的下载地址
	private String picUrl;

	/**
	 * 构造
	 * 
	 * @param type
	 *            任务类型：TASK_TYPE_UPLOAD，TASK_TYPE_DOWNLOAD
	 */
	public TaskPicData(int type) {
		this.type = type;
	}

	/**
	 * 构造
	 * 
	 * @param type
	 *            任务类型：TASK_TYPE_UPLOAD，TASK_TYPE_DOWNLOAD
	 * @param seqId
	 *            用于上传类型，取值为UploadPicAdapter.SEQ_CAMERA/SEQ_NEW_PIC
	 * @param picUrl
	 *            上传类型为图片序列号，下载类型为图片的下载地址
	 */
	public TaskPicData(int type, String seqId, String picUrl) {
		this.type = type;
		this.seqId = seqId;
		this.picUrl = picUrl;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}