package com.edu.accountingteachingmaterial.subject.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.adapter.EntryViewAdapter;
import com.edu.accountingteachingmaterial.bean.BillDataModel;
import com.edu.accountingteachingmaterial.bean.SubjectEntryData;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.view.ViewPagerParent;
import com.edu.library.util.ToastUtil;
import com.edu.subject.ISubject;
import com.edu.subject.SubjectListener;
import com.edu.subject.data.BaseTestData;

import java.util.ArrayList;
import java.util.List;

public class FenLuContentView extends RelativeLayout implements OnClickListener,ISubject {
    // 问题
    private TextView tvQuestion;
    // 问题
    private LinearLayout llyoutQuestion;
    // 确定按钮;添加一组
    private Button btnFinish;
    // 正确答案
    private String[] rAnswear;
    // 用户答案
    private StringBuilder myAsw;
    private Context mContext;

    private SubjectEntryData mData;
    private int mTestMode;
    private int mState; // 题目状态 0未答，1正确，2错误
    private BaseTestData mTestData;// 当前题的信息
    // 问题,获得的积分
    private TextView tvScore;
    private List<EntryView> ltQuestionItemView;
    private String[] subjectIds;
    /**
     * 是否全部正确
     */
    private int rightStatic = 1;
    // /**
    // * 金额输入控件
    // */
    private KeyboardView keyboard;

    private TextView tvSubjectType;
    private RelativeLayout rlBottom;
    private ImageView img;
    private ScrollView backgroundSv;
    private int startX;
    private int startY;
    private ViewPagerParent vpSubject;// viewPage界面
    private int mCurrentViewID = 0; // 当前页面
    private EntryViewAdapter eAdapter;
    private Button btnLeft, btnRight;
    private TextView tvNum;

    public FenLuContentView(Context context, BaseTestData data, int testMode) {
        super(context);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_calculation_secend, this);
        mData = (SubjectEntryData) data.getSubjectData();
        mState = data.getState();
        this.mTestMode = testMode;
        mTestData = data;
        ltQuestionItemView = new ArrayList<EntryView>();
        initView();
        refreshState(2);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        // tvQuestion.setOnClickListener(this);
//        llyoutQuestion = (LinearLayout) findViewById(R.id.llyout_choice);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(this);
        // tvQuestion.append(Html.fromHtml(mData.getIndexName() + ". " +
        // mData.getQuestion(), imageGetter, null));
        keyboard = (KeyboardView) this.findViewById(R.id.keyboard);
//        tvSubjectType = (TextView) findViewById(R.id.tv_subject_type);
//        tvSubjectType.setText("错误" + mTestData.getErrorCount() + "次");
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        tvNum = (TextView) findViewById(R.id.tv_num);
        vpSubject = (ViewPagerParent) this.findViewById(R.id.vp_bottom);
        rlBottom = (RelativeLayout) this.findViewById(R.id.rlyout_bottom);
        backgroundSv = (ScrollView) this.findViewById(R.id.sc);
        img = (ImageView) this.findViewById(R.id.on_down);
        img.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 初始化起点坐标
                        //startX = (int) motionEvent.getRawX();
                        startY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //  int endX = (int) motionEvent.getRawX();
                        int endY = (int) motionEvent.getRawY();

                        // 计算移动偏移量
                        // int dx = endX - startX;
                        int dy = endY - startY;
//                       ViewGroup.LayoutParams layoutParams =  viewPager2.getLayoutParams();
//                        layoutParams.height =view.getBottom() + dy;
                        // 更新左上右下距离
//                        int l = view.getLeft() + dx;
//                        int r = view.getRight() + dx;
                        int t = view.getTop() + dy;
                        int b = view.getBottom() + dy;
                        // 判断是否超出屏幕边界, 注意状态栏的高度
//                        if (l < 0 || r > winWidth || t < 0 || b > winHeight - 20) {
//                            break;
//                        }
                        // 更新界面
                        //  view.layout(view.getLeft(), t, view.getRight(), b);
                        //                    viewPager2.setTop( view.getBottom() + dy);
                        //    scrollView.setTop(view.getBottom() + dy);
                        backgroundSv.setBottom(backgroundSv.getBottom() + dy);
                        Log.d("MyFragment", "getScrollY: " + backgroundSv.getScrollY());
//                        Log.d("MyFragment", "getMaxScrollAmount: " + backgroundSv.getChildAt(0).getMeasuredHeight());
                        Log.d("MyFragment", "backgroundSv.getHeight(): " + backgroundSv.getHeight());
//                        Log.d("MyFragment", "dy " + dy);
                        if (backgroundSv.getScrollY() + backgroundSv.getHeight() >= backgroundSv.getChildAt(0).getMeasuredHeight()) {
                            backgroundSv.scrollTo(0, backgroundSv.getChildAt(0).getMeasuredHeight() - backgroundSv.getHeight());
                        }
                        //backgroundSv.scrollTo(0,backgroundSv.getBottom()+ dy);
                        rlBottom.setTop(rlBottom.getTop() + dy);

                        // 重新初始化起点坐标
                        //startX = (int) motionEvent.getRawX();
                        startY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 图片解析类
     */
    protected ImageGetter imageGetter = new ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            try {
                int id = getContext().getResources().getIdentifier(source, "drawable", getContext().getPackageName());
                // 根据id从资源文件中获取图片对象
                Drawable d = getContext().getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            } catch (Exception e) {
                return getContext().getResources().getDrawable(R.drawable.ic_launcher);
            }
        }
    };

    /**
     * 更新状态
     */
    private void refreshState(int flag) {
        // tvQuestion.setText(mData.getIndexName() + ". " +
        // mData.getQuestion());
        subjectIds = mTestData.getSubjectId().split(">>>");
        String[] questions = mData.getQuestion().split(">>>");
        tvQuestion.setText(Html.fromHtml(questions[1], imageGetter, null));
        for (int index = 0; index < subjectIds.length; index++) {
            EntryView entryView = new EntryView(mContext);
            // 模式1答题模式、2模式显示答案
            if (mState == 0 && mTestMode == 1) {
                entryView.setKeyboard(keyboard);
                entryView.setOnClickListener(this);
                entryView.init(Integer.valueOf(subjectIds[index]), EntryView.MODE_INITIAL, false);
                ltQuestionItemView.add(entryView);
            } else if (mTestMode == 2) {
                entryView.setKeyboard(keyboard);
                entryView.setOnClickListener(this);
                entryView.init(Integer.valueOf(subjectIds[index]), EntryView.MODE_JUDE_ANSWER, true);
                ltQuestionItemView.add(entryView);
            } else if (mState != 0) {
                entryView.setKeyboard(keyboard);
                entryView.setOnClickListener(this);
                entryView.init(Integer.valueOf(subjectIds[index]), EntryView.MODE_JUDE_ANSWER, true);
                ltQuestionItemView.add(entryView);
            }
        }
        tvNum.setText(1 + " / " + ltQuestionItemView.size() + "");
        eAdapter = new EntryViewAdapter(ltQuestionItemView);
        vpSubject.setAdapter(eAdapter);
        vpSubject.setOnPageChangeListener(mPageChangeListener);
        if (mTestMode == 2) {
            btnFinish.setVisibility(View.GONE);
//            tvSubjectType.setVisibility(View.VISIBLE);
        } else {
            if (flag != 1) {
                if (subjectIds.length == 1) {
                    btnFinish.setVisibility(View.VISIBLE);
                }
            }
//            tvSubjectType.setVisibility(View.GONE);
        }
        if (keyboard.getVisibility() == View.VISIBLE) {
            keyboard.setVisibility(View.GONE);
        }
    }

    // 页面相关状态的监听
    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int position) {// 页面选择响应函数
            mCurrentViewID = position;
            vpSubject.setCurrentItem(mCurrentViewID, true);
            if (mCurrentViewID == ltQuestionItemView.size() - 1) {
                btnFinish.setVisibility(View.VISIBLE);
            } else {
                btnFinish.setVisibility(View.GONE);
            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {// 滑动中。。。
        }

        public void onPageScrollStateChanged(int position) {
        }
    };

    /**
     * 显示图片
     *
     * @param picName
     */
    private void showPicture(String picName) {
        List<String> picList = new ArrayList<String>();
        picList = getPicList(picName);
        if (picList != null && picList.size() > 0) {
        }
    }

    /**
     * 截取图片名加入list
     *
     * @param picName
     * @return
     */
    private List<String> getPicList(String picName) {
        List<String> picList = new ArrayList<String>();
        String[] picNames = picName.split("&");
        for (int i = 0; i < picNames.length; i++) {
            picList.add(picNames[i]);
        }
        return picList;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_finish:
                judeAnswer();
                break;

            case R.id.btn_left:
                if (mCurrentViewID != 0) {
                    mCurrentViewID--;
                    vpSubject.setCurrentItem(mCurrentViewID, true);
                    tvNum.setText(mCurrentViewID + 1 / ltQuestionItemView.size());
                    tvNum.setText((mCurrentViewID + 1) + " / " + ltQuestionItemView.size() + "");
                } else {
                    ToastUtil.showToast(mContext, "已经是第一题了!");
                }
                if (mCurrentViewID == ltQuestionItemView.size() - 1) {
                    btnFinish.setVisibility(View.VISIBLE);
                } else {
                    btnFinish.setVisibility(View.GONE);
                }
                break;

            case R.id.btn_right:
                if (ltQuestionItemView != null && mCurrentViewID != ltQuestionItemView.size() - 1) {
                    mCurrentViewID++;
                    vpSubject.setCurrentItem(mCurrentViewID, true);
                    tvNum.setText((mCurrentViewID + 1) + " / " + ltQuestionItemView.size() + "");
                } else {
                    ToastUtil.showToast(mContext, "已经是最后一题了!");
                }
                if (mCurrentViewID == ltQuestionItemView.size() - 1) {
                    btnFinish.setVisibility(View.VISIBLE);
                } else {
                    btnFinish.setVisibility(View.GONE);
                }
                break;
            default:
                subDo(v);
                break;
        }
    }

    /**
     * 题干点击处理
     *
     * @param v
     */
    private void subDo(View v) {
//        llyoutQuestion.removeAllViews();
//        llyoutQuestion.addView(ltQuestionItemView.get(v.getId()));
        if (v.getId() == subjectIds.length - 1 && mState == 0 && mTestMode != 2) {
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnFinish.setVisibility(View.GONE);
        }
    }

    /**
     * 显示图片提示
     */
    private void showIndicator() {
        PreferenceHelper.getInstance(getContext()).setFirstTimeFalse(PreferenceHelper.KEY_ROPE_INDICATOR);
    }

    /**
     * 更新单据的状态
     *
     * @param state 状态
     */
    private void updateTestState(int state) {
        //TestDataModel.getInstance(getContext()).updateState(mTestData.getId(), state);
    }

    /**
     * 更新分录表的USCORE和IS_CORRECT
     *
     * @param
     */
    private void updateBillScore(int score, int isCorrect) {
        BillDataModel.getInstance(getContext()).updataUScoreAndIsCorrect(mData.getId(), score, isCorrect);
    }

    /**
     * 更新分录表的uBorrow和uLoan
     *
     * @param
     */
    private void updateBillUborrowAndUloan(String uBorrow, String uLoan) {
        BillDataModel.getInstance(getContext()).updateUserAnswer(mData.getId(), uBorrow, uLoan);
    }

    /**
     * 判断答案
     */
    private void judeAnswer() {
        int userTotalScore = 0;
        for (int i = 0; i < subjectIds.length; i++) {
            ltQuestionItemView.get(i).init(Integer.valueOf(subjectIds[i]), EntryView.MODE_SHOW_USER_ANSWER, true);
            ltQuestionItemView.get(i).saveUserAnswerToDb();
            int uScore = (int) ltQuestionItemView.get(i).judgeAnswerSubject(Integer.valueOf(subjectIds[i]));
            if (uScore == ltQuestionItemView.get(i).FULL_SCORE) {
                mState = 1;
            } else {
                mState = 2;
            }
            if (mState == 2) {
                rightStatic = 2;
            }
            userTotalScore = userTotalScore + uScore;
        }
        mTestData.setState(rightStatic);

        mTestData.setuScore(userTotalScore);

       // TestDataModel.getInstance(mContext).updateState(Integer.valueOf(mTestData.getId()), rightStatic, userTotalScore);

        btnFinish.setVisibility(View.GONE);
        refreshState(1);
    }

    @Override
    public void applyData(BaseTestData data) {

    }

    @Override
    public void saveAnswer() {

    }

    @Override
    public float submit() {
        return 0;
    }

    @Override
    public void showDetails() {

    }

    public void reset() {
        mData.setBorrowUser("");
        mData.setLoanUser("");
        mData.setuScore(0);
        mTestData.setState(0);
        mState = mTestData.getState();
        restart();
        btnFinish.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSubjectListener(SubjectListener listener) {

    }

    /**
     * 重来
     */
    private void restart() {
        updateTestState(0);// 清答题状态 置零
        updateBillScore(0, 0);// 清空分数和正误
        updateBillUborrowAndUloan("", "");// 借方，贷方 清空
        refreshState(2);
    }

}
