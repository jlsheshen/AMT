package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * 任务详情类
 * Created by Administrator on 2017/3/10.
 */

public class TaskDetailBean  extends BaseData{

    /**
     * answer :
     * comment : null
     * comment_date : null
     * content : <p>孔子课堂任务内容aaa<img src="http://192.168.1.143:8080/eduExam/resources/upload/image/20170309/1489059610349082710.jpg" title="1489059610349082710.jpg" alt="72f082025aafa40fe871b36bad64034f79f019d4.jpg"/></p>
     * id : null
     * pic : null
     * studentlist : [{"answer":null,"create_date":null,"name":"依依","picture":null}]
     * task_name : 孔子分组任务5
     */

    private String answer;
    private String comment;
    private String comment_date;
    private String content;
    private int id;
    private String pic;
    private String task_name;
    private List<StudentlistBean> studentlist;

    public String getAnswer() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
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
