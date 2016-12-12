package com.edu.accountingteachingmaterial.entity;

/**
 * Created by Administrator on 2016/12/12.
 */

public class HistoryListData {

    /**
     * chapter_id : 522
     * course_id : null
     * id : 1
     * is_exam : 0
     * lesson_id : 355
     * lesson_type : 2
     * section_id : null
     * upload_time : 2016-12-12 13:29:59
     * user_id : 35605
     */

    private int chapter_id;
    private Object course_id;
    private int id;
    private int is_exam;
    private int lesson_id;
    private int lesson_type;
    private Object section_id;
    private String upload_time;
    private int user_id;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public Object getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Object course_id) {
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_exam() {
        return is_exam;
    }

    public void setIs_exam(int is_exam) {
        this.is_exam = is_exam;
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public int getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(int lesson_type) {
        this.lesson_type = lesson_type;
    }

    public Object getSection_id() {
        return section_id;
    }

    public void setSection_id(Object section_id) {
        this.section_id = section_id;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
