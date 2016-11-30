package com.edu.accountingteachingmaterial.constant;

/**
 * Created by Administrator on 2016/11/21.
 */

public class NetUrlContstant {

    public static final String BASE_URL = "http://192.168.1.159/";
    //首页课程信息
    public static final String homeInfoUrl = BASE_URL + "interface/course/findCoursesByUserId/";
    //课程章节列表
    public static final String chapterUrl = BASE_URL + "interface/course/findChaptersByCourseId/";
    //试题列表
    public static final String chapterTypeUrl = BASE_URL + "interface/exam/findAllPracticeByChapterIdnType/";
    //经典示例
    public static final String classicCaseUrl =  BASE_URL +"interface/course/findContentsByTypenChapterId/";
}
