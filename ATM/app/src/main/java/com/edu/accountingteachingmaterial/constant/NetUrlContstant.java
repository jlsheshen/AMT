package com.edu.accountingteachingmaterial.constant;

import static com.edu.subject.BASE_URL.BASE_URL;

/**
 * Created by Administrator on 2016/11/21.
 */

public  class NetUrlContstant {

    public static final String URL_NAME = "URL_NAME";
    //调试接口
    public static final String settingIpUrl =  "/interface/returnSuccess";

    //首页课程信息
    public static final String homeInfoUrl =  "/interface/course/findCoursesByUserId/";
    //课程章节列表
    public static final String chapterUrl =   "/interface/course/findChaptersByCourseId/";
    //试卷列表
    public static final String chapterTypeUrl =   "/interface/exam/findAllPracticeByChapterIdnType/";
    //试题列表
    public static final String subjectListUrl =   "/interface/exam/findExamPaperTopicById/";
    //上传答案 一组题上传
    public static final String subjectSubmitUrl =   "/interface/exam/submitPracticeExamByStudentId/";
    //上传答案,单个题上传
    public static final String subjectSingleSubmitUrl = "/interface/exam/submitSingleTopicsByStudentId/";
//    {studentId}-{examId}

    //{studentId}-{examId}-{seconds}
    //经典示例
    public static final String classicCaseUrl =  "/interface/course/findContentsByTypenChapterId/";
    //在线考试信息列表
    public static final String examInfoUrl =  "/interface/exam/findExamPaperBriefById/";
    //{examId}
    //获取每道题目信息
    public static final String simpleTopicUrl =  "/interface/exam/findSimpleTopicById/";
    //{topicId}-{paperId}
    //获取在线考试列表
    public static final String getExamInfoUrl =  "/interface/exam/findPractiseExamByUserIdnSubjectIdnEndDate/";
    //拉取历史信息
    public static final String findHisUrl =  "/interface/exam/findUserStudyHistoryByStuNum/";
    //{userId}
    //上传历史信息
    public static final String upLoadingHisUrl =  "/interface/exam/saveUserStudyHistories";
    //上传本币模板数据
    public static final String localTemplates =   "/interface/exam/findBillTemplatesByIds";
//    //图片url前缀
//    public static final String background = "http://192.168.1.142:80/resources/files/background/";
    //视频播放地址、pdf下载地址
    public static final String mediaorPdfUrl =   "/interface/filedown/down/";
    //登陆
    public static final String loginUrl =   "/interface/login?";


    public static String getSettingIpUrl() {
        return BASE_URL + settingIpUrl;
    }

    public static String getHomeInfoUrl() {
        return BASE_URL + homeInfoUrl;
    }

    public static String getChapterUrl() {
        return BASE_URL + chapterUrl;
    }

    public static String getChapterTypeUrl() {
        return BASE_URL + chapterTypeUrl;
    }

    public static String getSubjectListUrl() {
        return BASE_URL + subjectListUrl;
    }

    public static String getSubjectSubmitUrl() {
        return BASE_URL + subjectSubmitUrl;
    }

    public static String getSubjectSingleSubmitUrl() {
        return BASE_URL + subjectSingleSubmitUrl;
    }

    public static String getClassicCaseUrl() {
        return BASE_URL + classicCaseUrl;
    }

    public static String getExamInfoUrl() {
        return BASE_URL + examInfoUrl;
    }

    public static String getSimpleTopicUrl() {
        return BASE_URL + simpleTopicUrl;
    }

    public static String getGetExamInfoUrl() {
        return BASE_URL + getExamInfoUrl;
    }

    public static String getFindHisUrl() {
        return BASE_URL + findHisUrl;
    }

    public static String getUpLoadingHisUrl() {
        return BASE_URL + upLoadingHisUrl;
    }

    public static String getLocalTemplates() {
        return BASE_URL + localTemplates;
    }

    public static String getMediaorPdfUrl() {
        return BASE_URL + mediaorPdfUrl;
    }

    public static String getLoginUrl() {
        return BASE_URL + loginUrl;
    }
}
