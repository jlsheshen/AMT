package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * 任务小组列表
 * Created by Administrator on 2017/3/10.
 */

public class GroupsListBean extends BaseData {

    /**
     * group_sum : 3
     * id : 19
     * stu_count : 3
     * studentlist : [{"answer":null,"create_date":null,"name":"依依","picture":null}]
     * task_name : 孔子分组任务5
     * team_name : 小组 1
     */

    private int group_sum;
    private int id;
    private int stu_count;
    private String task_name;
    private String team_name;
    private List<StudentlistBean> studentlist;

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

    public int getStu_count() {
        return stu_count;
    }

    public void setStu_count(int stu_count) {
        this.stu_count = stu_count;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public List<StudentlistBean> getStudentlist() {
        return studentlist;
    }

    public void setStudentlist(List<StudentlistBean> studentlist) {
        this.studentlist = studentlist;
    }

    public static class StudentlistBean {
        /**
         * answer : null
         * create_date : null
         * name : 依依
         * picture : null
         */

        private String answer;
        private String create_date;
        private String name;
        private String picture;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
