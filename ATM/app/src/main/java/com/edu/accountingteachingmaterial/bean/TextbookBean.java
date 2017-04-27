package com.edu.accountingteachingmaterial.bean;

import com.edu.accountingteachingmaterial.constant.BASE_URL;
import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2017/3/3.
 */

public class TextbookBean extends BaseData {


    /**
     * name : 测试专用
     * picture : /resources/upload/cover/2017-1-3/1483428803789.jpg
     * school_name : 爱丁数码
     * summary : 公共课程介绍
     * title : 测试公共课程
     */

    private String name;
    private String picture;
    private String school_name;
    private String summary;
    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return BASE_URL.getBaseImageUrl() + picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
