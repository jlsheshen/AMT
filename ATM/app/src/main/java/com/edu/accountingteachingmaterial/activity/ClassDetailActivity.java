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
import com.edu.accountingteachingmaterial.entity.ClassChapterData;
import com.edu.accountingteachingmaterial.fragment.ClassEmphasisFragment;
import com.edu.accountingteachingmaterial.fragment.ClassExampleFragment;
import com.edu.accountingteachingmaterial.fragment.ClassExerciseFragment;
import com.edu.accountingteachingmaterial.fragment.ClassReviewFragment;
import com.edu.accountingteachingmaterial.view.ChapterPopupWindow;

public class ClassDetailActivity extends BaseActivity implements OnClickListener {

    // 重点难点,经典实例,精选练习,自我检测
    RadioButton classEmphasisButton, classExampleButton, classExerciseButton, classReviewButton;
    Fragment classEmphasisFragment, classExampleFragment, classExerciseFragment, classReviewFragment;
    ImageView backIv, imgZhangjie, imgReviewHy;
    TextView textView;
    ClassChapterData.SubChaptersBean data;
    int chapterId;
    View vLine;

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
        bindAndListener(imgReviewHy, R.id.class_review_hy);
        textView = bindView(R.id.class_id_title_tv);
        imgZhangjie = (ImageView) findViewById(R.id.class_aty_zhangjie_iv);
        imgReviewHy = (ImageView) findViewById(R.id.class_review_hy);
        vLine = (View) findViewById(R.id.view);
    }

    @Override
    public void initData() {
        if (null == classEmphasisFragment) {
            classEmphasisFragment = new ClassEmphasisFragment();
        }
        replaceFragment(classEmphasisFragment);
        Bundle bundle = getIntent().getExtras();
        data = (ClassChapterData.SubChaptersBean) bundle.getSerializable("classData");
        chapterId = bundle.getInt("ChapterId");
        if (data != null) {
            textView.setText(data.getTitle());
        }
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
                    classReviewFragment = new ClassReviewFragment();
                    ((ClassReviewFragment) classReviewFragment).setData(chapterId);
                }
                replaceFragment(classReviewFragment);
                imgReviewHy.setVisibility(View.VISIBLE);
                break;
            case R.id.class_aty_back_iv:
                finish();
                break;
            case R.id.class_aty_zhangjie_iv:
                ChapterPopupWindow popWindow = new ChapterPopupWindow(ClassDetailActivity.this);
                popWindow.showPopupWindow(vLine);
                break;

            case R.id.class_review_hy:
                startActivity(ReviewHistoryActivity.class);
                break;
        }

    }


}
