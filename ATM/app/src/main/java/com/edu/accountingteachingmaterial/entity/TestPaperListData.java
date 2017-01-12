package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class TestPaperListData extends BaseData {


    /**
     * chapter_id : null
     * course_id : null
     * create_date : 2017-01-11 15:41:31
     * creator : 151
     * creator_name : 测试专用
     * end_time : 2017-01-11 20:42:00
     * exam_name : 第3道题
     * exam_type : 3
     * is_send : 1
     * last_time : 300
     * major_id : 377
     * modifier : null
     * modify_date : null
     * paper_id : 266
     * score : null
     * show_answer : 0
     * start_time : 2017-01-11 15:42:00
     * status : 1
     * stu_last_time : 10000
     * stu_score : 6.0
     * topic_num : 7
     * topics : [{"id":950,"oid":1706,"order":1,"score":2,"status":1,"type":1},{"id":951,"oid":1707,"order":2,"score":2,"status":1,"type":1},{"id":952,"oid":1714,"order":1,"score":2,"status":1,"type":2},{"id":934,"oid":1710,"order":2,"score":2,"status":1,"type":2},{"id":953,"oid":1718,"order":1,"score":2,"status":1,"type":3},{"id":954,"oid":1717,"order":2,"score":2,"status":1,"type":3},{"id":949,"oid":1738,"order":1,"score":16,"status":1,"type":22}]
     * upload_time : 2017-01-11 15:44:37
     */

    private Object chapter_id;
    private Object course_id;
    private String create_date;
    private int creator;
    private String creator_name;
    private String end_time;
    private String exam_name;
    private int exam_type;
    private int is_send;
    private int last_time;
    private int major_id;
    private Object modifier;
    private Object modify_date;
    private int paper_id;
    private Object score;
    private int show_answer;
    private String start_time;
    private int status;
    private int stu_last_time;
    private double stu_score;
    private int topic_num;
    private String upload_time;
    private List<TopicsBean> topics;

    public Object getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(Object chapter_id) {
        this.chapter_id = chapter_id;
    }

    public Object getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Object course_id) {
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

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public int getExam_type() {
        return exam_type;
    }

    public void setExam_type(int exam_type) {
        this.exam_type = exam_type;
    }

    public int getIs_send() {
        return is_send;
    }

    public void setIs_send(int is_send) {
        this.is_send = is_send;
    }

    public int getLast_time() {
        return last_time;
    }

    public void setLast_time(int last_time) {
        this.last_time = last_time;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public Object getModifier() {
        return modifier;
    }

    public void setModifier(Object modifier) {
        this.modifier = modifier;
    }

    public Object getModify_date() {
        return modify_date;
    }

    public void setModify_date(Object modify_date) {
        this.modify_date = modify_date;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public int getShow_answer() {
        return show_answer;
    }

    public void setShow_answer(int show_answer) {
        this.show_answer = show_answer;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStu_last_time() {
        return stu_last_time;
    }

    public void setStu_last_time(int stu_last_time) {
        this.stu_last_time = stu_last_time;
    }

    public double getStu_score() {
        return stu_score;
    }

    public void setStu_score(double stu_score) {
        this.stu_score = stu_score;
    }

    public int getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(int topic_num) {
        this.topic_num = topic_num;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }


}
