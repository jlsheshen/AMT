package com.edu.accountingteachingmaterial.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ReviewTopicVo {

    /**
     * ask : 0
     * comp : 0
     * filling : 1
     * judge : 3
     * multi : 3
     * one : 2
     * table : 1
     * level : [1,2,3]
     */

    private int ask;
    private int comp;
    private int filling;
    private int judge;
    private int multi;
    private int one;
    private int table;
    private List<Integer> level;

    public int getAsk() {
        return ask;
    }

    public void setAsk(int ask) {
        this.ask = ask;
    }

    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public int getFilling() {
        return filling;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }

    public int getJudge() {
        return judge;
    }

    public void setJudge(int judge) {
        this.judge = judge;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public List<Integer> getLevel() {
        return level;
    }

    public void setLevel(List<Integer> level) {
        this.level = level;
    }
}
