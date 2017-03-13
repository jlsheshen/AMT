package com.edu.accountingteachingmaterial.bean;

import java.util.List;

/**
 * 分组任务列表类
 * Created by Administrator on 2017/3/9.
 */

public class GroupTaskListBean {


    /**
     * totalRow : 2
     * pageNumber : 1
     * lastPage : true
     * firstPage : true
     * totalPage : 1
     * pageSize : 100
     * list : [{"class_id":77,"classname":"杂技与魔术表演2017级1班","content":"<p>中午吃什么<\/p>","course_id":19,"create_date":"2017-03-08 20:49:04","creator":151,"group_sum":2,"id":1,"name":"测试专用","status":1,"student_attend":0,"task_name":"吃饭吃好","task_status":1}]
     */

    private int totalRow;
    private int pageNumber;
    private boolean lastPage;
    private boolean firstPage;
    private int totalPage;
    private int pageSize;
    private List<ListBean> list;

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * class_id : 77
         * classname : 杂技与魔术表演2017级1班
         * content : <p>中午吃什么</p>
         * course_id : 19
         * create_date : 2017-03-08 20:49:04
         * creator : 151
         * group_sum : 2
         * id : 1
         * name : 测试专用
         * status : 1
         * student_attend : 0
         * task_name : 吃饭吃好
         * task_status : 1
         */

        private int class_id;
        private String classname;
        private String content;
        private int course_id;
        private String create_date;
        private int creator;
        private int group_sum;
        private int id;
        private String name;
        private int status;
        private int student_attend;
        private String task_name;
        private int task_status;

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public int getGroup_sum() {
            return group_sum;
        }

        public void setGroup_sum(int group_sum) {
            this.group_sum = group_sum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStudent_attend() {
            return student_attend;
        }

        public void setStudent_attend(int student_attend) {
            this.student_attend = student_attend;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public int getTask_status() {
            return task_status;
        }

        public void setTask_status(int task_status) {
            this.task_status = task_status;
        }
    }
}