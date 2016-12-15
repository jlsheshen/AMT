package com.edu.subject.data;

import android.content.Context;

import com.edu.subject.SubjectConstant;
import com.edu.subject.bill.BillAnswerHandler;
import com.edu.subject.bill.element.ElementType;
import com.edu.subject.bill.element.info.BaseElementInfo;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.bill.template.BillTemplate;
import com.edu.subject.bill.view.BlankGroupAmountEditText;
import com.edu.subject.net.AnswerResult;
import com.edu.subject.net.BlankResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 单据测试数据
 * 
 * @author lucher
 * 
 */
public class TestBillData extends BaseTestData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7866847549496754459L;

	// 单据模板
	private BillTemplate template;
	// 用户印章
	private String uSigns;
	// 题目数据
	private SubjectBillData subjectData;

	/**
	 * 加载模板，把题目数据加载到模板里
	 * 
	 * @param context
	 * @return 加载结果信息，成功为success
	 */
	public String loadTemplate(Context context) {
		if (subjectData == null) {
			return id + ",subjectId:" + subjectId + ",题目数据为空";
		}
		if (template == null) {
			return id + ",subjectId:" + subjectId + ",模板数据为空";
		}
		// 获取题目里的空，模板里的空，答案记录里的空 然后进行初始化操作
		String[] blanks = subjectData.getAnswer().split(SubjectConstant.SEPARATOR_ITEM);
		List<BlankInfo> blankDatas = new ArrayList<BlankInfo>();
		for (BaseElementInfo element : template.getElementDatas()) {
			if (element.getType() > SubjectConstant.ELEMENT_TYPE_BLANK_START && element.getType() < SubjectConstant.ELEMENT_TYPE_BLANK_END) {// 填空题
				blankDatas.add((BlankInfo) element);
			}
		}
		String[] uBlankses = null;
		if (uAnswer != null) {
			uBlankses = uAnswer.split(SubjectConstant.SEPARATOR_ITEM);
		}
		if (blanks.length != blankDatas.size()) {
			return id + ",subjectId:" + subjectId + ",题目数据与模板不匹配";
		}
		// 初始化正确答案和用户答案
		int uIndex = 0;// 用户答案索引,用户答案的size等于正确答案的size-不需要用户填写空的size
		for (int i = 0; i < blanks.length; i++) {
			if (blanks[i].startsWith(SubjectConstant.FLAG_PREFIX_DISABLED)) {// 不需要用户填写，直接显示答案
				String answer = blanks[i].substring(SubjectConstant.FLAG_PREFIX_DISABLED.length(), blanks[i].length());
				blankDatas.get(i).setEditable(false);
				blankDatas.get(i).setAnswer(answer);
			} else {
				blankDatas.get(i).setEditable(true);
				// 正确答案初始化
				if (blanks[i].equals(SubjectConstant.FLAG_NULL_STRING)) {// 空内容为null代表空字符串
					blankDatas.get(i).setAnswer("");
				} else {
					blankDatas.get(i).setAnswer(blanks[i]);
				}
				// 用户答案初始化
				if (uAnswer != null && !uAnswer.equals("") && !uAnswer.equals(SubjectConstant.FLAG_NULL_STRING)) {
					if (uBlankses[uIndex].equals(SubjectConstant.FLAG_NULL_STRING)) {// 空内容为null代表空字符串
						blankDatas.get(i).setuAnswer("");
					} else {
						blankDatas.get(i).setuAnswer(uBlankses[uIndex]);
					}
					uIndex++;
				}
			}
		}

		return "success";
	}

	public BillTemplate getTemplate() {
		return template;
	}

	public void setTemplate(BillTemplate template) {
		this.template = template;
	}

	public String getuSigns() {
		return uSigns;
	}

	public void setuSigns(String uSigns) {
		this.uSigns = uSigns;
	}

	@Override
	public SubjectBillData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(BaseSubjectData subjectData) {
		this.subjectData = (SubjectBillData) subjectData;
	}

	@Override
	public String toString() {
		return String.format("subjectData:%s,template:%s,uBlanks:%s,score:%s", subjectData, template, uAnswer, uScore);
	}

	@Override
	public AnswerResult toResult() {
		AnswerResult result = new AnswerResult();
		judgeAnswer(result);
		result.setFlag(getSubjectData().getFlag());
		result.setType(subjectType);
		if (uAnswer == null) {
			result.setAnswer("null");
		} else {
			result.setAnswer(uAnswer);
		}
		result.setScore(uScore);
		return result;
	}

	/**
	 * 判断答案
	 *
	 * @param result
	 */
	private void judgeAnswer(AnswerResult result) {
		// 存放需要分组的空，key-分组id，value-对应组的组件
		HashMap<Integer, List<BlankResult>> groups = new HashMap<Integer, List<BlankResult>>(1);

		String answers[] = getSubjectData().getAnswer().split(SubjectConstant.SEPARATOR_ITEM);
		String uAnswers[] = null;
		if (uAnswer != null) {
			uAnswers = uAnswer.split(SubjectConstant.SEPARATOR_ITEM);
		}
		List<BlankResult> blanks = new ArrayList<>(answers.length);
		int index = 0;// 所有空遍历index
		int uIndex = 0;// 用户答案索引,用户答案的size等于正确答案的size-不需要用户填写空的size
		for (BaseElementInfo element : template.getElementDatas()) {
			if (element.getType() > SubjectConstant.ELEMENT_TYPE_BLANK_START && element.getType() < SubjectConstant.ELEMENT_TYPE_BLANK_END) {// 填空题
				BlankResult blank = new BlankResult();
				blank.setIndex(index + 1);
				if (uAnswer == null) {
					blank.setScore(0);
					blank.setAnswer("null");
				} else {
					if (answers[index].startsWith(SubjectConstant.FLAG_PREFIX_DISABLED)) {// 不需要用户填写，直接显示答案
						blank.setScore(0);
						blank.setAnswer("null");
					} else {
						if (BillAnswerHandler.isGroupBlank(element.getType())) {// 一组空元素
							String remark = element.getRemark();
							int groupId;
							 groupId = Integer.valueOf(remark);

							if (groups.get(groupId) == null) {
								List<BlankResult> list = new ArrayList<BlankResult>(3);
								groups.put(groupId, list);
								list.add(blank);
							} else {
								groups.get(groupId).add(blank);
							}
						}
						// 答案判断
						if (element.getType() == ElementType.TYPE_AMOUNT_LOWER_GROUP) {
							blank.setAnswer(uAnswers[uIndex].replace(BlankGroupAmountEditText.ANSWER_FLAG, ""));
							//去掉前后缀
							String tmp = uAnswers[uIndex].substring(BlankGroupAmountEditText.ANSWER_FLAG.length());
							tmp = tmp.substring(0, tmp.length() - BlankGroupAmountEditText.ANSWER_FLAG.length());
							// 用户答案去掉前面空格后进行答案判断
							tmp = (tmp + "*").trim();
							tmp = tmp.substring(0, tmp.length() - 1);
							if (answers[index].equals(tmp)) {
								blank.setScore(element.getScore());
								blank.setRight(true);
							} else {
								blank.setScore(0);
								blank.setRight(false);
							}
						} else {
							blank.setAnswer(uAnswers[uIndex]);
							if (answers[index].equals(uAnswers[uIndex])) {
								blank.setScore(element.getScore());
								blank.setRight(true);
							} else {
								blank.setScore(0);
								blank.setRight(false);
							}
						}
						uIndex++;
					}
				}
				blanks.add(blank);
				index++;
			}
		}
		// 计算分组类控件的分数
		Iterator<Integer> iterator = groups.keySet().iterator();
		while (iterator.hasNext()) {
			List<BlankResult> group = groups.get(iterator.next());
			boolean right = true;// 对应组是否答对，只要有一个空答错，则算错
			for (BlankResult blank : group) {
				if (right && !blank.isRight()) {
					right = false;
				}
			}
			if (!right) {
				for (BlankResult blank : group) {
					blank.setScore(0);
				}
			}
		}
		result.setBlankResult(blanks);
	}

}
