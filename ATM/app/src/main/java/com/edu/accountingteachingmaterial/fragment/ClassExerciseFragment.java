package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectPracticeActivity;
import com.edu.accountingteachingmaterial.activity.SubjectTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExerciseExLvAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExerciseBean;
import com.edu.accountingteachingmaterial.bean.ExercisePracticeBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ClassExerciseFragment extends BaseFragment {
    ExpandableListView expandableListView;
    List<ExerciseBean> datas;

    @Override
    protected int initLayout() {
        return R.layout.fragment_class_exercise;
    }

    @Override
    protected void initView(View view) {
        expandableListView = bindView(R.id.exercise_exlv);

    }

    @Override
    protected void initData() {
        loadDatas();
        ExerciseExLvAdapter adapter = new ExerciseExLvAdapter(context);
        adapter.setDatas(datas);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Log.d("ClassExerciseFragment", "点击确认");
               if(datas.get(i).getQuestionType() == ClassContstant.EXERCISE_BEFORE_CLASS){
                   startActivity(SubjectPracticeActivity.class);


               }else if (datas.get(i).getQuestionType() == ClassContstant.EXERCISE_AFTER_CLASS){
                   startActivity(SubjectTestActivity.class);


               }else {

               }
                return false;
            }
        });
    }

    private void loadDatas() {
        datas = new ArrayList<>();
        for (int i = 1; i <10 ; i++) {
            ExerciseBean data = new ExerciseBean();
            data.setTitle("会计凭证");
            data.setItemNumber(i);
            data.setTime("20161111");
            data.setExerciseStatus(i%6);
            switch (i%3){
                case 0:
                    data.setQuestionType(ClassContstant.EXERCISE_BEFORE_CLASS);
                    break;
                case 1:
                    data.setQuestionType(ClassContstant.EXERCISE_IN_CLASS);
                    List<ExercisePracticeBean> exercisePracticeBeans = new ArrayList<>();
                    for (int j =1; j < 8; j++) {
                        ExercisePracticeBean practiceBean = new ExercisePracticeBean();
                        practiceBean.setQuestionType(j);
                        practiceBean.setContent("试题内容");
                        practiceBean.setPracticeNum(j);
                        if (j ==4){
                            practiceBean.setIsRight(ClassContstant.ANSWER_RIGHT);
                        }else if (j==5){
                            practiceBean.setIsRight(ClassContstant.ANSWER_ERROR);

                        }else {
                            practiceBean.setIsRight(ClassContstant.ANSWER_NODONE);
                        }
                        exercisePracticeBeans.add(practiceBean);
                    }
                    data.setExerciseBeanList(exercisePracticeBeans);
                    break;
                case 2:
                    data.setQuestionType(ClassContstant.EXERCISE_AFTER_CLASS);

                    break;

            }
            datas.add(data);

        }

    }
}
