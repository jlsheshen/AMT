package com.edu.subject.data.answer;

import java.util.List;

/**
 * 单据题答案数据
 * @author lucher
 *
 */
public class BillAnswerData extends CommonAnswerData {
	//标签
	private String label;
	//空答案
	private List<BlankResult> blanks;
	//印章答案
	private List<SignResult> signs;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<BlankResult> getBlanks() {
		return blanks;
	}

	public void setBlanks(List<BlankResult> blanks) {
		this.blanks = blanks;
	}

	public List<SignResult> getSigns() {
		return signs;
	}

	public void setSigns(List<SignResult> signs) {
		this.signs = signs;
	}

	/**
	 * blank对应的结果
	 * @author lucher
	 *
	 */
	public class BlankResult {
		// 填空对应的index
		private int index;
		//是否可编辑
		private boolean editable;
		// 用户答案
		private String answer;
		// 用户得分
		private float score;
		// 是否答对
		private boolean right;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public boolean isEditable() {
			return editable;
		}

		public void setEditable(boolean editable) {
			this.editable = editable;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		@Override
		public String toString() {
			return String.format("%s,answer:%s,score:%s", index, answer, score);
		}
	}

	/**
	 * 印章对应的结果
	 * @author lucher
	 *
	 */
	public class SignResult {
		//印章id
		private int signId;
		//x坐标
		private float x;
		//y坐标
		private float y;
		//是否正确
		private boolean right;
		// 用户得分
		private float score;

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public int getSignId() {
			return signId;
		}

		public void setSignId(int signId) {
			this.signId = signId;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		@Override
		public String toString() {
			return String.format("%s-right:%s,score:%s", signId, right, score);
		}
	}
}
