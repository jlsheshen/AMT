package com.edu.accountingteachingmaterial.entity;

import android.content.Context;
import android.util.Log;

import com.edu.accountingteachingmaterial.model.IUpLoading;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/12/12.
 */

public class HistoryListData extends BaseData implements IUpLoading {


    /**
     * chapter_id : 677
     * chapter_title : 会计的概念与目标
     * course_id : null
     * course_title : 基础会计 第四版 高等教育出版社
     * date_diff : 1
     * file_path : D:\filesys\20161216\138482f7-1c87-4093-9d6c-9635776cba11.mp4
     * file_type : 1
     * is_exam : 0
     * lesson_id : 436
     * lesson_title : 111
     * lesson_type : 2
     * section_id : null
     * section_title : null
     * upload_time : 2016-12-29 15:54:47
     * uri : /filedown/down/544
     * user_id : 39262
     */

    private int chapter_id;
    private String chapter_title;
    private int course_id;
    private String course_title;
    private int date_diff;
    private String file_path;
    private int file_type;
    private int is_exam;
    private int lesson_id;
    private String lesson_title;
    private int lesson_type;
    private String section_id;
    private String section_title;
    private String upload_time;
    private String uri;
    private int user_id;

    @Override
    public StudyHistoryVO getUpLoadingData(Context context) {
        StudyHistoryVO history = new StudyHistoryVO();
        history.setSectionId(Long.valueOf(chapter_id));
        history.setCourseId((long) course_id);
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

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public int getDate_diff() {
        return date_diff;
    }

    public void setDate_diff(int date_diff) {
        this.date_diff = date_diff;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
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

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public int getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(int lesson_type) {
        this.lesson_type = lesson_type;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_title() {
        return section_title;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
