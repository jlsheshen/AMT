package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/11/29.
 */

public class HistoryData extends BaseData {
    private String chapterName;
    private String data;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
