package com.edu.subject.net;

import com.edu.library.data.BaseData;

/**
 * 单据题填空的答题结果
 *
 * @author lucher
 *
 */
public class BlankResult extends BaseData {
    // 填空对应的index
    private int index;
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

}
