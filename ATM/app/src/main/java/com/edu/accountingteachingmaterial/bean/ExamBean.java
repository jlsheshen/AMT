package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ExamBean extends BaseData {
    //试卷id
    private int examId ;
    //当前状态
    private int exmaStatus;
    //试卷标题
    private String title;
    //试卷日期
    private String time;
    //发布人
    private String publisher;
    //试题数
    private Long itemNumber;
    //开始时间
    private String startTime;
    //测试时长
    private int duration;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
    public int getExmaStatus() {
        return exmaStatus;
    }

    public void setExmaStatus(int exmaStatus) {
        this.exmaStatus = exmaStatus;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Long itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
