package com.edu.accountingteachingmaterial.constant;

public final class ClassContstant {
    //重点示例文件类型

    //视频文件类型
    public static final int MEADIA_TYPE = 1;
    //音频文件类型
    public static final int VOICE_TYPE = 2;
    //ppt文件类型
    public static final int PPT_TYPE = 3;
    //doc文件类型
    public static final int DOC_TYPE = 4;
    //pdf文件类型
    public static final int PDF_TYPE = 5;

    /**
     * 答题模式
     */
    public static final int TEST_MODE_NORMAL = 1;
    /**
     * 查看答案模式
     */
    public static final int TEST_MODE_TEST = 2;
    /**
     * 随堂练习模式
     */
    public static final int TEST_MODE_INCLASS = 3;
    /**
     * 查看我的作答模式
     */
    public static final int TEST_MODE_LOOK = 4;

    //试卷当前状态
    //试卷已提交
    public static final int EXAM_COMMIT = 1;
    //试卷未提交
    public static final int EXAM_UNDONE = 2;
    //试卷已被批阅
    public static final int EXAM_READ = 3;
    //试卷未下载
    public static final int EXAM_NOT = 4;
    //试卷下载中
    public static final int EXAM_DOWNLOADING = 5;

    //练习类型
    //课前预习
    public static final int EXERCISE_BEFORE_CLASS = 3;
    //随堂测验
    public static final int EXERCISE_IN_CLASS = 4;
    //课后复习
    public static final int EXERCISE_AFTER_CLASS = 5;
    //

    //练习题对错状态
    //正确
    public static final int ANSWER_RIGHT = 1;
    //错误
    public static final int ANSWER_ERROR = 2;
    //未做
    public static final int ANSWER_NODONE = 3;

    /**
     * 考试页面
     * 跳轉傳值
     */
    public static final String SUBJECT_EXAM_ID = "SUBJECT_EXAM_ID";

    public static final String SUBJECT_DETAIL_ID = "SUBJECT_DETAIL_ID";

    /**
     * 自测页面跳转传值
     */
    public static final String SUBJECT_REVIEW_ID = "SUBJECT_REVIEW_ID";



    //题型
    //单选
    public static final int SUBJECT_SINGLE_CHOSE = 1;

    public static final String SUBJECT_SINGLE_CHOSE_STRING = "单选题";

    //多选
    public static final int SUBJECT_MULITI_CHOSE = 2;
    public static final String SUBJECT_MULITI_CHOSE_STRING = "多选题";

    //判断
    public static final int SUBJECT_JUDGE = 3;
    public static final String SUBJECT_JUDGE_STRING = "判断题";

    //实训
    public static final int SUBJECT_PRACTIAL = 4;
    public static final String SUBJECT_PRACTIAL_STRING = "实训题";

    //分录
    public static final int SUBJECT_ENTRY = 5;
    public static final String SUBJECT_ENTRY_STRING = "分录题";

    //凭证
    public static final int SUBJECT_BILL = 6;
    public static final String SUBJECT_BILL_STRING = "凭证题";

    //多组凭证
    public static final int SUBJECT_GROUP_BILL = 9;
    public static final String SUBJECT_GROUP_BILL_STRING = "分组凭证";

    //今天
    public static final int HISTORY_TODAY = 0;
    //昨天
    public static final int HISTORY_YESTODAY = 1;
    //很久很久以前
    public static final int HISTORY_AGO = 3;

    //题型难易程度
    //容易
    public static final float LEVEL_ORDINARY = 0f;
    //正常
    public static final float LEVEL_EASY = 0.5f;
    //困难
    public static final float LEVEL_HARD = 1f;

    //上传题目数量记录成功
    public static final int UPLOAD_TYPE = 21;
    //下载题目数量记录成功
    public static final int DOWNLOAD_TYPE = 22;
    public static final String S_SINGLE = "单选题";
    public static final String S_MULTI = "多选题";
    public static final String S_JUDGE = "判断题";
    public static final String S_FILLIN = "填空题";
    public static final String S_SHORTIN = "简答题";
    public static final String S_COMPREHENSIVE = "综合题";
    public static final String S_FORM = "表格题";
    public static final int SUB_SINGLE = 1;
    public static final int SUB_MULTI = 2;
    public static final int SUB_JUDGE = 3;
    public static final int SUB_FILLIN = 4;
    public static final int SUB_SHORTIN = 5;
    public static final int SUB_COMPREHENSIVE = 6;
    public static final int SUB_FORM = 22;
}
