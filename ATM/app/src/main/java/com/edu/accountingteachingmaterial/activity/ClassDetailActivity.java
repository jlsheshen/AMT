package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.base.BaseApplication;
import com.edu.accountingteachingmaterial.entity.SubChaptersBean;
import com.edu.accountingteachingmaterial.fragment.ClassEmphasisFragment;
import com.edu.accountingteachingmaterial.fragment.ClassExampleFragment;
import com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment;
import com.edu.accountingteachingmaterial.fragment.GroupTaskFragment;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.view.ChapterPopupWindow;

/**
 * 课程详情
 */
public class ClassDetailActivity extends BaseActivity implements OnClickListener {

    // 重点难点,经典实例,精选练习,自我检测
    RadioButton  classExampleButton, classExerciseButton, classReviewButton;
    RadioButton classEmphasisButton;
    Fragment  classExampleFragment, classExerciseFragment, classReviewFragment;
    ClassEmphasisFragment classEmphasisFragment;
    ImageView backIv, imgZhangjie, imgReviewHy;
    TextView textView;
    SubChaptersBean data;
    int chapterId;
    View vLine;
    //当前是否是教材入口
    private boolean isBook = PreferenceHelper.getInstance(BaseApplication.getContext()).getBooleanValue(PreferenceHelper.IS_TEXKBOOK);

    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_class_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(classEmphasisButton, R.id.class_emphasis_iv);
        bindAndListener(classExampleButton, R.id.class_example_iv);
        bindAndListener(classExerciseButton, R.id.class_exercise_iv);
        bindAndListener(classReviewButton, R.id.class_review_iv);
        bindAndListener(backIv, R.id.class_aty_back_iv);
        bindAndListener(imgZhangjie, R.id.class_aty_zhangjie_iv);
        imgReviewHy = (ImageView) findViewById( R.id.class_review_hy);
        imgReviewHy.setOnClickListener(this);
        if (isBook){
            findViewById(R.id.class_review_iv).setVisibility(View.GONE);
        }
        textView = bindView(R.id.class_id_title_tv);
        imgZhangjie = (ImageView) findViewById(R.id.class_aty_zhangjie_iv);
        vLine = (View) findViewById(R.id.view);
    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();
        data = (SubChaptersBean) bundle.getSerializable("classData");
        chapterId = bundle.getInt("ChapterId");
        if (data != null) {
            textView.setText(data.getTitle());
        }
        if (null == classEmphasisFragment) {
            classEmphasisFragment = new ClassEmphasisFragment();
        }
        replaceFragment(classEmphasisFragment);
        classEmphasisFragment.setChapter(chapterId);

        // TODO Auto-generated method stub
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.class_aty_view, fragment);
        transaction.commit();
    }

    private void bindAndListener(View view, int id) {
        view = bindView(id);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.class_emphasis_iv:
                if (null == classEmphasisFragment) {
                    classEmphasisFragment = new ClassEmphasisFragment();
                }
                classEmphasisFragment.setChapter(chapterId);
                replaceFragment(classEmphasisFragment);
                imgReviewHy.setVisibility(View.GONE);
                break;

            case R.id.class_example_iv:
                if (null == classExampleFragment) {
                    classExampleFragment = new ClassExampleFragment();
                    ((ClassExampleFragment) classExampleFragment).setData(data);
                }
                replaceFragment(classExampleFragment);
                imgReviewHy.setVisibility(View.GONE);
                break;
            case R.id.class_exercise_iv:
                if (null == classExerciseFragment) {
                    classExerciseFragment = new ClassExerciseFragment();
                    ((ClassExerciseFragment) classExerciseFragment).setData(data);
                }
                replaceFragment(classExerciseFragment);
                imgReviewHy.setVisibility(View.GONE);
                break;
            case R.id.class_review_iv:
                if (null == classReviewFragment) {
                    classReviewFragment = new GroupTaskFragment();
                }
                replaceFragment(classReviewFragment);
                imgReviewHy.setVisibility(View.GONE);
                imgZhangjie.setVisibility(View.GONE);

                break;
            case R.id.class_aty_back_iv:
                finish();
                break;
            case R.id.class_aty_zhangjie_iv:
                ChapterPopupWindow popWindow = new ChapterPopupWindow(ClassDetailActivity.this);
                popWindow.showPopupWindow(vLine);
                break;

            case R.id.class_review_hy:

                break;
        }

    }


}
