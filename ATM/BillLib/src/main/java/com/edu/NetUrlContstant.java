package com.edu;

/**
 * Created by Administrator on 2016/11/21.
 */

public class NetUrlContstant {
    public static final String URL_NAME = "URL_NAME";

    public static String BASE_URL = "http://192.168.1.142:8080/";
    //首页课程信息
    public static final String homeInfoUrl = BASE_URL + "interface/course/findCoursesByUserId/";
    //课程章节列表
    public static final String chapterUrl = BASE_URL + "interface/course/findChaptersByCourseId/";
    //试卷列表
    public static final String chapterTypeUrl = BASE_URL + "interface/exam/findAllPracticeByChapterIdnType/";
    //试题列表
    public static final String subjectListUrl = BASE_URL + "interface/exam/findExamPaperTopicById/";
    //上传答案 一组题上传
    public static final String subjectSubmitUrl = BASE_URL + "interface/exam/submitPracticeExamByStudentId/";
    //上传答案,单个题上传
    public static final String subjectSingleSubmitUrl = BASE_URL + "interface/exam/submitSingleTopicsByStudentId/";
//    {studentId}-{examId}

    //{studentId}-{examId}-{seconds}
    //经典示例
    public static final String classicCaseUrl = BASE_URL + "interface/course/findContentsByTypenChapterId/";
    //在线考试信息列表
    public static final String examInfoUrl = BASE_URL + "interface/exam/findExamPaperBriefById/";
    //{examId}
    //获取每道题目信息
    public static final String simpleTopicUrl = BASE_URL + "interface/exam/findSimpleTopicById/";
    //{topicId}-{paperId}
    //获取在线考试列表
    public static final String getExamInfoUrl = BASE_URL + "interface/exam/findPractiseExamByUserIdnSubjectIdnEndDate/";
    //拉取历史信息
    public static final String findHisUrl = BASE_URL + "interface/exam/findUserStudyHistoryByUserId/";
    //{userId}
    //上传历史信息
    public static final String upLoadingHisUrl = BASE_URL + "interface/exam/saveUserStudyHistories";
    //上传本币模板数据
    public static final String localTemplates = BASE_URL + "interface/exam/findBillTemplatesByIds";
    //图片url前缀
    public static final String background = "http://192.168.1.142:80/resources/files/background/";
    //视频播放地址、pdf下载地址
    public static final String mediaorPdfUrl = BASE_URL + "interface/filedown/down/";

}
