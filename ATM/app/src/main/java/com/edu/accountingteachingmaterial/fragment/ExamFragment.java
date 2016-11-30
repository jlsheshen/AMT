package com.edu.accountingteachingmaterial.fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.activity.SubjectTestActivity;
import com.edu.accountingteachingmaterial.adapter.ExamAdapter;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.bean.ExamBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.edu.library.util.DBCopyUtil;
import com.edu.testbill.Constant;
import com.edu.testbill.util.SoundPoolUtil;

import java.util.ArrayList;
import java.util.List;

public class ExamFragment extends BaseFragment {

    ListView listView;
    List<ExamBean> datas;
    ExamAdapter examAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_exam;
    }

    @Override
    protected void initView(View view) {
        listView = bindView(R.id.exam_lv);

    }

    @Override
    protected void initData() {

        // 检测数据库是否已拷贝
        DBCopyUtil fileCopyUtil = new DBCopyUtil(context);
        fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
        SoundPoolUtil.getInstance().init(context);


        loadData();

        examAdapter = new ExamAdapter(context);
        examAdapter.setDatas(datas);

        listView.setAdapter(examAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,  final View view, final int i, long l) {
                if (datas.get(i).getExmaStatus() == ClassContstant.EXAM_NOT) {
                     final ImageView imageView = (ImageView) view.findViewById(R.id.item_exam_type_iv);
                    imageView.setVisibility(View.GONE);
                    view.findViewById(R.id.item_exam_type_pb).setVisibility(View.VISIBLE);
                    datas.get(i).setExmaStatus(ClassContstant.EXAM_DOWNLOADING);
                    CountDownTimer timer = new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            datas.get(i).setExmaStatus(ClassContstant.EXAM_UNDONE);
                            view.findViewById(R.id.item_exam_type_pb).setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.mipmap.btn_weitijiao_n);
                        }
                    }.start();


                }else {
                    startActivity(SubjectTestActivity.class);

                }
            }
        });

    }

    private void loadData() {

        datas = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ExamBean examBean = new ExamBean();

            examBean.setExmaStatus(i);
            examBean.setTitle("会计立体化教材");
            examBean.setTime("20161111");
            examBean.setPublisher("赵铁柱");
            examBean.setItemNumber((long) 130);
            examBean.setStartTime("2016-11-11 10:30");
            examBean.setDuration(60);
            datas.add(examBean);
        }
        // TODO Auto-generated method stub

    }
}
