package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/12/13.
 */

public class BillTemplateListBean extends BaseData{
    int id;
    String timestamp;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
