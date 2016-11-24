package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ExerciseBean extends BaseData {
    //练习id
    private int exerciseId ;
    //当前状态
    private int exerciseStatus;
    //试题类型
    private int questionType;
    //试卷标题
    private String title;
    //试卷日期
    private String time;
    //试题数
    private int itemNumber;
    //随堂练习题
    private List<ExercisePracticeBean> exerciseBeanList;

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getExerciseStatus() {
        return exerciseStatus;
    }

    public void setExerciseStatus(int exerciseStatus) {
        this.exerciseStatus = exerciseStatus;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public List<ExercisePracticeBean> getExerciseBeanList() {
        return exerciseBeanList;
    }

    public void setExerciseBeanList(List<ExercisePracticeBean> exerciseBeanList) {
        this.exerciseBeanList = exerciseBeanList;
    }
}
