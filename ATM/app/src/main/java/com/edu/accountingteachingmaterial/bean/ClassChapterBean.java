package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

import java.util.List;



public class ClassChapterBean extends BaseData {
	
	int chapterId;
	String title;
	String chapterNum;
	List<NodeBean> nodes;

	public String getChapterNum() {
		return chapterNum;
	}

	public void setChapterNum(String chapterNum) {
		this.chapterNum = chapterNum;
	}

	public int getChapterId() {
		return chapterId;
	}


	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List<NodeBean> getNodes() {
		return nodes;
	}


	public void setNodes(List<NodeBean> nodes) {
		this.nodes = nodes;
	}



	

}
