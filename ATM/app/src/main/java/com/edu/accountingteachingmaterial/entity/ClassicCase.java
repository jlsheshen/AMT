package com.edu.accountingteachingmaterial.entity;

import android.content.Context;
import android.util.Log;

import com.edu.accountingteachingmaterial.model.IUpLoading;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/11/29.
 */

public class ClassicCase extends BaseData implements IUpLoading{


    /**
     * chapter_id : 718
     * content : null
     * course_id : 156
     * create_date : 2016-12-15 10:16:16
     * creator : 39261
     * file_id : 531
     * file_type : 3
     * is_publish : 1
     * lesson_type : 2
     * modifier : null
     * modify_date : 2016-12-22 13:51:22
     * parent_menu : null
     * priority : 8
     * status : 1
     * title : 利润表式样
     * type : 3
     * uri : /filedown/down/531
     */

    private int chapter_id;
    private String content;
    private int course_id;
    private String create_date;
    private int creator;
    private int file_id;
    private int file_type;
    private int is_publish;
    private int lesson_type;
    private String modifier;
    private String modify_date;
    private String parent_menu;
    private int priority;
    private int status;
    private String title;
    private int type;
    private String uri;

    @Override
    public StudyHistoryVO getUpLoadingData(Context context) {
        StudyHistoryVO history = new StudyHistoryVO();
        history.setChapterId(null);
        history.setCourseId(Long.valueOf(course_id));
        history.setSectionId(Long.valueOf(chapter_id));
        history.setIs_exam(0);
        history.setLessonId(Long.valueOf(id));
        history.setLessonType(lesson_type);
        history.setUserId(Long.valueOf(PreferenceHelper.getInstance(context).getStringValue(PreferenceHelper.USER_ID)));
        Log.d("ClassicCase", "history.getUserId():" + history.getUserId() + "---" + history.getCourseId());
        return history;
    }

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

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public int getIs_publish() {
        return is_publish;
    }

    public void setIs_publish(int is_publish) {
        this.is_publish = is_publish;
    }

    public int getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(int lesson_type) {
        this.lesson_type = lesson_type;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }

    public String getParent_menu() {
        return parent_menu;
    }

    public void setParent_menu(String parent_menu) {
        this.parent_menu = parent_menu;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
