package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
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
import com.edu.accountingteachingmaterial.dao.ReviewExamListDao;
import com.edu.accountingteachingmaterial.dao.SubjectTestDataDao;
import com.edu.accountingteachingmaterial.view.DeteleDialog;

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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                reviewHisAdapter.setClickShow();
                checkList = reviewHisAdapter.getIsChecked();
                layout.setVisibility(View.VISIBLE);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到答题界面
                Bundle bundle = new Bundle();
                bundle.putInt("chapterId", datas.get(position).getId());
                startActivity(SubjectReViewActivity.class, bundle);
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
        reviewHisAdapter.setChecked(new ReviewHisAdapter.OnCheckedListener() {
            @Override
            public void onCheckeBoxChecked() {
                int i = 0;
                for (Boolean aBoolean : checkList) {
                    if (aBoolean){
                        i++;
                    }
                }
                if (i>0){
                    deteleTv.setText("删除(" + i + ")");
                    deteleTv.setAlpha(1);
                }else {
                    deteleTv.setText("删除");
                    deteleTv.setAlpha(0.5f);
                }

            }
        });
    }

    private void loadData() {
        datas = (List<ReviewHisListBean>) ReviewExamListDao.getInstance(this).getAllDatas();
              //  new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            ReviewHisListBean d = new ReviewHisListBean();
//            d.setState(ClassContstant.EXAM_UNDONE);
//            d.setDate("2016-21-26");
//            d.setNumber("56");
//            d.setScore("615");
//            d.setTitle("155");
//            datas.add(d);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.review_his_cancel_tv:
                reviewHisAdapter.setClickConceal();
                layout.setVisibility(View.GONE);

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
    void deteleData(){
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i)) {
                ReviewExamListDao.getInstance(this).deleteData(datas.get(i).getId());
                SubjectTestDataDao.getInstance(this).deleteData(datas.get(i).getId());
                checkList.remove(i);
                datas.remove(i);
                i--;
            }
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

}
