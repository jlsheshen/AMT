package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;
import com.edu.subject.BASE_URL;

import java.util.List;

/**
 * 任务详情类
 * Created by Administrator on 2017/3/10.
 */

public class TaskDetailBean  extends BaseData {


    /**
     * answer : 456TVT v
     * comment : null
     * comment_date : null
     * content : <p>中午吃什么</p>
     * filelist : [{"id":4,"pic":"/resources/upload/cover/task/1489559558615.jpg"},{"id":8,"pic":"/resources/upload/cover/task/1489640245094.jpg"}]
     * history : [{"create_date":null,"name":"小七"},{"create_date":null,"name":"小屋"},{"create_date":null,"name":"筱柳"}]
     * stu_count : 0
     * studentlist : [{"answer":null,"create_date":null,"name":"小屋","picture":null,"team_name":"小组1"},{"answer":null,"create_date":null,"name":"筱柳","picture":null,"team_name":"小组1"},{"answer":null,"create_date":null,"name":"小七","picture":null,"team_name":"小组1"}]
     * task_name : 吃饭吃好
     * team_id : 2
     */

    private String answer;
    private String comment;
    private String comment_date;
    private String content;
    private int stu_count;
    private String task_name;
    private int team_id;
    private List<FileListBean> filelist;
    private List<HistoryBean> history;
    private List<StudentlistBean> studentlist;

    public String getAnswer()  {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public List<FileListBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<FileListBean> filelist) {
        this.filelist = filelist;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public List<StudentlistBean> getStudentlist() {
        return studentlist;
    }

    public void setStudentlist(List<StudentlistBean> studentlist) {
        this.studentlist = studentlist;
    }

    public static class FileListBean extends BaseData{
        /**
         * id : 4
         * pic : /resources/upload/cover/task/1489559558615.jpg
         */

        private String pic;
        private boolean isFoot;

        public String getPic() {
            return BASE_URL.getBaseImageUrl() + pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public boolean isFoot() {
            return isFoot;
        }

        public FileListBean setFoot(boolean foot) {
            isFoot = foot;
            return this;
        }
    }

    public static class HistoryBean  extends BaseData{
        /**
         * create_date : null
         * name : 小七
         */

        private String create_date;
        private String name;

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
    }

    public static class StudentlistBean extends BaseData{
        /**
         * answer : null
         * create_date : null
         * name : 小屋
         * picture : null
         * team_name : 小组1
         */

        private String answer;
        private String create_date;
        private String name;
        private String picture;
        private String team_name;

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
            return BASE_URL.getBaseImageUrl() +picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }
    }
}