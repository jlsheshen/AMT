package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * 练习中随堂练习题
 * Created by Administrator on 2016/11/9.
 */
public class ExercisePracticeBean extends BaseData {
    //id
    private int practiceId;
    //题序号
    private int practiceNum;
    //对或错
    private int isRight;
    //题型
    private int questionType;
    //内容
    private String content;

    public int getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(int practiceId) {
        this.practiceId = practiceId;
    }

    public int getPracticeNum() {
        return practiceNum;
    }

    public void setPracticeNum(int practiceNum) {
        this.practiceNum = practiceNum;
    }

    public int isRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
