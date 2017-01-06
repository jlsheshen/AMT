package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.ReviewHisAdapter;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.bean.ReviewHisListBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.accountingteachingmaterial.dao.ReviewExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.view.DeteleDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ReviewHistoryActivity extends BaseActivity implements View.OnClickListener {
    ListView listView;
    ReviewHisAdapter reviewHisAdapter;
    TextView cancelTv, deteleTv, allCheckTv;
    ImageView backIv;
    ViewGroup layout;
    List<ReviewHisListBean> datas;
    int chapterId;
    List<Boolean> checkList;
    DeteleDialog deteleDialog;


    @Override
    public int setLayout() {
        return R.layout.activity_review_history;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        listView = bindView(R.id.review_his_lv);
        cancelTv = bindView(R.id.review_his_cancel_tv);
        cancelTv.setOnClickListener(this);

        backIv = bindView(R.id.review_his_back_iv);
        backIv.setOnClickListener(this);
        deteleTv = bindView(R.id.bolw_bar_delete_tv);
        deteleTv.setOnClickListener(this);
        allCheckTv = bindView(R.id.blow_bar_allchecked_tv);
        allCheckTv.setOnClickListener(this);
        layout = bindView(R.id.bolw_bar);
        EventBus.getDefault().register(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                reviewHisAdapter.setClickShow();
                checkList = reviewHisAdapter.getIsChecked();
                layout.setVisibility(View.VISIBLE);
                cancelTv.setVisibility(View.VISIBLE);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (datas.get(position).getState() == ClassContstant.EXAM_COMMIT) {
                    //跳转到答题界面
                    Bundle bundle = new Bundle();
                    bundle.putInt("chapterId", datas.get(position).getId());
                    startActivity(SubjectDetailsLocalActivity.class, bundle);

                } else {
                    //跳转到答题界面
                    Bundle bundle = new Bundle();
                    bundle.putInt("chapterId", datas.get(position).getId());
                    startActivity(SubjectReViewActivity.class, bundle);
                }


            }
        });

    }

    @Override
    public void initData() {
        reviewHisAdapter = new ReviewHisAdapter(this);
//        datas = ReviewExamListDao.getInstance(this).getDataByChatper(chapterId);

        loadData();
        listView.setAdapter(reviewHisAdapter);
        reviewHisAdapter.setDatas(datas);
//        reviewHisAdapter.setClickShow();
        reviewHisAdapter.setChecked(new ReviewHisAdapter.OnCheckedListener() {
            @Override
            public void onCheckeBoxChecked() {
                int i = 0;
                for (Boolean aBoolean : checkList) {
                    if (aBoolean) {
                        i++;
                    }
                }
                if (i > 0) {
                    deteleTv.setText("删除(" + i + ")");
                    deteleTv.setAlpha(1);
                } else {
                    deteleTv.setText("删除");
                    deteleTv.setAlpha(0.5f);
                }

            }
        });
    }

    private void loadData() {
        datas = (List<ReviewHisListBean>) ReviewExamListDao.getInstance(this).getAllDatas();
    }

    /**
     * 根据发来的状态,来刷新列表
     *
     * @param state
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Integer state) {
        Log.d("ClassExerciseFragment", "走过了EventBus");
        if (state == ClassContstant.EXAM_COMMIT) {
            loadData();
            reviewHisAdapter.setDatas(datas);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.review_his_cancel_tv:
                reviewHisAdapter.setClickConceal();
                layout.setVisibility(View.GONE);
                cancelTv.setVisibility(View.GONE);

                break;
            case R.id.review_his_back_iv:
                finish();
                break;
            case R.id.bolw_bar_delete_tv:
//                deteleData();
//                reviewHisAdapter.setDatas(datas);

                showDeteleDialog();

                break;
            case R.id.blow_bar_allchecked_tv:
                reviewHisAdapter.setAllchecked();
                break;
            default:
                break;

        }
    }

    void deteleData() {
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i)) {
                ReviewExamListDao.getInstance(this).deleteData(datas.get(i).getId());
                SubjectTestDataDao.getInstance(this).deleteData(datas.get(i).getId());
                checkList.remove(i);
                datas.remove(i);
                i--;
            }
        }
        if (datas.size() < 1) {
            cancelTv.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);

        }
        reviewHisAdapter.setDatas(datas);
    }

    private void showDeteleDialog() {
        deteleDialog = new DeteleDialog(this);
        if (!deteleDialog.isShowing()) {
            deteleDialog.show();
        }
        deteleDialog.setDialogListener(new DeteleDialog.SetDialogListener() {
            @Override
            public void onOkClicked() {
                deteleData();

            }

            @Override
            public void onCancelClicked() {
                if (deteleDialog.isShowing()) {
                    deteleDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
