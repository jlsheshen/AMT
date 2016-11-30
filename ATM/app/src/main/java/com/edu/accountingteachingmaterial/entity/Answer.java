package com.edu.accountingteachingmaterial.entity;

/**
 * Created by Administrator on 2016/11/30.
 */

public class Answer {
    int flag;
    String uAnswer;
    String uSign;
    String uScore;
    String type;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getuAnswer() {
        return uAnswer;
    }

    public void setuAnswer(String uAnswer) {
        this.uAnswer = uAnswer;
    }

    public String getuSign() {
        return uSign;
    }

    public void setuSign(String uSign) {
        this.uSign = uSign;
    }

    public String getuScore() {
        return uScore;
    }

    public void setuScore(String uScore) {
        this.uScore = uScore;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
