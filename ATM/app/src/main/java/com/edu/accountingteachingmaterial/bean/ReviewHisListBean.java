package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ReviewHisListBean extends BaseData {


    //标题
    String title ;
    //题数
    String number;
    //日期
    String date;
    //分数
    String score;
    //状态
    int state;
    //章节id
    int chapterId;
    //类型
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
