package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.entity.ExamListData;

/**
 * Created by Administrator on 2016/11/18.
 */

public class UnitTestActivity extends BaseActivity implements OnClickListener {
    ImageView imgBack, imgShow;
    TextView testTitle, tvPublisher, tvReleaseTime, tvScore, tvSubmittingTime,
            tvAnswerTime, tvStartTime, tvEndtime, tvChallengTime,
            tvSingle, tvMultiple, tvJudge, tvFillIn, tvShort, tvComprehensive, tvTotal;
    Button btnStart;

    @Override
    public int setLayout() {
        return R.layout.activity_unit_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bindAndListener(imgBack, R.id.class_aty_back_iv);
        bindAndListener(imgShow, R.id.img_show);
        bindAndListener(testTitle, R.id.class_id_title_tv);
        bindAndListener(tvPublisher, R.id.publisher_tv);
        bindAndListener(tvReleaseTime, R.id.release_time_tv);
        bindAndListener(tvScore, R.id.score_tv);
        bindAndListener(tvSubmittingTime, R.id.submitting_time_tv);
        bindAndListener(tvAnswerTime, R.id.answer_time_tv);
        bindAndListener(tvStartTime, R.id.start_time_tv);
        bindAndListener(tvEndtime, R.id.end_time_tv);
        bindAndListener(tvChallengTime, R.id.challeng_time_tv);
        bindAndListener(tvSingle, R.id.single_tv);
        bindAndListener(tvMultiple, R.id.multiple_tv);
        bindAndListener(tvJudge, R.id.judge_tv);
        bindAndListener(tvFillIn, R.id.fill_in_tv);
        bindAndListener(tvShort, R.id.short_tv);
        bindAndListener(tvComprehensive, R.id.comprehensive_tv);
        bindAndListener(tvTotal, R.id.total_tv);
        bindAndListener(btnStart, R.id.btn_start);


    }

    @Override
    public void initData() {

    }

    private void bindAndListener(View view, int id) {
        view = bindView(id);
        view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.class_aty_back_iv:
                this.finish();
                break;

            case R.id.btn_start:
                //ExamListData考试数据（测试）
                Bundle bundle = new Bundle();
                ExamListData datas = new ExamListData();
                datas.setChapter_id(11);
                datas.setId(1);
                bundle.putSerializable("ExamListData", datas);
                startActivity(SubjectTestActivity.class, bundle);
                break;


        }
    }
}
