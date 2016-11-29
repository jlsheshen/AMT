package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/11/21.
 * 试卷列表
 */

public class ExamListData extends BaseData {



    /**
     * 另外添加 答题状态
     * @return
     */
    private int State;
    /**
     * chapter_id : 179
     * course_id : 111
     * create_date : 2016-11-28 11:31:58
     * creator : 219
     * end_time : null
     * exam_name : 课前预习
     * exam_type : 2
     * is_send : 0
     * last_time : null
     * lesson_type : 3
     * major_id : null
     * modifier : null
     * modify_date : null
     * paper_id : 443
     * score : null
     * show_answer : 1
     * start_time : null
     * status : 1
     * topic_num : 4
     */

    private int chapter_id;
    private int course_id;
    private String create_date;
    private int creator;
    private Object end_time;
    private String exam_name;
    private int exam_type;
    private int is_send;
    private Object last_time;
    private int lesson_type;
    private Object major_id;
    private Object modifier;
    private Object modify_date;
    private int paper_id;
    private Object score;
    private int show_answer;
    private Object start_time;
    private int status;
    private int topic_num;

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }


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

    public int getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(int lesson_type) {
        this.lesson_type = lesson_type;
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

    public int getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(int topic_num) {
        this.topic_num = topic_num;
    }
}
