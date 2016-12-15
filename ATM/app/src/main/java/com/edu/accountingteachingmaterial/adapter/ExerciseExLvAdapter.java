//package com.edu.accountingteachingmaterial.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.edu.accountingteachingmaterial.R;
//import com.edu.accountingteachingmaterial.constant.ClassContstant;
//import com.edu.accountingteachingmaterial.entity.ExamListData;
//import com.edu.subject.SubjectState;
//import com.edu.subject.data.BaseTestData;
//import com.zhy.autolayout.utils.AutoUtils;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2016/11/9.
// */
//public class ExerciseExLvAdapter extends BaseExpandableListAdapter {
//
//
//    Context context;
//    List<ExamListData> datas;
//
//    public ExerciseExLvAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void setDatas(List<ExamListData> datas) {
//        this.datas = datas;
//        notifyDataSetChanged();
//    }
//
//
//
//    @Override
//    public int getGroupCount() {
//        return datas == null ? 0 : datas.size();
//    }
//
//    @Override
//    public int getChildrenCount(int i) {
//        return datas.get(i).getTestList() == null ? 0 : datas.get(i).getTestList().size();
//    }
//
//    @Override
//    public Object getGroup(int i) {
//        return null;
//    }
//
//    @Override
//    public Object getChild(int i, int i1) {
//        return null;
//    }
//
//    @Override
//    public long getGroupId(int i) {
//        return datas.get(i).getId();
//    }
//
//    @Override
//    public long getChildId(int i, int i1) {
//        return Long.parseLong(datas.get(i).getTestList().get(i1).getSubjectId());
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
//
//    @Override
//    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
//        GroupViewHolder groupViewHolder = null;
//        if (view == null || view.getTag(R.id.tag_group) == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_exlv, viewGroup, false);
//            AutoUtils.autoSize(view);
//
//            groupViewHolder = new GroupViewHolder(view);
//            view.setTag(R.id.tag_group,groupViewHolder);
//        } else {
//            groupViewHolder = (GroupViewHolder) view.getTag(R.id.tag_group);
//        }
//        ExamListData exerciseBean = datas.get(i);
////        groupViewHolder.titleTv.setText("" + exerciseBean.getExam_name());
//        groupViewHolder.testNumTv.setText("" + exerciseBean.getTopic_num());
//        groupViewHolder.textimeTv.setText("");
//
//        switch (exerciseBean.getLesson_type()) {
//            case ClassContstant.EXERCISE_BEFORE_CLASS:
//
//                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_keqian);
//                groupViewHolder.titleTv.setText("课前预习:" + exerciseBean.getExam_name());
//                break;
//            case ClassContstant.EXERCISE_IN_CLASS:
//                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_suitang);
//                groupViewHolder.titleTv.setText("随堂测验:" + exerciseBean.getExam_name());
//                break;
//            case ClassContstant.EXERCISE_AFTER_CLASS:
//                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_kehou);
//                groupViewHolder.titleTv.setText("课后作业:" + exerciseBean.getExam_name());
//                break;
//        }
//        switch (exerciseBean.getState()) {
//            case ClassContstant.EXAM_COMMIT:
//                if (exerciseBean.getLesson_type() != ClassContstant.EXERCISE_IN_CLASS){
//                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_commit_type);}
//                else {
//                    groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
//                }
//                break;
//            case ClassContstant.EXAM_NOT:
//                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_download_type);
//                break;
//            case ClassContstant.EXAM_READ:
//                if (exerciseBean.getLesson_type() != ClassContstant.EXERCISE_IN_CLASS){
//                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_read_type);}
//                else {
//                    groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
//                }
//                break;
//            case ClassContstant.EXAM_UNDONE:
//                groupViewHolder.progressBar.setVisibility(View.GONE);
//                if (exerciseBean.getLesson_type() != ClassContstant.EXERCISE_IN_CLASS){
//                    groupViewHolder.stautsIv.setVisibility(View.VISIBLE);
//                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_undown_type);}
//                else {
//                    groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
//                }
//
//                break;
//            default:
//                groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
//        break;
//        }
//        return view;
//    }
//
//    @Override
//    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
//        ChildViewHolder childViewHolder = null;
//        if (view == null || view.getTag(R.id.tag_child) == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_practice_exlv, viewGroup, false);
//            AutoUtils.autoSize(view);
//
//            childViewHolder = new ChildViewHolder(view);
//            view.setTag(R.id.tag_child, childViewHolder);
//        } else {
//            childViewHolder = (ChildViewHolder) view.getTag(R.id.tag_child);
//        }
//
//        BaseTestData exercisePracticeBean = datas.get(i).getTestList().get(i1);
//        childViewHolder.contentTv.setText(exercisePracticeBean.getSubjectData().getQuestion());
//        String s = null;
//        switch (exercisePracticeBean.getSubjectType()) {
//            case ClassContstant.SUBJECT_SINGLE_CHOSE:
//                s = ClassContstant.SUBJECT_SINGLE_CHOSE_STRING;
//                break;
//            case ClassContstant.SUBJECT_MULITI_CHOSE:
//                s = ClassContstant.SUBJECT_MULITI_CHOSE_STRING;
//                break;
//            case ClassContstant.SUBJECT_JUDGE:
//                s = ClassContstant.SUBJECT_JUDGE_STRING;
//                break;
//            case ClassContstant.SUBJECT_PRACTIAL:
//                s = ClassContstant.SUBJECT_PRACTIAL_STRING;
//                break;
//            case ClassContstant.SUBJECT_ENTRY:
//                s = ClassContstant.SUBJECT_ENTRY_STRING;
//                break;
//            case ClassContstant.SUBJECT_BILL:
//                s = ClassContstant.SUBJECT_BILL_STRING;
//                break;
//            case ClassContstant.SUBJECT_GROUP_BILL:
//                s = ClassContstant.SUBJECT_GROUP_BILL_STRING;
//                break;
//        }
//        childViewHolder.numTv.setText(exercisePracticeBean.getSubjectIndex() + " " + s);
//        switch (exercisePracticeBean.getState()) {
//            case SubjectState.STATE_CORRECT:
//                childViewHolder.isRightIv.setImageResource(R.mipmap.icon_dui_n);
//                childViewHolder.isRightIv.setVisibility(View.VISIBLE);
//                break;
//            case SubjectState.STATE_WRONG:
//                childViewHolder.isRightIv.setImageResource(R.mipmap.icon_cuo_n);
//                childViewHolder.isRightIv.setVisibility(View.VISIBLE);
//
//                break;
//            default:
//                childViewHolder.isRightIv.setVisibility(View.INVISIBLE);
//
//                break;
//        }
//        return view;
//    }
//    @Override
//    public boolean isChildSelectable(int i, int i1) {
//        return true;
//    }
//
//
//    class GroupViewHolder {
//        TextView titleTv, textimeTv, testNumTv;
//        ImageView headIv, stautsIv;
//        ProgressBar progressBar;
//        public GroupViewHolder(View view) {
//            titleTv = (TextView) view.findViewById(R.id.item_exercise_title_tv);
//            textimeTv = (TextView) view.findViewById(R.id.item_exercise_time_tv);
//            testNumTv = (TextView) view.findViewById(R.id.item_exercise_testnum_tv);
//            headIv = (ImageView) view.findViewById(R.id.item_exercise_head_iv);
//            stautsIv = (ImageView) view.findViewById(R.id.item_exercise_type_iv);
//            progressBar = (ProgressBar) view.findViewById(R.id.item_exercise_type_pb);
//
//        }
//    }
//
//    class ChildViewHolder {
//        TextView numTv, contentTv;
//        ImageView isRightIv;
//
//        public ChildViewHolder(View view) {
//            numTv = (TextView) view.findViewById(R.id.item_practice_number_tv);
//            contentTv = (TextView) view.findViewById(R.id.item_practice_content_tv);
//            isRightIv = (ImageView) view.findViewById(R.id.item_practice_isright_iv);
//
//        }
//    }
//
//
//}
package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.ExerciseBean;
import com.edu.accountingteachingmaterial.bean.ExercisePracticeBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ExerciseExLvAdapter extends BaseExpandableListAdapter {


    Context context;
    List<ExerciseBean> datas;

    public ExerciseExLvAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ExerciseBean> datas) {
        this.datas = datas;
    }


    @Override
    public int getGroupCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return datas.get(i).getExerciseBeanList() == null ? 0 : datas.get(i).getExerciseBeanList().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if (view == null || view.getTag(R.id.tag_group) == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_exlv, viewGroup, false);
            AutoUtils.autoSize(view);

            groupViewHolder = new GroupViewHolder(view);
            view.setTag(R.id.tag_group, groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag(R.id.tag_group);
        }
        ExerciseBean exerciseBean = datas.get(i);
        groupViewHolder.testNumTv.setText("" + exerciseBean.getItemNumber());
        groupViewHolder.textimeTv.setText(exerciseBean.getTime());

        switch (exerciseBean.getQuestionType()) {
            case ClassContstant.EXERCISE_BEFORE_CLASS:
                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_keqian);
                groupViewHolder.titleTv.setText(exerciseBean.getTitle() + "课前预习");
                break;
            case ClassContstant.EXERCISE_IN_CLASS:
                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_suitang);
                groupViewHolder.titleTv.setText(exerciseBean.getTitle() + "随堂测验");
                break;
            case ClassContstant.EXERCISE_AFTER_CLASS:
                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_kehou);
                groupViewHolder.titleTv.setText(exerciseBean.getTitle() + "课后作业");
                break;
        }
        switch (exerciseBean.getExerciseStatus()) {
            case ClassContstant.EXAM_COMMIT:
                groupViewHolder.stautsIv.setImageResource(R.mipmap.btn_yituijiao_n);

                break;
            case ClassContstant.EXAM_DOWNLOADING:
                groupViewHolder.stautsIv.setVisibility(View.GONE);
                groupViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
            case ClassContstant.EXAM_NOT:

                break;
            case ClassContstant.EXAM_READ:
                groupViewHolder.stautsIv.setImageResource(R.mipmap.btn_yipiyue_n);

                break;
            case ClassContstant.EXAM_UNDONE:
                groupViewHolder.stautsIv.setImageResource(R.mipmap.btn_weitijiao_n);

                break;
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if (view == null || view.getTag(R.id.tag_child) == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_practice_exlv, viewGroup, false);
            AutoUtils.autoSize(view);

            childViewHolder = new ChildViewHolder(view);
            view.setTag(R.id.tag_child, childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag(R.id.tag_child);
        }
        ExercisePracticeBean exercisePracticeBean = datas.get(i).getExerciseBeanList().get(i1);
        childViewHolder.contentTv.setText(exercisePracticeBean.getContent());
        String s = null;
        switch (exercisePracticeBean.getQuestionType()) {
            case ClassContstant.SUBJECT_SINGLE_CHOSE:
                s = ClassContstant.SUBJECT_SINGLE_CHOSE_STRING;
                break;
            case ClassContstant.SUBJECT_MULITI_CHOSE:
                s = ClassContstant.SUBJECT_MULITI_CHOSE_STRING;
                break;
            case ClassContstant.SUBJECT_JUDGE:
                s = ClassContstant.SUBJECT_JUDGE_STRING;
                break;
            case ClassContstant.SUBJECT_PRACTIAL:
                s = ClassContstant.SUBJECT_PRACTIAL_STRING;
                break;
            case ClassContstant.SUBJECT_ENTRY:
                s = ClassContstant.SUBJECT_ENTRY_STRING;
                break;
            case ClassContstant.SUBJECT_BILL:
                s = ClassContstant.SUBJECT_BILL_STRING;
                break;
            case ClassContstant.SUBJECT_GROUP_BILL:
                s = ClassContstant.SUBJECT_GROUP_BILL_STRING;
                break;
        }
        childViewHolder.numTv.setText(exercisePracticeBean.getPracticeNum() + " " + s);
        switch (exercisePracticeBean.isRight()) {
            case ClassContstant.ANSWER_RIGHT:
                childViewHolder.isRightIv.setImageResource(R.mipmap.icon_dui_n);
                childViewHolder.isRightIv.setVisibility(View.VISIBLE);
                break;
            case ClassContstant.ANSWER_ERROR:
                childViewHolder.isRightIv.setImageResource(R.mipmap.icon_cuo_n);
                childViewHolder.isRightIv.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder {
        TextView titleTv, textimeTv, testNumTv;
        ImageView headIv, stautsIv;
        ProgressBar progressBar;

        public GroupViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_exercise_title_tv);
            textimeTv = (TextView) view.findViewById(R.id.item_exercise_time_tv);
            testNumTv = (TextView) view.findViewById(R.id.item_exercise_testnum_tv);
            headIv = (ImageView) view.findViewById(R.id.item_exercise_head_iv);
            stautsIv = (ImageView) view.findViewById(R.id.item_exercise_type_iv);
            progressBar = (ProgressBar) view.findViewById(R.id.item_exercise_type_pb);

        }
    }

    class ChildViewHolder {
        TextView numTv, contentTv;
        ImageView isRightIv;

        public ChildViewHolder(View view) {
            numTv = (TextView) view.findViewById(R.id.item_practice_number_tv);
            contentTv = (TextView) view.findViewById(R.id.item_practice_content_tv);
            isRightIv = (ImageView) view.findViewById(R.id.item_practice_isright_iv);

        }
    }


}
