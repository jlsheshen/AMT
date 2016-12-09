package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class TestPaperListData extends BaseData {


    /**
     * chapter_id : 502
     * course_id : 141
     * create_date : 2016-12-02 09:15:25
     * creator : 6037
     * creator_name : 李源
     * end_time : null
     * exam_name : 原始凭证作业
     * exam_type : 2
     * id : 1179
     * is_send : 0
     * last_time : null
     * major_id : null
     * modifier : null
     * modify_date : null
     * paper_id : 476
     * score : null
     * show_answer : 0
     * start_time : null
     * status : 1
     * topics : [{"id":14159,"oid":14420,"order":1,"score":6,"status":1,"type":1},{"id":14151,"oid":14423,"order":2,"score":6,"status":1,"type":1},{"id":14160,"oid":14417,"order":3,"score":6,"status":1,"type":1},{"id":14152,"oid":14431,"order":1,"score":6,"status":1,"type":2},{"id":14154,"oid":14433,"order":2,"score":6,"status":1,"type":2},{"id":14145,"oid":14427,"order":3,"score":6,"status":1,"type":2},{"id":14161,"oid":14445,"order":1,"score":6,"status":1,"type":3},{"id":14156,"oid":14438,"order":2,"score":6,"status":1,"type":3},{"id":14162,"oid":14440,"order":3,"score":6,"status":1,"type":3},{"id":14158,"oid":14448,"order":1,"score":46,"status":1,"type":22}]
     */

    private int chapter_id;
    private int course_id;
    private String create_date;
    private int creator;
    private String creator_name;
    private Object end_time;
    private String exam_name;
    private int exam_type;
    private int id;
    private int is_send;
    private Object last_time;
    private Object major_id;
    private Object modifier;
    private Object modify_date;
    private int paper_id;
    private Object score;
    private int show_answer;
    private Object start_time;
    private int status;
    private List<TopicsBean> topics;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
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

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_send() {
        return is_send;
    }

    public void setIs_send(int is_send) {
        this.is_send = is_send;
    }

    public Object getLast_time() {
        return last_time;
    }

    public void setLast_time(Object last_time) {
        this.last_time = last_time;
    }

    public Object getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Object major_id) {
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

    public Object getStart_time() {
        return start_time;
    }

    public void setStart_time(Object start_time) {
        this.start_time = start_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }


}
