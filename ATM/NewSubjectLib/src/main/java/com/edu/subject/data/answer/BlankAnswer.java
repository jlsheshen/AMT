package com.edu.subject.data.answer;

import com.edu.library.data.BaseData;

/**
 * 填空题每个空答案封装
 * @author lucher
 *
 */
public class BlankAnswer extends BaseData {
    //索引
    private int index;
    //是否答对
    private boolean right;
    //得分
    private float score;
    //答案
    private String answer;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }}