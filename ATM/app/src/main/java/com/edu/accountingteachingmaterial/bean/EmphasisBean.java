package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * 重点难点数据类
 * Created by Administrator on 2017/3/15.
 */

public class EmphasisBean extends BaseData {

    /**
     * chapter_id : 120
     * content : <p>555</p>
     * course_id : 19
     * file_id : null
     * file_type : 5
     * lesson_type : 1
     * priority : 19
     * title :
     * type : 5
     * uri : /common/course/detail/findHTMLByLessonId/530
     */

    private int chapter_id;
    private String content;
    private int course_id;
    private String file_id;
    private int file_type;
    private int lesson_type;
    private int priority;
    private String title;
    private int type;
    private String uri;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
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

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public int getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(int lesson_type) {
        this.lesson_type = lesson_type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
