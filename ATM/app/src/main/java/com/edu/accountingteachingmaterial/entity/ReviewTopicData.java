package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/12/19.
 */

public class ReviewTopicData extends BaseData {


    /**
     * ordinary : {"ask":0,"comp":0,"filling":1,"judge":3,"multi":3,"one":2,"table":1}
     * easy : {"ask":3,"comp":0,"filling":2,"judge":1,"multi":5,"one":4,"table":0}
     * hard : {"ask":0,"comp":2,"filling":0,"judge":0,"multi":0,"one":0,"table":0}
     */

    private OrdinaryBean ordinary;
    private EasyBean easy;
    private HardBean hard;

    public OrdinaryBean getOrdinary() {
        return ordinary;
    }

    public void setOrdinary(OrdinaryBean ordinary) {
        this.ordinary = ordinary;
    }

    public EasyBean getEasy() {
        return easy;
    }

    public void setEasy(EasyBean easy) {
        this.easy = easy;
    }

    public HardBean getHard() {
        return hard;
    }

    public void setHard(HardBean hard) {
        this.hard = hard;
    }

    public static class OrdinaryBean {
        /**
         * ask : 0
         * comp : 0
         * filling : 1
         * judge : 3
         * multi : 3
         * one : 2
         * table : 1
         */

        private int ask;
        private int comp;
        private int filling;
        private int judge;
        private int multi;
        private int one;
        private int table;

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
    }

    public static class EasyBean {
        /**
         * ask : 3
         * comp : 0
         * filling : 2
         * judge : 1
         * multi : 5
         * one : 4
         * table : 0
         */

        private int ask;
        private int comp;
        private int filling;
        private int judge;
        private int multi;
        private int one;
        private int table;

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
    }

    public static class HardBean {
        /**
         * ask : 0
         * comp : 2
         * filling : 0
         * judge : 0
         * multi : 0
         * one : 0
         * table : 0
         */

        private int ask;
        private int comp;
        private int filling;
        private int judge;
        private int multi;
        private int one;
        private int table;

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
    }
}
