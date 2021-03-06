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

    //试卷当前状态
    //试卷已提交
    public static final int EXAM_COMMIT = 1;
    //试卷未提交
    public static final int EXMA_UNDONE = 2;
    //试卷已被批阅
    public static final int EXAM_READ = 3;
    //试卷未下载
    public static final int EXAM_NOT = 4;
    //试卷下载中
    public static final int EXAM_DOWNLOADING = 5;

    //练习类型
    //课前预习
    public static final int EXERCISE_BEFORE_CLASS = 1;
    //随堂测验
    public static final int EXERCISE_IN_CLASS = 2;
    //课后复习
    public static final int EXERCISE_AFTER_CLASS = 3;

    //练习题对错状态
    //正确
    public static final int ANSWER_RIGHT = 1;
    //错误
    public static final int ANSWER_ERROR = 2;
    //未做
    public static final int ANSWER_NODONE = 3;



    //题型
    //单选
    public static final int SUBJECT_SINGLE_CHOSE = 1;

    public static final String SUBJECT_SINGLE_CHOSE_STRING = "单选题";

    //多选
    public static final int SUBJECT_MULITI_CHOSE = 2;
    public static final String SUBJECT_MULITI_CHOSE_STRING = "多选题";

    //判断
    public static final int SUBJECT_JUDGE =3;
    public static final String SUBJECT_JUDGE_STRING = "判断题";

    //实训
    public static final int SUBJECT_PRACTIAL =4;
    public static final String SUBJECT_PRACTIAL_STRING = "实训题";

    //分录
    public static final int SUBJECT_ENTRY = 5;
    public static final String SUBJECT_ENTRY_STRING = "分录题";

    //凭证
    public static final int SUBJECT_BILL = 6;
    public static final String SUBJECT_BILL_STRING = "凭证题";

    //多组凭证
    public static final int SUBJECT_GROUP_BILL = 7;
    public static final String SUBJECT_GROUP_BILL_STRING = "分组凭证";










}
