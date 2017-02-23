package com.edu.subject.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.edu.library.util.ToastUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.SubjectType;
import com.edu.subject.bill.element.info.BaseElementInfo;
import com.edu.subject.bill.element.info.BlankInfo;
import com.edu.subject.bill.template.BillTemplate;
import com.edu.subject.bill.template.BillTemplateDao;
import com.edu.subject.data.answer.BillAnswerData;
import com.edu.subject.data.body.BillBodyData;
import com.edu.subject.net.SubjectAnswerResult;

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
	// 题目数据
	private SubjectBillData subjectData;

	public BillTemplate getTemplate() {
		return template;
	}

	public void setTemplate(BillTemplate template) {
		this.template = template;
	}

	@Override
	public SubjectBillData getSubjectData() {
		return subjectData;
	}

	@Override
	public void setSubjectData(CommonSubjectData subjectData) {
		this.subjectData = (SubjectBillData) subjectData;
	}

	public BillAnswerData getUAnswerData() {
		return (BillAnswerData) answerData;
	}

	/**
	 * 加载模板，把题目数据加载到模板里，并且加载用户答案
	 * 
	 * @param context
	 * @param index 
	 * @return 加载结果
	 */
	public boolean loadTemplate(BillTemplate template, Context context, int index) {
		setTemplate(template);
		if (subjectData == null) {
			ToastUtil.showToast(context, id + ",subjectId:" + subjectId + ",题目数据为空");
			return false;
		}
		if (template == null) {
			ToastUtil.showToast(context, id + ",subjectId:" + subjectId + ",模板数据为空");
			return false;
		}
		// 获取单据里内容
		BillBodyData billBody = subjectData.getBills().get(index);
		//加载空
		if (!loadBlanks(context, billBody)) {
			return false;
		}
		//加载印章
		loadSigns(context, billBody);
		//加载闪电符
		loadFlashs(context, billBody);

		return true;
	}

	/**
	 * 加载空
	 * @param context
	 * @param billBody
	 * @return
	 */
	private boolean loadBlanks(Context context, BillBodyData billBody) {
		//获取题目里的所有空
		String[] blanks = billBody.getBlanks().split(SubjectConstant.SEPARATOR_ITEM);
		List<BlankInfo> blankDatas = new ArrayList<BlankInfo>();//保存模板里的所有空
		for (BaseElementInfo element : template.getElementDatas()) {
			if (element.getType() > SubjectConstant.ELEMENT_TYPE_BLANK_START && element.getType() < SubjectConstant.ELEMENT_TYPE_BLANK_END) {// 填空题
				blankDatas.add((BlankInfo) element);
			}
		}
		//模板里的空与题目里的空数量校验
		if (blanks.length != blankDatas.size()) {
			ToastUtil.showToast(context, id + ",subjectId:" + subjectId + ",题目数据与模板不匹配,模板：" + blankDatas.size() + ",实际：" + blanks.length);
			return false;
		}

		// 初始化正确答案和用户答案
		//		int uIndex = 0;// 用户答案索引,用户答案的size等于正确答案的size-不需要用户填写空的size
		for (int i = 0; i < blanks.length; i++) {
			if (blanks[i].startsWith(SubjectConstant.FLAG_PREFIX_DISABLED)) {// 不需要用户填写，直接显示答案
				String answer = blanks[i].substring(SubjectConstant.FLAG_PREFIX_DISABLED.length(), blanks[i].length());
				blankDatas.get(i).setEditable(false);
				blankDatas.get(i).setAnswer(answer);
			} else {//需要用户填写的空
				blankDatas.get(i).setEditable(true);
				// 正确答案初始化
				if (blanks[i].equals(SubjectConstant.FLAG_NULL_STRING)) {// 空内容为null代表空字符串
					blankDatas.get(i).setAnswer("");
				} else {
					blankDatas.get(i).setAnswer(blanks[i]);
				}
				// 用户答案初始化
				try {
					if (getUAnswerData() != null && getUAnswerData().getBlanks() != null) {
						if (getUAnswerData().getBlanks().get(i).getAnswer().equals(SubjectConstant.FLAG_NULL_STRING)) {// 空内容为null代表空字符串
							blankDatas.get(i).setuAnswer("");
						} else {
							blankDatas.get(i).setuAnswer(getUAnswerData().getBlanks().get(i).getAnswer());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 加载印章
	 * @param context
	 * @param billBody
	 */
	private void loadSigns(Context context, BillBodyData billBody) {
		//题目里的印章加载
		String signs = billBody.getSigns();
		template.getElementDatas().addAll(BillTemplateDao.getInstance(context).loadSigns(signs));
	}

	/**
	 * 加载闪电符
	 * @param context
	 * @param billBody
	 */
	private void loadFlashs(Context context, BillBodyData billBody) {
		String flashs = billBody.getFlashs();
		template.getElementDatas().addAll(BillTemplateDao.getInstance(context).loadFlashs(flashs));
	}

	@Override
	public SubjectAnswerResult toResult() {
		SubjectAnswerResult result = new SubjectAnswerResult();
		result.setFlag(getSubjectData().getFlag());
		result.setType(SubjectType.SUBJECT_BILL);
		if (getUAnswer() == null) {
			result.setAnswer("null");
		} else {
			result.setAnswer(getUAnswer().toString());
		}
		result.setScore(uScore);
		return result;
	}

	@Override
	public String toString() {
		return String.format("subjectData:%s,template:%s,uAnswer:%s,score:%s", subjectData, template, userAnswer, uScore);
	}

	/**
	 * 获取用户答案，然后保存到数据库
	 * @return
	 */
	public String getUAnswer() {
		userAnswer = new UserAnswerData();
		userAnswer.setUanswer(JSON.toJSONString(answerData));
		return userAnswer.toString();
	}

	@Override
	public void parseUAnswerData(UserAnswerData answer) {
		if (answer != null) {
			answerData = JSON.parseObject(answer.getUanswer(), BillAnswerData.class);
		}
	}
}
