package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/13.
 */

public class BillTemplateListBean extends BaseData{
    int id;
    Timestamp timestamp;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
