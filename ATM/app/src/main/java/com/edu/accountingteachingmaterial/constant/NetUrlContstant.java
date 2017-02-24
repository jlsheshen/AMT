package com.edu.accountingteachingmaterial.constant;


import static com.edu.accountingteachingmaterial.constant.BASE_URL.TEMP_URL;
import static com.edu.accountingteachingmaterial.constant.BASE_URL.BASE_URL;

/**
 * Created by Administrator on 2016/11/21.
 */

public class NetUrlContstant {
    public static final String INTERCACE = "/eduExam/interface/";

    //调试接口
    public static final String settingIpUrl = INTERCACE + "returnSuccess";

    //首页课程信息
    public static final String homeInfoUrl = INTERCACE + "course/findCoursesByUserId/";
    //课程章节列表
    public static final String chapterUrl = INTERCACE + "course/findChaptersByCourseId/";
    //试卷列表
    public static final String chapterTypeUrl = INTERCACE + "exam/findAllPracticeByChapterIdnType/";
    //试题列表
    public static final String subjectListUrl = INTERCACE + "exam/findExamPaperTopicById/";
    //上传答案 一组题上传
    public static final String subjectSubmitUrl = INTERCACE + "exam/submitPracticeExamByStudentId/";
    //上传答案,单个题上传
    public static final String subjectSingleSubmitUrl = INTERCACE + "exam/submitSingleTopicsByStudentId/";
//    {studentId}-{examId}

    //{studentId}-{examId}-{seconds}
    //经典示例
    public static final String classicCaseUrl = INTERCACE + "course/findContentsByTypenChapterId/";
    //在线考试信息列表
//    public static final String examInfoUrl = INTERCACE+"exam/findExamPaperBriefById/";
    public static final String examInfoUrl = INTERCACE + "exam/findExamPaperNResultById/";
    //{examId}
    //获取每道题目信息
    public static final String simpleTopicUrl = INTERCACE + "exam/findSimpleTopicById/";
    //{topicId}-{paperId}
    //获取在线考试列表
    public static final String examInfoUrlList = INTERCACE + "exam/findPractiseExamByUserIdnSubjectIdnEndDate/";
    //拉取历史信息
    //拉取历史信息
    public static final String findHisUrl = INTERCACE + "exam/findUserStudyHistoryById/";
    //{userId}
    //上传历史信息
    public static final String upLoadingHisUrl = INTERCACE + "exam/saveUserStudyHistories";
    //上传本币模板数据
    public static final String localTemplates = INTERCACE + "exam/findBillTemplatesByIds";
    //    //图片url前缀
//    public static final String background = "http://192.168.1.142:80/resources/files/background/";
    //视频播放地址、pdf下载地址
    public static final String mediaorPdfUrl = "/eduExam/interface";
    //登陆
    public static final String loginUrl = INTERCACE + "login?";
    //自测获取总题数
    public static final String getReviewList = INTERCACE + "course/findTopicSumByChapterIdnLevel/";
    //上传选择题数和难易程度
    public static final String uploadingReviewList = INTERCACE + "course/createPaperForSelfTest/";
    //获取试卷答题时间
    public static final String uploadingTestTime = INTERCACE + "exam/findRemainingByExamId/";
    //获取重点难点
    public static final String EMPHASIS_URL =  "/eduExam/guest/learn/";

    public static String getEmphasisUrl() {
        return  BASE_URL +EMPHASIS_URL;
    }

    public static String getSettingIpUrl() {
        return TEMP_URL + settingIpUrl;
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

    public static String getExamInfoUrlList() {
        return BASE_URL + examInfoUrlList;
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

    public static String getGetReviewList() {
        return BASE_URL + getReviewList;
    }

    public static String getUploadingReviewList() {
        return BASE_URL + uploadingReviewList;
    }

    public static String getUploadingTestTime() {
        return BASE_URL + uploadingTestTime;
    }
}
