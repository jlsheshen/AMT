package com.edu.accountingteachingmaterial.util;

/**
 * Created by Administrator on 2017/5/9.
 */

public class SplitChapterIdUtil {
    public static String spliterId(String chapterLocalId,String userId){

        int i = userId.length();
        String chapterId =chapterLocalId.substring(0,chapterLocalId.length()-i);
//        String sendExamId[] = (String.valueOf(chapterId)).split(userId);
        return chapterId;

    }

}
