package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.entity.ExamListData;
import com.edu.subject.SubjectState;
import com.edu.subject.data.BaseTestData;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ExerciseExLvAdapter extends BaseExpandableListAdapter {


    Context context;
    List<ExamListData> datas;

    public ExerciseExLvAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ExamListData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }



    @Override
    public int getGroupCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return datas.get(i).getTestList() == null ? 0 : datas.get(i).getTestList().size();
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
        return datas.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return (datas.get(i).getTestList().get(i1).getSubjectId());
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if (view == null || view.getTag(R.id.tag_group) == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_exlv, viewGroup, false);

            groupViewHolder = new GroupViewHolder(view);
            view.setTag(R.id.tag_group,groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag(R.id.tag_group);
        }
        ExamListData exerciseBean = datas.get(i);
        groupViewHolder.testNumTv.setText("" + exerciseBean.getTopic_num());
        groupViewHolder.textimeTv.setText(""  + exerciseBean.getCreate_date());

        switch (exerciseBean.getLesson_type()) {
            case ClassContstant.EXERCISE_BEFORE_CLASS:
                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_suitang);
                groupViewHolder.titleTv.setText(exerciseBean.getExam_name());
                break;
            case ClassContstant.EXERCISE_IN_CLASS:
                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_suitang);
                groupViewHolder.titleTv.setText("随堂测验:" + exerciseBean.getExam_name());
                break;
            case ClassContstant.EXERCISE_AFTER_CLASS:
                groupViewHolder.headIv.setImageResource(R.mipmap.touxiang_kehou);
                groupViewHolder.titleTv.setText("课后作业:" + exerciseBean.getExam_name());
                break;
        }
        switch (exerciseBean.getState()) {
            case ClassContstant.EXAM_COMMIT:
                if (exerciseBean.getLesson_type() != ClassContstant.EXERCISE_IN_CLASS){
                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_commit_type);}
                else {
                    groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
                }
                break;
            case ClassContstant.EXAM_NOT:
                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_download_type);
                break;
            case ClassContstant.EXAM_READ:
                if (exerciseBean.getLesson_type() != ClassContstant.EXERCISE_IN_CLASS){
                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_read_type);}
                else {
                    groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
                }
                break;
            case ClassContstant.EXAM_UNDONE:
                groupViewHolder.progressBar.setVisibility(View.GONE);
                if (exerciseBean.getLesson_type() != ClassContstant.EXERCISE_IN_CLASS){
                    groupViewHolder.stautsIv.setVisibility(View.VISIBLE);
                groupViewHolder.stautsIv.setImageResource(R.drawable.selector_exam_undown_type);}
                else {
                    groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
                }

                break;
            default:
                groupViewHolder.stautsIv.setVisibility(View.INVISIBLE);
        break;
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if (view == null || view.getTag(R.id.tag_child) == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_practice_exlv, viewGroup, false);

            childViewHolder = new ChildViewHolder(view);
            view.setTag(R.id.tag_child, childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag(R.id.tag_child);
        }

        BaseTestData exercisePracticeBean = datas.get(i).getTestList().get(i1);
        if (exercisePracticeBean.getSubjectData() == null){
            Log.d("ExerciseExLvAdapter", "没有支持的题型");
            return view;
        }
        childViewHolder.contentTv.setText(exercisePracticeBean.getSubjectData().getQuestion().getText());
        String s = null;
        switch (exercisePracticeBean.getSubjectData().getSubjectType()) {
            case ClassContstant.SUBJECT_SINGLE_CHOSE:
                s = ClassContstant.SUBJECT_SINGLE_CHOSE_STRING;
                break;
            case ClassContstant.SUBJECT_MULITI_CHOSE:
                s = ClassContstant.SUBJECT_MULITI_CHOSE_STRING;
                break;
            case ClassContstant.SUBJECT_JUDGE:
                s = ClassContstant.SUBJECT_JUDGE_STRING;
                break;
            case ClassContstant.SUBJECT_BLANK:
                s = ClassContstant.SUBJECT_BLANK_STRING;
                break;
            case ClassContstant.SUBJECT_ENTRY:
                s = ClassContstant.SUBJECT_ENTRY_STRING;
                break;
            case ClassContstant.SUBJECT_BILL:
                s = ClassContstant.SUBJECT_BILL_STRING;
                break;
            case ClassContstant.SUBJECT_SIMPLE_ANSWER:
                s = ClassContstant.SUBJECT_SIMPLE_ANSWER_STRING;
                break;
            case ClassContstant.SUBJECT_COMPREHENSIVE:
                s = ClassContstant.SUBJECT_COMPREHENSIVE_STRING;
                break;
            case ClassContstant.SUBJECT_GROUP_BILL:
                s = ClassContstant.SUBJECT_GROUP_BILL_STRING;
                break;
        }
        childViewHolder.numTv.setText(exercisePracticeBean.getSubjectIndex() + " " + s);
        switch (exercisePracticeBean.getState()) {
            case SubjectState.STATE_CORRECT:
                childViewHolder.isRightIv.setImageResource(R.mipmap.icon_dui_n);
                childViewHolder.isRightIv.setVisibility(View.VISIBLE);
                break;
            case SubjectState.STATE_WRONG:
                childViewHolder.isRightIv.setImageResource(R.mipmap.icon_cuo_n);
                childViewHolder.isRightIv.setVisibility(View.VISIBLE);

                break;
            default:
                childViewHolder.isRightIv.setVisibility(View.INVISIBLE);

                break;
        }
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
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
