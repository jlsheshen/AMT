package com.edu.subject.bill.element.info;


/**
 * 对应空的数据
 * 
 * @author lucher
 * 
 */
public class BlankInfo extends BaseElementInfo {

	// 正确答案
	private String answer;
	// 用户答案
	private String uAnswer;
	// 是否可编辑，同样的模板，某些题的某些空不可以编辑，而是直接显示答案
	private boolean editable;
	// 字体大小
	private int textSize;

	// 是否答对，程序维护
	private boolean right;

	public BlankInfo() {

	}

	/**
	 * 构造
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param type
	 *            类别
	 */
	public BlankInfo(int x, int y, int width, int height, int type) {
		super(x, y, width, height, type);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getuAnswer() {
		return uAnswer;
	}

	public void setuAnswer(String uAnswer) {
		this.uAnswer = uAnswer;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return String.format("id:%s,answer:%s,uAnswer:%s,score:%s", getId(), answer, uAnswer, getScore());
	}
}
