package com.edu.subject.data;

import com.alibaba.fastjson.JSON;
import com.edu.library.data.BaseData;

/**
 * 通用题型用户答案数据封装，用于保持各种题型的答案保存到数据库以及从数据库读取的方式统一
 * @author lucher
 *经过修改,添加分数字段
 */
public class UserAnswerData extends BaseData {

	//用户答案
	private String uanswer;

	//用户得分
	private float uscore;
	//是否正确
	private int state = 0;
	//子题类型
	private int type;


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUanswer() {
		return uanswer;
	}

	public void setUanswer(String uanswer) {
		this.uanswer = uanswer;
	}

	public float getuScore() {
		return uscore;
	}

	public void setuScore(float uScore) {
		this.uscore = uScore;

	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
