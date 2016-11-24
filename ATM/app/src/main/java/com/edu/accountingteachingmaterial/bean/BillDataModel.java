package com.edu.accountingteachingmaterial.bean;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.edu.accountingteachingmaterial.subject.view.EntryView.EntryItem;
import com.edu.accountingteachingmaterial.util.ArrayUtil;
import com.edu.accountingteachingmaterial.util.StringFormatUtil;
import com.edu.library.data.BaseDataDao;

import java.util.List;

/**
 * 分录模块Bill_DATA表 数据处理模型封装
 *
 * @author edu_lx
 */

public class BillDataModel {

    private static final String TAG = "BillDataModel";

    // 自身实例
    private static BillDataModel instance;

    private Context mContext;

    public static synchronized BillDataModel getInstance(Context context) {
        if (instance == null) {
            instance = new BillDataModel(context);
        }
        return instance;
    }

    private BillDataModel(Context context) {
        this.mContext = context;
    }

    /**
     * 根据id获取对应的数据
     *
     * @param id
     * @return
     */
    public SubjectEntryData getDataById(int id) {
        return (SubjectEntryData) SubjectEntryDataDao.getInstance(mContext).getDataById(id);
    }

    // public List<BillData> getDataByIds(String ids){
    // return BillDataDao.getInstance(mContext).getDatasByBillDataIds(ids);
    // }

    /**
     * 获取ids指定id的所有数据
     *
     * @param ids
     * @return
     */
    public List<SubjectEntryData> getDatasIn(String ids, boolean cleanUserAnswer) {
        return SubjectEntryDataDao.getInstance(mContext).getDatasIn(ids, cleanUserAnswer);
    }

    /**
     * 获取ids指定id的所有数据
     *
     * @param ids
     * @return
     */
    public SubjectEntryData getDatasInTest(int ids, boolean cleanUserAnswer) {
        return SubjectEntryDataDao.getInstance(mContext).getDatasInTest(ids, cleanUserAnswer);
    }

    /**
     * 获取ids指定id的所有数据
     *
     * @param ids
     * @return
     */
    public SubjectEntryData setDatasInTest(int ids, boolean cleanUserAnswer) {
        return SubjectEntryDataDao.getInstance(mContext).getDatasInTest(ids, cleanUserAnswer);
    }

    /**
     * 保存用户答案到数据库
     *
     * @param id
     * @param borrow 借方答案
     * @param loan   贷方答案
     */
    public void saveUserAnswer(int id, String borrow, String loan) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.BORROWUSER, borrow);
        values.put(SubjectEntryDataDao.LOANUSER, loan);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
        System.out.println("borrow-->" + borrow + "***loan-->" + loan);
    }

    /**
     * 保存用户分数和是否正确
     *
     * @param uscore    用户分数
     * @param iscorrect 是否正确
     */
    public void updataUScoreAndIsCorrect(int id, float uscore, int iscorrect) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.USCORE, uscore);
        values.put(SubjectEntryDataDao.IS_CORRECT, iscorrect);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
    }

    /**
     * 用户答案置为空
     *
     * @param id
     * @param borrow
     * @param loan
     */
    public void updateUserAnswer(int id, String borrow, String loan) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.BORROWUSER, borrow);
        values.put(SubjectEntryDataDao.LOANUSER, loan);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
    }

    // /**
    // * 判断答案正确性
    // *
    // * @param borrowAnswerList
    // * 正确答案
    // * @param loanAnswerList
    // * @param items
    // * 用户答案
    // * @return 获取的分数
    // */
    // public float judeAnswer(List<String> borrowAnswerList, List<String>
    // loanAnswerList, List<EntryItem> itemList) {
    // float score = 0;
    // List<EntryItem> items = ArrayUtil.reverse(itemList);
    // // 寻找完全匹配的答案
    // label1: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAll(borrowAnswerList.get(j).trim(),
    // userAnswer.trim())) {
    // items.get(i).setUserAnswerCorrect(true, true, true);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label1;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAll(loanAnswerList.get(j).trim(),
    // userAnswer.trim())) {
    // items.get(i).setUserAnswerCorrect(true, true, true);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label1;
    // }
    // }
    // }
    //
    // }
    // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
    // items.size() == 0) {// 全答对，10分
    // score = 10;
    // return score;
    // }
    //
    // // 寻找一级科目和二级科目匹配的答案
    // label2: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsSubjects(borrowAnswerList.get(j), userAnswer))
    // {
    //
    // items.get(i).setUserAnswerCorrect(true, true, false);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label2;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsSubjects(loanAnswerList.get(j), userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, true, false);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label2;
    // }
    // }
    // }
    //
    // }
    // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
    // items.size() == 0) {// 5分
    // score = 5;
    // return score;
    // }
    //
    // // 寻找一级科目和金额匹配的答案
    // label3: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if
    // (StringFormatUtil.equalsAmountAndPrimarySubject(borrowAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, true);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label3;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAmountAndPrimarySubject(loanAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, true);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label3;
    // }
    // }
    // }
    // }
    //
    // // 寻找一级科目匹配的答案
    // label4: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsPrimarySubject(borrowAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, false);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label4;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsPrimarySubject(loanAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, false);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label4;
    // }
    // }
    // }
    // }
    //
    // // 剩下的全错情况
    // for (EntryItem item : items) {
    // item.setUserAnswerCorrect(false, false, false);
    // }
    //
    // return score;
    //
    // }

    /**
     * 判断答案正确性
     *
     * @param borrowAnswerList 正确答案
     * @param loanAnswerList
     * @return 获取的分数
     */
    public float judeAnswer(List<String> borrowAnswerList, List<String> loanAnswerList, List<EntryItem> itemList, float singleScore) {

        float score = 0;
        List<EntryItem> items = ArrayUtil.reverse(itemList);
        // 寻找完全匹配的答案
        label1:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAll(borrowAnswerList.get(j).trim(), userAnswer.trim())) {
                        items.get(i).setUserAnswerCorrect(true, true, true);
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label1;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAll(loanAnswerList.get(j).trim(), userAnswer.trim())) {
                        items.get(i).setUserAnswerCorrect(true, true, true);
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label1;
                    }
                }
            }

        }
        if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 && items.size() == 0) {// 全答对，10分
            score = singleScore;
            return score;
        }

        // 寻找一级科目和二级科目匹配的答案
        label2:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsSubjects(borrowAnswerList.get(j), userAnswer)) {

                        items.get(i).setUserAnswerCorrect(true, true, false);
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label2;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsSubjects(loanAnswerList.get(j), userAnswer)) {
                        items.get(i).setUserAnswerCorrect(true, true, false);
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label2;
                    }
                }
            }

        }
        if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 && items.size() == 0) {// 5分
            score = singleScore / 2;
            return score;
        }

        // 寻找一级科目和金额匹配的答案
        label3:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAmountAndPrimarySubject(borrowAnswerList.get(j), userAnswer)) {
                        items.get(i).setUserAnswerCorrect(true, false, true);
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label3;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAmountAndPrimarySubject(loanAnswerList.get(j), userAnswer)) {
                        items.get(i).setUserAnswerCorrect(true, false, true);
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label3;
                    }
                }
            }
        }

        // 寻找一级科目匹配的答案
        label4:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsPrimarySubject(borrowAnswerList.get(j), userAnswer)) {
                        items.get(i).setUserAnswerCorrect(true, false, false);
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label4;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsPrimarySubject(loanAnswerList.get(j), userAnswer)) {
                        items.get(i).setUserAnswerCorrect(true, false, false);
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label4;
                    }
                }
            }
        }

        // 剩下的全错情况
        for (EntryItem item : items) {
            item.setUserAnswerCorrect(false, false, false);
        }

        return score;

    }

    /**
     * 判断答案正确性
     *
     * @param borrowAnswerList 正确答案
     * @param loanAnswerList
     * @param items            用户答案
     * @return 获取的分数
     */
    public float getjudeAnswer(List<String> borrowAnswerList, List<String> loanAnswerList, List<EntryItem> itemList, float singleScore) {
        float score = 0;
        List<EntryItem> items = ArrayUtil.reverse(itemList);

        // 寻找完全匹配的答案
        label1:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAll(borrowAnswerList.get(j).trim(), userAnswer.trim())) {
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label1;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAll(loanAnswerList.get(j).trim(), userAnswer.trim())) {
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label1;
                    }
                }
            }

        }
        if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 && items.size() == 0) {// 全答对，10分

            score = singleScore;
            return score;
        }

        // 寻找一级科目和二级科目匹配的答案
        label2:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsSubjects(borrowAnswerList.get(j), userAnswer)) {
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label2;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsSubjects(loanAnswerList.get(j), userAnswer)) {
                        // items.get(i).setUserAnswerCorrect(true, true, false);
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label2;
                    }
                }
            }

        }
        if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 && items.size() == 0) {// 5分
            score = singleScore / 2;
            return score;
        }

        // 寻找一级科目和金额匹配的答案
        label3:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAmountAndPrimarySubject(borrowAnswerList.get(j), userAnswer)) {
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label3;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsAmountAndPrimarySubject(loanAnswerList.get(j), userAnswer)) {
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label3;
                    }
                }
            }
        }

        // 寻找一级科目匹配的答案
        label4:
        for (int i = items.size() - 1; i >= 0; i--) {
            String userAnswer = items.get(i).getUserAnswer();
            if (items.get(i).mType == EntryItem.TYPE_BORROW) {
                for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsPrimarySubject(borrowAnswerList.get(j), userAnswer)) {
                        Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        borrowAnswerList.remove(borrowAnswerList.get(j));

                        continue label4;
                    }
                }
            } else {
                for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
                    if (StringFormatUtil.equalsPrimarySubject(loanAnswerList.get(j), userAnswer)) {
                        Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                        items.remove(items.get(i));
                        loanAnswerList.remove(loanAnswerList.get(j));

                        continue label4;
                    }
                }
            }
        }

        return score;
    }

    // /**
    // * 判断答案正确性
    // *
    // * @param borrowAnswerList
    // * 正确答案
    // * @param loanAnswerList
    // * @param items
    // * 用户答案
    // * @return 获取的分数
    // */
    // public float judeAnswerForTest(List<String> borrowAnswerList,
    // List<String> loanAnswerList, List<EntryItems> itemList, float
    // singleScore) {
    // float score = 0;
    // List<EntryItems> items = ArrayUtil.reverseT(itemList);
    // // 寻找完全匹配的答案
    // label1: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAll(borrowAnswerList.get(j).trim(),
    // userAnswer.trim())) {
    // items.get(i).setUserAnswerCorrect(true, true, true);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label1;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAll(loanAnswerList.get(j).trim(),
    // userAnswer.trim())) {
    // items.get(i).setUserAnswerCorrect(true, true, true);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label1;
    // }
    // }
    // }
    //
    // }
    // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
    // items.size() == 0) {// 全答对，10分
    // score = singleScore;
    // return score;
    // }
    //
    // // 寻找一级科目和二级科目匹配的答案
    // label2: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsSubjects(borrowAnswerList.get(j), userAnswer))
    // {
    //
    // items.get(i).setUserAnswerCorrect(true, true, false);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label2;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsSubjects(loanAnswerList.get(j), userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, true, false);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label2;
    // }
    // }
    // }
    //
    // }
    // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
    // items.size() == 0) {// 5分
    // score = singleScore / 2;
    // return score;
    // }
    //
    // // 寻找一级科目和金额匹配的答案
    // label3: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if
    // (StringFormatUtil.equalsAmountAndPrimarySubject(borrowAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, true);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label3;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAmountAndPrimarySubject(loanAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, true);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label3;
    // }
    // }
    // }
    // }
    //
    // // 寻找一级科目匹配的答案
    // label4: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsPrimarySubject(borrowAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, false);
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label4;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsPrimarySubject(loanAnswerList.get(j),
    // userAnswer)) {
    // items.get(i).setUserAnswerCorrect(true, false, false);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label4;
    // }
    // }
    // }
    // }
    //
    // // 剩下的全错情况
    // for (EntryItems item : items) {
    // item.setUserAnswerCorrect(false, false, false);
    // }
    //
    // return score;
    // }

    // /**
    // * 得分
    // *
    // * @param borrowAnswerList
    // * 正确答案
    // * @param loanAnswerList
    // * @param items
    // * 用户答案
    // * @return 获取的分数
    // */
    // public float getScore(List<String> borrowAnswerList, List<String>
    // loanAnswerList, List<EntryItems> itemList, float totalScore) {
    // float score = 0;
    // List<EntryItems> items = ArrayUtil.reverseT(itemList);
    //
    // // 寻找完全匹配的答案
    // label1: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAll(borrowAnswerList.get(j).trim(),
    // userAnswer.trim())) {
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label1;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAll(loanAnswerList.get(j).trim(),
    // userAnswer.trim())) {
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label1;
    // }
    // }
    // }
    //
    // }
    // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
    // items.size() == 0) {// 全答对，10分
    //
    // score = totalScore;
    // return score;
    // }
    //
    // // 寻找一级科目和二级科目匹配的答案
    // label2: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsSubjects(borrowAnswerList.get(j), userAnswer))
    // {
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label2;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsSubjects(loanAnswerList.get(j), userAnswer)) {
    // // items.get(i).setUserAnswerCorrect(true, true, false);
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label2;
    // }
    // }
    // }
    //
    // }
    // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
    // items.size() == 0) {// 5分
    // score = totalScore / 2;
    // return score;
    // }
    //
    // // 寻找一级科目和金额匹配的答案
    // label3: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if
    // (StringFormatUtil.equalsAmountAndPrimarySubject(borrowAnswerList.get(j),
    // userAnswer)) {
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label3;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsAmountAndPrimarySubject(loanAnswerList.get(j),
    // userAnswer)) {
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label3;
    // }
    // }
    // }
    // }
    //
    // // 寻找一级科目匹配的答案
    // label4: for (int i = items.size() - 1; i >= 0; i--) {
    // String userAnswer = items.get(i).getUserAnswer();
    // if (items.get(i).mType == EntryItems.TYPE_BORROW) {
    // for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsPrimarySubject(borrowAnswerList.get(j),
    // userAnswer)) {
    // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // borrowAnswerList.remove(borrowAnswerList.get(j));
    //
    // continue label4;
    // }
    // }
    // } else {
    // for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
    // if (StringFormatUtil.equalsPrimarySubject(loanAnswerList.get(j),
    // userAnswer)) {
    // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
    // userAnswer);
    // items.remove(items.get(i));
    // loanAnswerList.remove(loanAnswerList.get(j));
    //
    // continue label4;
    // }
    // }
    // }
    // }
    //
    // return score;
    // }

    /**
     * 得分 临时方法（二级科目不进行判断）
     *
     * @param borrowAnswerList 正确答案
     * @param loanAnswerList
     * @return 获取的分数
     */
    public float getResultScore(List<String> borrowAnswerList, List<String> loanAnswerList, List<String> userBorrowAnswerList, List<String> userLoanAnswerList, int type, float totalScore) {
        float score = 0;

        // 判断金额,一级科目是否都相同
        label3:
        for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
            if (j < borrowAnswerList.size() && j < userBorrowAnswerList.size()) {
                String userAnswer = userBorrowAnswerList.get(j).trim();
                if (StringFormatUtil.equalsAmountAndPrimarySubject(borrowAnswerList.get(j), userAnswer.trim())) {
                    Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                    borrowAnswerList.remove(borrowAnswerList.get(j));
                    userBorrowAnswerList.remove(userBorrowAnswerList.get(j));
                    continue label3;
                }
            }
        }
        label3:
        for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
            if (j < loanAnswerList.size() && j < userLoanAnswerList.size()) {
                String userAnswer = userLoanAnswerList.get(j).trim();
                if (StringFormatUtil.equalsAmountAndPrimarySubject(loanAnswerList.get(j), userAnswer.trim())) {
                    Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                    loanAnswerList.remove(loanAnswerList.get(j));
                    userLoanAnswerList.remove(userLoanAnswerList.get(j));
                    continue label3;
                }
            }
        }
        if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 && userLoanAnswerList.size() == 0 && userBorrowAnswerList.size() == 0) {// 5分
            score = totalScore;
            return score;
        }

        // 寻找一级科目匹配的答案
        label4:
        for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
            if (j < borrowAnswerList.size() && j < userBorrowAnswerList.size()) {
                String userAnswer = userBorrowAnswerList.get(j).trim();
                if (StringFormatUtil.equalsPrimarySubject(borrowAnswerList.get(j), userAnswer.trim())) {
                    Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" + userAnswer);
                    borrowAnswerList.remove(borrowAnswerList.get(j));
                    userBorrowAnswerList.remove(userBorrowAnswerList.get(j));
                    continue label4;
                }
            }
        }
        label4:
        for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
            if (j < loanAnswerList.size() && j < userLoanAnswerList.size()) {
                String userAnswer = userLoanAnswerList.get(j).trim();
                if (StringFormatUtil.equalsPrimarySubject(loanAnswerList.get(j), userAnswer.trim())) {
                    Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" + userAnswer);
                    loanAnswerList.remove(loanAnswerList.get(j));
                    userLoanAnswerList.remove(userLoanAnswerList.get(j));
                    continue label4;
                }
            }
        }

        if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 && userLoanAnswerList.size() == 0 && userBorrowAnswerList.size() == 0) {// 5分
            score = totalScore / 2;
            return score;
        }

        // // 寻找金额匹配的答案
        // label5: for (int j = borrowAnswerList.size() - 1; j >= 0; j--) {
        // if (j < borrowAnswerList.size() && j < userBorrowAnswerList.size()) {
        // String userAnswer = userBorrowAnswerList.get(j).trim();
        // if (StringFormatUtil.equalsAmount(borrowAnswerList.get(j),
        // userAnswer.trim())) {
        // Log.d(TAG, "answer:" + borrowAnswerList.get(j) + ",userAnswer:" +
        // userAnswer);
        // borrowAnswerList.remove(borrowAnswerList.get(j));
        // userBorrowAnswerList.remove(userBorrowAnswerList.get(j));
        // continue label5;
        // }
        // }
        // }
        // label5: for (int j = loanAnswerList.size() - 1; j >= 0; j--) {
        // if (j < loanAnswerList.size() && j < userLoanAnswerList.size()) {
        // String userAnswer = userLoanAnswerList.get(j).trim();
        // if (StringFormatUtil.equalsAmount(loanAnswerList.get(j),
        // userAnswer.trim())) {
        // Log.d(TAG, "answer:" + loanAnswerList.get(j) + ",userAnswer:" +
        // userAnswer);
        // loanAnswerList.remove(loanAnswerList.get(j));
        // userLoanAnswerList.remove(userLoanAnswerList.get(j));
        // continue label5;
        // }
        // }
        // }
        //
        // if (loanAnswerList.size() == 0 && borrowAnswerList.size() == 0 &&
        // userLoanAnswerList.size() == 0 && userBorrowAnswerList.size() == 0)
        // {// 5分
        // score = totalScore / 2;
        // return score;
        // }

        return score;
    }

    /**
     * 获取所有id号
     *
     * @return
     */
    public List<Integer> getAllIds() {
        return SubjectEntryDataDao.getInstance(mContext).getAllIds();
    }

    /*
     * * 获取所有id
     *
     * @return
     */
    public List<SubjectEntryData> getAllIDatasByType(int chapterId) {
        return SubjectEntryDataDao.getInstance(mContext).getAllIDatasByType(chapterId);
    }

    /**
     * 根据id获取数据
     *
     * @return
     */
    public SubjectEntryData getEntryData(int id) {
        return SubjectEntryDataDao.getInstance(mContext).getEntryData(id);
    }

    // /**
    // * 插入数据
    // */
    // public int insertData(int chapterId, TopicMVO topic) {
    // ContentValues values = new ContentValues();
    // values.put(SubjectEntryDataDao.SERVER_ID, topic.getTopicId().intValue());
    // values.put(SubjectEntryDataDao.CHAPTER_ID, chapterId);
    //
    // String questionAndAnswer = Img64TagHandler.getFromHtmlValues(mContext,
    // topic.getRichQuestion().getText());
    // String questionOrAnswers[] = questionAndAnswer.split("@@@");
    // values.put(SubjectEntryDataDao.QUESTION, questionOrAnswers[0]);
    // StringBuffer image = new StringBuffer();
    // if (topic.getRichQuestion().getImgSrc() != null &&
    // topic.getRichQuestion().getImgSrc().size() > 0) {
    // for (int i = 0; i < topic.getRichQuestion().getImgSrc().size(); i++) {
    // image.append(topic.getRichQuestion().getImgSrc().get(i) + "&");
    // }
    // }
    // if (image != null && image.length() > 0) {
    // values.put(SubjectEntryDataDao.PICNAME, image.substring(0, image.length()
    // - 1));// "pic_name"
    // }
    // // 用###分割借和贷
    // // 38=-1=4750&&&23=-1=4750###18=16=4750&&&38=-1=4750
    //
    // // String loanansAndBorrow =
    // // topic.getRichAnswerMVO().get(0).getRichContent().getText();
    // // loanansAndBorrow = Img64TagHandler.getFromHtmlValues(mContext,
    // // loanansAndBorrow);
    // if (questionOrAnswers.length > 1) {
    // String loanansAndBorrow = questionOrAnswers[1];
    // if (loanansAndBorrow != null) {
    // String[] loanansAndBorrows = loanansAndBorrow.split("###");
    // if (loanansAndBorrows.length > 1) {
    // values.put(SubjectEntryDataDao.BORROWANS, loanansAndBorrows[0]);
    // values.put(SubjectEntryDataDao.LOANANS, loanansAndBorrows[1]);
    // }
    // }
    // }
    //
    // values.put(SubjectEntryDataDao.BORROWUSER, "");
    // values.put(SubjectEntryDataDao.LOANUSER, "");
    // values.put(SubjectEntryDataDao.IS_CORRECT, topic.getIsCorrect());
    // values.put(SubjectEntryDataDao.SCORE, topic.getScore());
    // values.put(SubjectEntryDataDao.USCORE, topic.getUscore());
    // values.put(SubjectEntryDataDao.TYPE, 0);// topic.getTopicType() //
    // // 普通：0，子题：1，父题：2
    // values.put(SubjectEntryDataDao.CHILDREN, "");
    // values.put(BaseDataDao.REMARK, "REMARK");
    //
    // SubjectEntryDataDao.getInstance(mContext).insertData(values);
    //
    // // 一级科目和二级科目应保存到对应的表中?? 有重复添加问题
    // // FirstSubjectDataModel.getInstance(mContext).insertData(topic);
    // // SecondSubjectDataModel.getInstance(mContext).insertData(topic);
    //
    // return (int) SubjectEntryDataDao.getInstance(mContext).getMaxItemId();
    // }

    /**
     * 插入数据
     */
    public void insertData(int server_id, int chapter_id, String question, String pic_name, String borrow, String loan, String borrow_user, String loan_user, int isCorrect, Float score, Float uScore,
                           int type, String children) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.SERVER_ID, server_id);
        values.put(SubjectEntryDataDao.CHAPTER_ID, chapter_id);
        values.put(SubjectEntryDataDao.QUESTION, question);
        values.put(SubjectEntryDataDao.PICNAME, pic_name);
        values.put(SubjectEntryDataDao.BORROWANS, borrow);
        values.put(SubjectEntryDataDao.LOANANS, loan);
        values.put(SubjectEntryDataDao.BORROWUSER, borrow_user);
        values.put(SubjectEntryDataDao.LOANUSER, loan_user);
        values.put(SubjectEntryDataDao.IS_CORRECT, isCorrect);
        values.put(SubjectEntryDataDao.SCORE, score);
        values.put(SubjectEntryDataDao.USCORE, uScore);
        values.put(SubjectEntryDataDao.TYPE, type);
        values.put(SubjectEntryDataDao.CHILDREN, children);
        SubjectEntryDataDao.getInstance(mContext).insertData(values);
    }

    /**
     * 获取最后一条数据
     *
     * @param chapterId
     * @param type
     * @param server_id
     * @return
     */
    public SubjectEntryData getDataLast(int chapterId, int type, int server_id) {
        return SubjectEntryDataDao.getInstance(mContext).getDataLast(chapterId, type, server_id);
    }

    /**
     * 更新 最新id的children字段内容
     */
    public void updateChildContent(int id, String children) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.CHILDREN, children);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
    }

    /**
     * 拷贝数据
     *
     * @param id
     */
    public void getCopyData(int id, int createServerId) {
        SubjectEntryDataDao.getInstance(mContext).getCopyData(id, createServerId);
    }

    /**
     * 获取最后一条
     */
    public SubjectEntryData getLastData() {
        return SubjectEntryDataDao.getInstance(mContext).getDataLast();
    }

    /**
     * 更新对应remark，标记是否做过
     *
     * @param id
     * @param isDone
     */
    public void updateRemark(int id, String isDone) {
        ContentValues values = new ContentValues();
        values.put(BaseDataDao.REMARK, isDone);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
    }

    /**
     * 更新用户分数
     *
     * @param id
     * @param score
     */
    public void updateScore(int id, float score) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.USCORE, score);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
    }

    /**
     * 清除数据
     *
     * @param id
     * @param borrow_user
     * @param loan_user
     * @param isCorrect
     * @param uScore
     */
    public void updateContent(int id, String borrow_user, String loan_user, int isCorrect, Float uScore) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.BORROWUSER, borrow_user);
        values.put(SubjectEntryDataDao.LOANUSER, loan_user);
        values.put(SubjectEntryDataDao.IS_CORRECT, isCorrect);
        values.put(SubjectEntryDataDao.USCORE, uScore);
        SubjectEntryDataDao.getInstance(mContext).updateData(String.valueOf(id), values);
    }

    /**
     * 清除数据
     *
     * @param id
     * @param borrow_user
     * @param loan_user
     * @param isCorrect
     * @param uScore
     */
    public void updateContentS(String id, String borrow_user, String loan_user, int isCorrect, Float uScore) {
        ContentValues values = new ContentValues();
        values.put(SubjectEntryDataDao.BORROWUSER, borrow_user);
        values.put(SubjectEntryDataDao.LOANUSER, loan_user);
        values.put(SubjectEntryDataDao.IS_CORRECT, isCorrect);
        values.put(SubjectEntryDataDao.USCORE, uScore);
        SubjectEntryDataDao.getInstance(mContext).updateDataS(id, values);
    }

}