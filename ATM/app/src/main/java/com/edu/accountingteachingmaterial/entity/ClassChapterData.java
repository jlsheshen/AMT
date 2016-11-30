package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class ClassChapterData extends BaseData {

    /**
     * subChapters : [{"subChapters":[],"id":179,"title":"SASA","order":27},{"subChapters":[],"id":181,"title":"dasasd","order":59},{"subChapters":[],"id":202,"title":"asd","order":63},{"subChapters":[],"id":209,"title":"asdasd","order":57},{"subChapters":[],"id":211,"title":"asdasd","order":86},{"subChapters":[],"id":215,"title":"asdasd","order":92},{"subChapters":[],"id":238,"title":"asdsad","order":94},{"subChapters":[],"id":246,"title":"asdasd","order":84}]
     * id : 150
     * title : 第一章
     * order : 1
     */

    private int id;
    private String title;
    private int order;
    private List<SubChaptersBean> subChapters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<SubChaptersBean> getSubChapters() {
        return subChapters;
    }

    public void setSubChapters(List<SubChaptersBean> subChapters) {
        this.subChapters = subChapters;
    }

    public static class SubChaptersBean extends BaseData{
        /**
         * subChapters : []
         * id : 179
         * title : SASA
         * order : 27
         */

        private int id;
        private String title;
        private int order;
        private List<?> subChapters;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public List<?> getSubChapters() {
            return subChapters;
        }

        public void setSubChapters(List<?> subChapters) {
            this.subChapters = subChapters;
        }
    }
}
