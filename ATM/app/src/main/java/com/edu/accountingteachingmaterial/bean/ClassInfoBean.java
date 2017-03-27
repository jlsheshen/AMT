package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * 课堂/教材详情接口
 * Created by Administrator on 2017/3/3.
 */

public class ClassInfoBean extends BaseData {

    /**
     * course_type : 0
     * creator : 测试专用
     * picture : /resources/upload/cover/2017-3-2/1488432933638.jpg
     * publish : 1
     * school : 爱丁数码
     * status : 1
     * title : 简易教材NEW
     */

    private int course_type;
    private String creator;
    private String picture;
    private int publish;
    private String school;
    private int status;
    private String title;
    /**
     * summary : asd
     */

    private String summary;

    public int getCourse_type() {
        return course_type;
    }

    public void setCourse_type(int course_type) {
        this.course_type = course_type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
