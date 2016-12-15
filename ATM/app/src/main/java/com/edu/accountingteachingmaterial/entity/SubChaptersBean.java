package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class SubChaptersBean extends BaseData {
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