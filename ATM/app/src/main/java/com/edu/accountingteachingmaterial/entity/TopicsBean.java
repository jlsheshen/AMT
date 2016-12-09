package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/12/8.
 */


public class TopicsBean extends BaseData{
    /**
     * id : 14159
     * oid : 14420
     * order : 1
     * score : 6.0
     * status : 1
     * type : 1
     */

    private int id;
    private int oid;
    private int order;
    private double score;
    private int status;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
