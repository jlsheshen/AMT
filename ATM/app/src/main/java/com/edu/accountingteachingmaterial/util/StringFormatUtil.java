package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;

import com.edu.accountingteachingmaterial.bean.FirstSubjectDataModel;

import java.util.List;


/**
 * 改变字符串样式的工具，主要用于改变某些字符串中特定字符颜色或字体
 *
 * @author lucher
 */
public class StringFormatUtil {

    private static final String TAG = "StringFormatUtil";

    /**
     * 给指定字符串中间加指定分割符号后转为字符串
     *
     * @param body   待转字符串
     * @param symbol 添加的字符
     * @return
     */
    public static String addDivide(String body, String symbol) {
        if (body.length() <= 1) {
            return body;
        }

        String[] arr = body.split("");
        StringBuilder builder = new StringBuilder();
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            builder.append(arr[i]);
            if (i != length - 1) {
                builder.append(symbol);
            }
        }

        Log.d(TAG, body + "..." + builder.toString());
        return builder.toString();
    }

    /**
     * 给指定list中间加指定分割符号后转为字符串
     *
     * @param arr    待转数组
     * @param symbol 添加的字符
     * @return
     */
    public static String addDivide(List<Integer> list, String symbol) {
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            builder.append(list.get(i));
            if (i != size - 1) {
                builder.append(symbol);
            }
        }

        Log.d(TAG, list + "..." + builder.toString());
        return builder.toString();
    }

    /**
     * 按指定长度截取字符串，且在后面添加...
     *
     * @param builder
     * @param length
     * @return
     */
    public static String cutString(StringBuilder builder, int length) {
        if (builder.length() > length) {
            builder.replace(length, builder.toString().length(), "...");
        }

        if (builder.toString().endsWith("、")) {
            builder.replace(builder.toString().length() - 1, builder.toString().length(), "");
        }

        return builder.toString();
    }

    /**
     * 解析问题字符串
     *
     * @param question
     * @return 返回一个二维数组，数组的长度代表有几个资产类别，每个一维数组长度为2，第一个元素为科目类别的id，第二个元素代表该科目类别的数量
     */
    public static int[][] parseQuestion(String question) {
        try {
            String[] categorys = question.split(",");// 解析出有几个科目类别
            int[][] result = new int[categorys.length][2];
            for (int i = 0; i < categorys.length; i++) {
                // 解析出科目类别以及对应的数量
                String[] tmp = categorys[i].split("-");
                result[i][0] = Integer.parseInt(tmp[0].trim());
                result[i][1] = Integer.parseInt(tmp[1].trim());
                Log.d(TAG, i + "-tmp[0]:" + tmp[0] + ",tmp[1]:" + tmp[1]);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 以指定格式获取正确答案
     *
     * @return
     */
    public static String getCorrectAnswer(Context context, List<String> borrows, List<String> loans) {
        StringBuilder builder = new StringBuilder("借：");
        for (int i = 0; i < borrows.size(); i++) {
            if (i == 0) {
                builder.append(getItemAnswer(context, borrows.get(i).split("=")));
            } else {
                builder.append("                           " + getItemAnswer(context, borrows.get(i).split("=")));
            }
        }
        builder.append("                       贷：");
        for (int i = 0; i < loans.size(); i++) {
            if (i == 0) {
                builder.append(getItemAnswer(context, loans.get(i).split("=")));
            } else {
                builder.append("                               " + getItemAnswer(context, loans.get(i).split("=")));
            }
        }

        return builder.toString();
    }

    /**
     * 以指定格式获取用户答题标记对应的中文答案
     *
     * @return
     */
    public static String getAnsFromFlag(Context context, List<String> borrows, List<String> loans) {
        StringBuilder builder = new StringBuilder("借：");
        for (int i = 0; i < borrows.size(); i++) {
            if (i == 0) {
                builder.append(getItemFromFlag(context, borrows.get(i).split("=")));
            } else {
                builder.append("   " + getItemFromFlag(context, borrows.get(i).split("=")));
            }
        }
        builder.append("&nbsp;&nbsp;&nbsp;&nbsp;贷：");
        for (int i = 0; i < loans.size(); i++) {
            if (i == 0) {
                builder.append(getItemFromFlag(context, loans.get(i).split("=")));
            } else {
                builder.append("   " + getItemFromFlag(context, loans.get(i).split("=")));
            }
        }

        return builder.toString();
    }

    /**
     * 按照格式获取item的正确答案
     *
     * @param context
     * @param answer
     * @return
     */
    private static String getItemFromFlag(Context context, String[] answer) {
        StringBuilder builder = new StringBuilder();
        if (!answer[0].equals("")) {
            int primaryId = Integer.parseInt(answer[0]);
            if (primaryId != -1) {
                builder.append(FirstSubjectDataModel.getInstance(context).getDataById(primaryId).getName() + "——");
            }

            int secondId = Integer.parseInt(answer[1]);
            if (secondId != -1) {
                builder.append(SecondSubjectDataModel.getInstance(context).getDataById(secondId).getName() + "——");
            }
            if (answer.length == 3) {
                // int amount = 0;
                String amount = null;
                try {
                    // amount = Integer.parseInt(answer[2]);
                    amount = answer[2].toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                builder.append(amount + "\n" + "<br>");
            }
        }
        return builder.toString();
    }

    /**
     * 按照格式获取item的正确答案
     *
     * @param context
     * @param answer
     * @return
     */
    private static String getItemAnswer(Context context, String[] answer) {
        StringBuilder builder = new StringBuilder();
        if (!answer[0].equals("")) {
            int primaryId = Integer.parseInt(answer[0]);
            if (primaryId != -1) {
                builder.append(FirstSubjectDataModel.getInstance(context).getDataById(primaryId).getName() + "——");
            }

            int secondId = Integer.parseInt(answer[1]);
            if (secondId != -1) {
                builder.append(SecondSubjectDataModel.getInstance(context).getDataById(secondId).getName() + "——");
            }
            if (answer.length == 3) {
                // int amount = 0;
                String amount = null;
                try {
                    amount = answer[2].toString();
                    // amount = Integer.parseInt(answer[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                builder.append(amount + "\n");
            }
        }
        return builder.toString();
    }

    /**
     * 判断一级科目和二级科目以及金额是否都相同
     *
     * @param answer
     * @param userAnswer
     * @return
     */
    public static boolean equalsAll(String answer, String userAnswer) {
        String[] answers = answer.split("=");
        String[] userAnswers = userAnswer.split("=");
        if (userAnswers.length < 3 || answers.length < 3) {
            return false;
        }
        if (answers[0].trim().equals(userAnswers[0].trim()) && answers[1].trim().equals(userAnswers[1].trim())) {
            float amount = Float.valueOf(answers[2]);
            float userAmount = -1;
            try {
                userAmount = Float.valueOf(userAnswers[2]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (amount == userAmount) {
                return true;
            }

        }

        return false;
    }

    /**
     * 判断一级科目和二级科目是否都相同
     *
     * @param answer
     * @param userAnswer
     * @return
     */
    public static boolean equalsSubjects(String answer, String userAnswer) {
        String[] answers = answer.split("=");
        String[] userAnswers = userAnswer.split("=");
        if (answers[0].trim().equals(userAnswers[0].trim()) && answers[1].trim().equals(userAnswers[1].trim())) {
            return true;
        }

        return false;
    }

    /**
     * 判断一级科目是否都相同
     *
     * @param answer
     * @param userAnswer
     * @return
     */
    public static boolean equalsPrimarySubject(String answer, String userAnswer) {
        String[] answers = answer.split("=");
        String[] userAnswers = userAnswer.split("=");
        if (answers[0].trim().equals(userAnswers[0].trim())) {
            return true;
        }

        return false;
    }

    /**
     * 判断金额是否都相同
     *
     * @param answer
     * @param userAnswer
     * @return
     */
    public static boolean equalsAmount(String answer, String userAnswer) {
        String[] answers = answer.split("=");
        String[] userAnswers = userAnswer.split("=");
        if (userAnswers.length > 2) {
            float amount = Float.valueOf(answers[2]);
            float userAmount = -1;
            try {
                userAmount = Float.valueOf(userAnswers[2]);
            } catch (Exception e) {
                return false;
            }

            if (amount == userAmount) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断金额,一级科目是否都相同
     *
     * @param answer
     * @param userAnswer
     * @return
     */
    public static boolean equalsAmountAndPrimarySubject(String answer, String userAnswer) {
        String[] answers = answer.split("=");
        String[] userAnswers = userAnswer.split("=");
        if (userAnswers.length < answers.length) {
            return false;
        }
        float amount = Float.valueOf(answers[2]);
        float userAmount = -1;
        try {
            userAmount = Float.valueOf(userAnswers[2]);
        } catch (Exception e) {
            return false;
        }
        if (amount == userAmount && answers[0].trim().equals(userAnswers[0].trim())) {
            return true;
        }
        return false;
    }
}
