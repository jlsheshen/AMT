package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/12/15.
 */

public class AccToken extends BaseData{


    /**
     * loginToken : 5997:577abe23-9ce7-40ca-9aa8-6230bc8933db
     * schoolName : 爱丁数码
     * clazzId : 20
     * clazzName : 1
     * stuId : 5997
     * stuName : 王小强
     * majorName : 会计
     * clazzYear : 2016
     * majorId : 335
     * loginName : 20161110101
     */

    private String loginToken;
    private String schoolName;
    private int clazzId;
    private String clazzName;
    private int stuId;
    private String stuName;
    private String majorName;
    private String clazzYear;
    private int majorId;
    private String loginName;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getClazzYear() {
        return clazzYear;
    }

    public void setClazzYear(String clazzYear) {
        this.clazzYear = clazzYear;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
