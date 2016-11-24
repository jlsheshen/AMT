package com.edu.accountingteachingmaterial.subject.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.BillDataModel;
import com.edu.accountingteachingmaterial.bean.SubjectEntryData;
import com.edu.accountingteachingmaterial.bean.SubjectEntryDataDao;
import com.edu.accountingteachingmaterial.util.EditTextUtil;
import com.edu.accountingteachingmaterial.util.StringFormatUtil;
import com.edu.accountingteachingmaterial.view.PrimarySubjectDialog;
import com.edu.accountingteachingmaterial.view.SencondarySubjectDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借贷组视图封装
 *
 * @author lucher
 */
public class EntryView extends ScrollView implements OnClickListener {
    public int FULL_SCORE = 10;
    /**
     * 初始化状态
     */
    public static final int MODE_INITIAL = 1;
    /**
     * 显示用户答案状态
     */
    public static final int MODE_SHOW_USER_ANSWER = 2;
    /**
     * 判断用户答案状态
     */
    public static final int MODE_JUDE_ANSWER = 3;
    private static final String TAG = "EntryView";

    /**
     * 借方视图容器
     */
    private LinearLayout llBorrowContent;
    /**
     * 贷方视图容器
     */
    private LinearLayout llLoanContent;

    /**
     * 分割线
     */
    private View divideLine;
    /**
     * 正确答案
     */
    private TextView tvCorrectAnswer;
    /**
     * 结果图片
     */
    private ImageView ivResult;

    private Context mContext;

    // item的id
    private int entryId = 1;

    /**
     * 一级科目选择对话框
     */
    private PrimarySubjectDialog primaryDialog;
    /**
     * 二级科目选择对话框
     */
    private SencondarySubjectDialog secondaryDialog;

    /**
     * 保存所有EntryItem
     */
    private List<EntryItem> items;

    /**
     * 对应单据的数据
     */
    private SubjectEntryData data;

    // 金额输入框
    private KeyboardView keyboard;
    private float score = 0;

    // 借方和贷方的正确答案
    private List<String> borrowAnswerList;
    private List<String> loanAnswerList;
    // 存放借贷的答案组数
    private List<String> groupBorrowAnswerList;
    private List<String> groupLoanAnswerList;

    private List<View> viewChildList;
    private List<LinearLayout> ViewllBorrowList;
    private List<LinearLayout> ViewllLoanList;
    private boolean groupRoot = true;// 组根节点
    private LinearLayout llgroupLayout;// 借贷组合
    private Button btnGroupAddOrRemove;// 增加一组或减少一组
    // 存放每个条目
    private List<View> mViewsList;
    private int groupItemId = 0;
    private View viewChild;//
    private float singleScore = 0;// 一组借贷得分
    // 是否可显示答案内容，课后练习模块需要请求答案后才能看答案
    private boolean mShowAnswer;

    public EntryView(Context context) {
        super(context, null);

        initView(context);
    }

    public EntryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    /**
     * 初始化视图控件
     */
    private void initView(Context context) {
        mContext = context;
        View view = View.inflate(mContext, R.layout.layout_test_entry_view, this);
        llgroupLayout = (LinearLayout) view.findViewById(R.id.ll_borrow_loan_group);
        btnGroupAddOrRemove = (Button) view.findViewById(R.id.btn_add_or_rmovegroup);
        divideLine = view.findViewById(R.id.divide_line);
        tvCorrectAnswer = (TextView) view.findViewById(R.id.tv_correct_answer);
        ivResult = (ImageView) view.findViewById(R.id.iv_result_state);
        btnGroupAddOrRemove.setOnClickListener(this);
        primaryDialog = new PrimarySubjectDialog(mContext);
        secondaryDialog = new SencondarySubjectDialog(context);
        items = new ArrayList<EntryItem>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_or_rmovegroup:
                if (groupRoot) {
                    groupRoot = false;
                    addGroup();
                } else {
                    addGroup();
                }
                break;

            default:
                break;
        }

    }

    /**
     * 添加一组
     */
    private void addGroup() {
        creatGroupItem();
        // 加入借方条目
        addChildItem(groupItemId - 1, true, EntryItem.TYPE_BORROW);
        // 加入贷方条目
        addChildItem(groupItemId - 1, true, EntryItem.TYPE_LOAN);
        saveUserAnswerToDb();
    }

    /**
     * 创建组item
     */
    private void creatGroupItem() {
        View view = View.inflate(mContext, R.layout.borrow_loan_linearlaout, null);
        initGroupView(view);
        // initGroupState();
        llgroupLayout.addView(view);

    }

    private void initGroupView(View view) {
        llBorrowContent = (LinearLayout) view.findViewById(R.id.ll_borrow);
        llLoanContent = (LinearLayout) view.findViewById(R.id.ll_loan);
        llLoanContent.setTag(groupItemId);
        llBorrowContent.setTag(groupItemId);
        ViewllLoanList.add(llLoanContent);
        ViewllBorrowList.add(llBorrowContent);
        viewChild = view;
        view.setTag(groupItemId);
        viewChildList.add(view);
        groupItemId++;
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        groupBorrowAnswerList = new ArrayList<String>();
        groupLoanAnswerList = new ArrayList<String>();

        viewChildList = new ArrayList<View>();
        mViewsList = new ArrayList<View>();

        ViewllBorrowList = new ArrayList<LinearLayout>();
        ViewllLoanList = new ArrayList<LinearLayout>();
        items.clear();
        groupItemId = 0;
        entryId = 1;
        if (ViewllBorrowList != null) {
            ViewllBorrowList.clear();
        }
        if (ViewllLoanList != null) {
            ViewllLoanList.clear();
        }
        if (llgroupLayout != null) {
            llgroupLayout.removeAllViews();
        }
        groupRoot = true;
    }

    /**
     * 初始化数据
     * <p>
     * 对应数据
     *
     * @param mode       初始化模式
     * @param showAnswer 测试模块
     */
    public void init(int id, int mode, boolean showAnswer) {
        SubjectEntryData data = (SubjectEntryData) SubjectEntryDataDao.getInstance(mContext).getDataById(id);

        mShowAnswer = showAnswer;
        FULL_SCORE = (int) data.getScore();
        initDate();
        String[] groupBorrow = data.getBorrowAns().split("&&&");// 获取组的正确答案
        String[] groupLoan = data.getLoanAns().split("&&&");// 获取组的正确答案
        for (int j = 0; j < groupBorrow.length; j++) {
            groupBorrowAnswerList.add(groupBorrow[j]);
        }
        for (int j = 0; j < groupLoan.length; j++) {
            groupLoanAnswerList.add(groupLoan[j]);
        }

        borrowAnswerList = new ArrayList<String>();
        loanAnswerList = new ArrayList<String>();

        singleScore = FULL_SCORE / groupBorrowAnswerList.size();

        StringBuilder tvCorrect = new StringBuilder("");
        for (int a = 0; a < groupBorrowAnswerList.size(); a++) {
            borrowAnswerList = Arrays.asList(groupBorrowAnswerList.get(a).split(">>>"));
            loanAnswerList = Arrays.asList(groupLoanAnswerList.get(a).split(">>>"));
            if (tvCorrect.toString().equals("")) {
                tvCorrect.append(StringFormatUtil.getCorrectAnswer(mContext, borrowAnswerList, loanAnswerList));
            } else {
                tvCorrect.append(StringFormatUtil.getCorrectAnswer(mContext, borrowAnswerList, loanAnswerList));
            }
        }
        tvCorrectAnswer.setText("正确答案：" + tvCorrect);

        this.data = data;
        String[] userGroupBorrow = data.getBorrowUser().split("&&&");// 获取多组借的用户答案
        String[] userGroupLoans = data.getLoanUser().split("&&&");// 获取多组贷的用户答案

        String[] userBorrow;
        String[] userLoans;

        // 根据mode初始化item的状态
        if (mode == MODE_INITIAL) {
            creatGroupItem();
            // 加入借方条目
            EntryItem borrowItem = addChildItem(0, true, EntryItem.TYPE_BORROW);
            if (mode == MODE_INITIAL) {
                borrowItem.judgeHideUserAnswer();
            }
            // 加入贷方条目
            EntryItem loanItem = addChildItem(0, true, EntryItem.TYPE_LOAN);
            if (mode == MODE_INITIAL) {
                loanItem.judgeHideUserAnswer();
            }

        } else if (mode == MODE_SHOW_USER_ANSWER || mode == MODE_JUDE_ANSWER) {
            Log.d("mode", mode + "");
            for (int i = 0; i < userGroupBorrow.length; i++) {
                userBorrow = userGroupBorrow[i].split(">>>");
                creatGroupItem();
                // 加入借方条目
                for (int a = 0; a < userBorrow.length; a++) {
                    boolean root = a == 0 ? true : false;
                    EntryItem item = addChildItem(i, root, EntryItem.TYPE_BORROW);
                    ItemInfo info = new ItemInfo();
                    info.setUserAnswer(userBorrow[a]);
                    item.applyInfo(info);
                    item.showUserAnswer();
                    if (mode == MODE_JUDE_ANSWER) {
                        item.judgeUserAnswer();
                    }
                    saveUserAnswerToDb();
                }
                userLoans = userGroupLoans[i].split(">>>");
                for (int a = 0; a < userLoans.length; a++) {
                    boolean root = a == 0 ? true : false;
                    EntryItem item = addChildItem(i, root, EntryItem.TYPE_LOAN);
                    ItemInfo info = new ItemInfo();
                    info.setUserAnswer(userLoans[a]);
                    item.applyInfo(info);
                    item.showUserAnswer();
                    if (mode == MODE_JUDE_ANSWER) {
                        item.judgeUserAnswer();
                    }
                    saveUserAnswerToDb();
                }
                groupRoot = false;
            }

            if (mode == MODE_JUDE_ANSWER) {
                judgeAnswerSubject(id);
            }
        }
        // 初始化正确答案的显示状态
        if (mode == MODE_INITIAL || mode == MODE_SHOW_USER_ANSWER) {
            setCorrectAnswerVisibility(View.GONE);
        } else {
            setCorrectAnswerVisibility(View.VISIBLE);
        }
    }

    /**
     * 判断答案的正确性
     *
     * @return 获取的分数
     */
    public float judgeAnswerSubject(int id) {
        // 判断每个item
        for (EntryItem item : items) {
            item.judgeUserAnswer();
        }
        float totalScore = 0;
        for (int a = 0; a < groupBorrowAnswerList.size(); a++) {
            borrowAnswerList = new ArrayList<String>();
            loanAnswerList = new ArrayList<String>();
            for (String str : groupBorrowAnswerList.get(a).split(">>>")) {
                borrowAnswerList.add(str);
            }
            for (String str : groupLoanAnswerList.get(a).split(">>>")) {
                loanAnswerList.add(str);
            }
            List<EntryItem> singleItems = new ArrayList<EntryItem>();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getGroupId() == a) {
                    singleItems.add(items.get(i));
                }
            }
            score = BillDataModel.getInstance(mContext).judeAnswer(borrowAnswerList, loanAnswerList, singleItems, singleScore);
            totalScore = totalScore + score;
        }
        if (mShowAnswer) {
            if (totalScore == data.getScore()) {
//				ivResult.setBackgroundResource(R.drawable.iv_entry_correct);
            } else {
//				ivResult.setBackgroundResource(R.drawable.iv_entry_error);
            }
        }
        setCorrectAnswerVisibility(View.VISIBLE);

        return totalScore;
    }

    /**
     * 获取得分
     *
     * @return
     */
    public float getScore() {
        return score;
    }

    /**
     * 保存用户答案
     */
    public void saveUserAnswer() {
        StringBuilder borrow = new StringBuilder("");
        StringBuilder loan = new StringBuilder("");
        for (EntryItem item : items) {
            if (item.mType == EntryItem.TYPE_BORROW) {
                if (borrow.toString().equals("")) {
                    borrow.append(item.getUserAnswer());
                } else {
                    borrow.append(item.getUserAnswer());
                }
            } else {
                if (loan.toString().equals("")) {
                    loan.append(item.getUserAnswer());
                } else {
                    loan.append(item.getUserAnswer());
                }
            }
        }
        data.setBorrowUser(borrow.toString());
        data.setLoanUser(loan.toString());
    }

    /**
     * 保存用户答案到数据库
     */
    public void saveUserAnswerToDb() {
        StringBuilder borrow = new StringBuilder("");
        StringBuilder loan = new StringBuilder("");
        // 组数
        int groupCount = llgroupLayout.getChildCount();
        for (int i = 0; i < groupCount; i++) {
            // 组控件，包含借和贷
            LinearLayout groupLayout = (LinearLayout) llgroupLayout.getChildAt(i);
            // 借的容器
            LinearLayout borrowLayout = (LinearLayout) groupLayout.getChildAt(0);
            int borrowCount = borrowLayout.getChildCount();// 借组数
            for (int j = 0; j < borrowCount; j++) {
                // 一个借item
                LinearLayout itemView = (LinearLayout) borrowLayout.getChildAt(j);
                Object obj = itemView.getTag();
                EntryItem info = (EntryItem) obj;
                if (info.mType == EntryItem.TYPE_BORROW) {
                    if (borrow.toString().equals("")) {
                        borrow.append(info.getUserAnswer());
                    } else {
                        if (borrow.substring((borrow.length() - 3)).equals("&&&")) {
                            borrow.append(info.getUserAnswer());
                        } else {
                            borrow.append(">>>" + info.getUserAnswer());
                        }
                    }
                }
            }
            borrow.append("&&&");
            Log.d(TAG, "obj:borrow= " + borrow.toString());

            // 贷的容器
            LinearLayout loanLayout = (LinearLayout) groupLayout.getChildAt(1);
            int loanCount = loanLayout.getChildCount();// 贷组数
            for (int j = 0; j < loanCount; j++) {
                // 一个贷item
                LinearLayout itemView = (LinearLayout) loanLayout.getChildAt(j);
                Object obj = itemView.getTag();
                EntryItem info = (EntryItem) obj;
                if (info.mType == EntryItem.TYPE_LOAN) {
                    if (loan.toString().equals("")) {
                        loan.append(info.getUserAnswer());
                    } else {
                        if (loan.substring((loan.length() - 3)).equals("&&&")) {
                            loan.append(info.getUserAnswer());
                        } else {
                            loan.append(">>>" + info.getUserAnswer());
                        }
                    }
                }
            }
            loan.append("&&&");
            Log.d(TAG, "obj:loan= " + loan.toString());

            // 初始化正确答案

            float totalScore = 0;
            for (int a = 0; a < groupBorrowAnswerList.size(); a++) {
                borrowAnswerList = new ArrayList<String>();
                loanAnswerList = new ArrayList<String>();
                for (String str : groupBorrowAnswerList.get(a).split(">>>")) {
                    borrowAnswerList.add(str);
                }
                for (String str : groupLoanAnswerList.get(a).split(">>>")) {
                    loanAnswerList.add(str);
                }
                List<EntryItem> singleItems = new ArrayList<EntryItem>();
                for (int s = 0; s < items.size(); s++) {
                    if (items.get(s).getGroupId() == a) {
                        singleItems.add(items.get(s));
                    }
                }
                score = BillDataModel.getInstance(mContext).getjudeAnswer(borrowAnswerList, loanAnswerList, singleItems, singleScore);
                totalScore = totalScore + score;
            }
            data.setuScore(totalScore);
            if (loan.length() > 3 && loan.substring((loan.length() - 3)).equals("&&&")) {
                data.setBorrowUser(borrow.substring(0, (borrow.length() - 3)).toString());
                data.setLoanUser(loan.substring(0, (loan.length() - 3)).toString());
                // 把答案保存到数据库
                BillDataModel.getInstance(mContext).saveUserAnswer(data.getId(), borrow.substring(0, (borrow.length() - 3)).toString(), loan.substring(0, (loan.length() - 3)).toString());
            } else {
                data.setBorrowUser(borrow.toString());
                data.setLoanUser(loan.toString());
                // 把答案保存到数据库
                BillDataModel.getInstance(mContext).saveUserAnswer(data.getId(), borrow.toString(), loan.toString());
            }
        }
    }

    /**
     * 获取分录题信息
     *
     * @return
     */
    public SubjectEntryData getData() {
        return data;
    }

    /**
     * 移除条目
     *
     * @param view      移除的视图
     * @param type      借贷类型
     * @param entryItem
     */
    private void removeItem(int tag, View view, int type, EntryItem entryItem) {
        items.remove(entryItem);
        if (type == EntryItem.TYPE_BORROW) {
            llBorrowContent = (LinearLayout) view.getParent();
            llBorrowContent.removeView(view);
        } else if (type == EntryItem.TYPE_LOAN) {
            llLoanContent = (LinearLayout) view.getParent();
            llLoanContent.removeView(view);
        }
    }

    private void removeGroup(Object tag, View view) {
        llgroupLayout.removeView(viewChildList.get((Integer) tag));
    }

    /**
     * 移除条目
     *
     * @param root 是否为根节点
     * @param type 借贷类型
     * @return
     */
    private EntryItem addChildItem(Object tag, boolean root, int type) {
        EntryItem item = null;
        if (type == EntryItem.TYPE_BORROW) {
            item = addChildBorrowItem(tag, root);
        } else if (type == EntryItem.TYPE_LOAN) {
            item = addChildLoanItem(tag, root);
        }
        entryId++;
        items.add(item);
        // saveUserAnswerToDb();
        return item;
    }

    /**
     * 添加借方条目
     *
     * @param root 是否为根节点
     */
    private EntryItem addChildBorrowItem(Object tag, boolean root) {
        EntryItem item = new EntryItem(entryId);
        View view = item.createItem(tag, EntryItem.TYPE_BORROW, root);
        view.setTag(item);
        setItem(view);
        for (int i = 0; i < ViewllBorrowList.size(); i++) {
            if (ViewllBorrowList.get(i).getTag() == tag) {
                ViewllBorrowList.get(i).addView(view);
            }
        }
        return item;
    }

    /**
     * 添加贷方条目
     *
     * @param root 是否为根节点
     */
    private EntryItem addChildLoanItem(Object tag, boolean root) {
        EntryItem item = new EntryItem(entryId);
        View view = item.createItem(tag, EntryItem.TYPE_LOAN, root);
        view.setTag(item);
        setItem(view);
        for (int i = 0; i < ViewllLoanList.size(); i++) {
            if (ViewllLoanList.get(i).getTag() == tag) {
                ViewllLoanList.get(i).addView(view);
            }
        }
        return item;
    }

    /**
     * 设置条目属性
     */
    private void setItem(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        view.setLayoutParams(lp);
    }

    /**
     * 是否显示正确答案
     *
     * @param visibility
     */
    private void setCorrectAnswerVisibility(int visibility) {
        if (visibility == View.VISIBLE && !mShowAnswer) {
            return;
        }
        divideLine.setVisibility(visibility);
        tvCorrectAnswer.setVisibility(visibility);
        ivResult.setVisibility(visibility);
    }

    /**
     * 设置金额输入键盘
     *
     * @param keyboard
     */
    public void setKeyboard(KeyboardView keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * 获取item的一级科目id,如果当前item的一级科目id为-1，则取前一个item的一级科目id
     *
     * @param type
     * @param item
     * @return
     */
    private int getPrimaryId(View view, int type, EntryItem item) {
        int id = -1;

        LinearLayout tmp;
        if (type == EntryItem.TYPE_BORROW) {
            tmp = (LinearLayout) view.getParent().getParent();
        } else {
            tmp = (LinearLayout) view.getParent().getParent();
        }
        for (int i = 0; i < tmp.getChildCount(); i++) {
            if (tmp.getChildAt(i).getTag() == item) {
                if (i == 0) {
                    id = item.primaryId;
                } else {
                    if (item.primaryId != -1) {
                        id = item.primaryId;
                    } else {
                        id = ((EntryItem) tmp.getChildAt(i - 1).getTag()).primaryId;
                    }
                }

                break;
            }
        }

        Log.d(TAG, "id:" + id);
        return id;
    }

    /**
     * 对一个借贷条目的封装
     *
     * @author lucher
     */
    public class EntryItem implements OnClickListener {

        /**
         * 类型为借款方
         */
        public static final int TYPE_BORROW = 1;
        /**
         * 类型为贷款方
         */
        public static final int TYPE_LOAN = 2;

        // item对应的id
        private int id;

        // 一个条目对应的视图
        private View mView;
        // 标签，借或贷
        private TextView tvLabel;
        // 一级科目
        private TextView tvPSubject;
        // 二级科目
        private TextView tvSSubject;
        // 金额
        private EditText etAmount;
        // 添加或移除按钮
        private Button btnAddOrRemove;
        // 删除一组借贷
        private Button btnRmovegroup;

        // 是否为根节点
        private boolean mRoot;
        // 借贷类别
        public int mType;

        // 一级科目选中的id
        public int primaryId = -1;
        // 二级科目选中的id
        private int secondaryId = -1;
        // 保存选中二级科目时对应一级科目的id
        private int tmpPId = -1;
        // 对应的info
        private ItemInfo mInfo;
        // 对应所在组的item
        private int groupId;
        private Map<String, View> map;

        /**
         * 获取groupId
         *
         * @return
         */
        public int getGroupId() {
            return groupId;
        }

        // 一级科目点击监听
        private PrimarySubjectDialog.OnPItemClickListener pListener = new PrimarySubjectDialog.OnPItemClickListener() {

            @Override
            public void onPItemClick(int id, String text) {
                primaryId = id;
                tvPSubject.setText(text);
                checkSecondarySubject(primaryId);
                saveUserAnswerToDb();
                BillDataModel.getInstance(mContext).updateRemark(data.getId(), "1");
                // SubjectEntryData datas=(SubjectEntryData)
                // SubjectEntryDataDao.getInstance(mContext).getDataById(data.getId());
                // if(datas.getRemark().equals("1")){
                data.setDone(true);
                // }
            }
        };

        // 二级科目点击监听
        private SencondarySubjectDialog.OnSItemClickListener sListener = new SencondarySubjectDialog.OnSItemClickListener() {

            @Override
            public void onSItemClick(int id, String text) {

                secondaryId = id;
                tmpPId = primaryId;
                if (text.equals("空")) {
                    text = "";
                }
                tvSSubject.setText(text);
                saveUserAnswerToDb();
                BillDataModel.getInstance(mContext).updateRemark(data.getId(), "1");
                // SubjectEntryData datas=(SubjectEntryData)
                // SubjectEntryDataDao.getInstance(mContext).getDataById(data.getId());
                // if(datas.getRemark().equals("1")){
                data.setDone(true);
                // }
            }
        };

        public EntryItem(int id) {
            this.id = id;
        }

        /**
         * 创建item
         *
         * @param type
         * @param root
         * @return
         */
        public View createItem(Object tag, int type, boolean root) {
            View view = View.inflate(mContext, R.layout.layout_entry_item, null);
            initView(view);

            mRoot = root;
            initState(type);
            mType = type;
            groupId = (Integer) tag;
            return view;
        }

        /**
         * 选择一级科目后检查二级科目是否属于当前一级科目，若不是则把二级科目抹掉
         *
         * @param pId
         */
        private void checkSecondarySubject(int pId) {
            if (pId != tmpPId) {
                tvSSubject.setText("");
                secondaryId = -1;
            }
        }

        /**
         * 初始化视图
         *
         * @param view
         */
        private void initView(View view) {
            map = new HashMap<String, View>();
            tvLabel = (TextView) view.findViewById(R.id.tv_label);
            tvPSubject = (TextView) view.findViewById(R.id.tv_primary);
            tvSSubject = (TextView) view.findViewById(R.id.tv_secondary);
            etAmount = (EditText) view.findViewById(R.id.et_amount);
            btnAddOrRemove = (Button) view.findViewById(R.id.btn_add_or_remove);
            btnRmovegroup = (Button) view.findViewById(R.id.btn_rmovegroup);
            btnRmovegroup.setOnClickListener(this);
            tvPSubject.setOnClickListener(this);
            tvSSubject.setOnClickListener(this);
            btnAddOrRemove.setOnClickListener(this);
            mView = view;

            EditTextUtil.setInputtypeNull(etAmount);
            etAmount.setOnClickListener(null);
            etAmount.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        etAmount.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                saveUserAnswerToDb();
                                BillDataModel.getInstance(mContext).updateRemark(data.getId(), "1");
                                data.setDone(true);
                            }
                        });
                    }
                    return false;
                }
            });

            if (keyboard != null) {
                keyboard.addEditText(etAmount);

            }
            primaryDialog.setOnPItemClickListener(id, pListener);
            secondaryDialog.setOnSItemClickListener(id, sListener);

        }

        /**
         * 初始化状态
         */
        private void initState(int type) {
            if (type == TYPE_BORROW) {
                tvLabel.setText("  借  ");
                tvLabel.setTextColor(getResources().getColor(R.color.colorBlueBackground));
            } else if (type == TYPE_LOAN) {
                tvLabel.setText("  贷  ");
                tvLabel.setTextColor(getResources().getColor(R.color.colorNodeListItem));
            }

            if (mRoot) {// 根节点的item只能新增item
                if (type == TYPE_BORROW) {
                    btnAddOrRemove.setBackgroundResource(R.mipmap.jiejia);
                } else if (type == TYPE_LOAN) {
                    btnAddOrRemove.setBackgroundResource(R.mipmap.daijia);
                }
                btnAddOrRemove.setTag(groupItemId - 1);
                if (!groupRoot && type == TYPE_BORROW) {
                    btnRmovegroup.setVisibility(VISIBLE);
                    btnRmovegroup.setTag(groupItemId - 1);
                    if (type == TYPE_BORROW) {
                        btnRmovegroup.setBackgroundResource(R.mipmap.jiejian);
                    } else if (type == TYPE_LOAN) {
                        btnRmovegroup.setBackgroundResource(R.mipmap.daijian);
                    }
                }
            } else {// 子节点的item只能移除自身
                tvLabel.setText("");
                btnAddOrRemove.setTag(entryId + "_" + (groupItemId - 1));
                map.put("del" + entryId, mView);
                mViewsList.add(mView);
//                btnAddOrRemove.setBackgroundResource(R.drawable.btn_selector_minus);
                if (type == TYPE_BORROW) {
                    btnAddOrRemove.setBackgroundResource(R.mipmap.jiejian);
                } else if (type == TYPE_LOAN) {
                    btnAddOrRemove.setBackgroundResource(R.mipmap.daijian);
                }
            }
        }

        // 把info信息应用到该item
        public void applyInfo(ItemInfo info) {
            mInfo = info;
        }

        // 显示用户答案
        public void showUserAnswer() {
            tmpPId = primaryId = mInfo.getPrimaryId();
            secondaryId = mInfo.getSecondId();
            if (primaryId != -1) {
//                tvPSubject.setText(FirstSubjectDataModel.getInstance(mContext).getDataById(primaryId).getName());
            }
            if (secondaryId != -1) {
//                tvSSubject.setText(SecondSubjectDataModel.getInstance(mContext).getDataById(secondaryId).getName());
            }
            etAmount.setText(mInfo.getAmount());

        }

        /**
         * 判断答案
         */
        public void judgeUserAnswer() {
            tvPSubject.setEnabled(false);
            tvPSubject.setTextColor(Color.BLACK);
            tvSSubject.setEnabled(false);
            tvSSubject.setTextColor(Color.BLACK);
            etAmount.setEnabled(false);
            etAmount.setTextColor(Color.BLACK);
            btnAddOrRemove.setVisibility(View.GONE);
            btnGroupAddOrRemove.setVisibility(View.INVISIBLE);
            btnRmovegroup.setVisibility(View.INVISIBLE);
        }

        /**
         * 判断答案
         */
        public void judgeHideUserAnswer() {
            tvPSubject.setEnabled(true);
            tvPSubject.setTextColor(Color.BLACK);
            tvSSubject.setEnabled(true);
            tvSSubject.setTextColor(Color.BLACK);
            etAmount.setEnabled(true);
            etAmount.setTextColor(Color.BLACK);
            btnAddOrRemove.setVisibility(View.VISIBLE);
            btnGroupAddOrRemove.setVisibility(View.VISIBLE);
        }

        /**
         * 设置每个空是否正确
         *
         * @param primarySubject
         * @param sencondarySubject
         * @param amount
         */
        public void setUserAnswerCorrect(boolean primarySubject, boolean sencondarySubject, boolean amount) {
            if (!mShowAnswer) {
                return;
            }
            if (!primarySubject) {
//				tvPSubject.setBackgroundResource(R.drawable.entry_item_subject_error);
            } else {
//				tvPSubject.setBackgroundResource(R.drawable.entry_item_subject_correct);
            }
            if (!sencondarySubject) {
//				tvSSubject.setBackgroundResource(R.drawable.entry_item_subject_error);
            } else {
//				tvSSubject.setBackgroundResource(R.drawable.entry_item_subject_correct);
            }
            if (!amount) {
//				etAmount.setBackgroundResource(R.drawable.entry_item_error);
            } else {
//				etAmount.setBackgroundResource(R.drawable.entry_item_correct);
            }
        }

        @Override
        public void onClick(View view) {
            BillDataModel.getInstance(mContext).updateRemark(data.getId(), "1");
            data.setDone(true);
            if (view == btnAddOrRemove) {
                if (mRoot) {
                    addChildItem(btnAddOrRemove.getTag(), false, mType);

                } else {
                    String tmp[] = btnAddOrRemove.getTag().toString().split("_");
                    for (int i = 0; i < mViewsList.size(); i++) {
                        if (map.get("del" + tmp[0]) == mViewsList.get(i)) {
                            removeItem(Integer.valueOf(tmp[1]), mViewsList.get(i), mType, this);
                            mViewsList.remove(i);
                        }
                    }
                }
                saveUserAnswerToDb();
            } else if (view == btnRmovegroup) {
                removeGroup(btnRmovegroup.getTag(), viewChild);
                saveUserAnswerToDb();
            } else if (view == tvPSubject) {
                if (!primaryDialog.isShowing()) {
                    primaryDialog.show(id);
                }
            } else if (view == tvSSubject) {
                if (!secondaryDialog.isShowing()) {
                    primaryId = getPrimaryId(view, mType, this);
                    secondaryDialog.show(id, primaryId);
                }
            }

        }

        /**
         * 获取用户答案
         *
         * @return
         */
        public String getUserAnswer() {
            String amount = etAmount.getText().toString().trim();
            return primaryId + "=" + secondaryId + "=" + amount;
        }
    }

    /**
     * 对每个条目数据的封装
     *
     * @author lucher
     */
    public class ItemInfo {
        // 借方用户答案
        public String userAnswer;

        // 一级科目id
        private int primaryId;
        // 二级科目id
        private int secondId;
        // 金额
        private String amount;

        public void setUserAnswer(String userAnswer) {
            if (userAnswer.equals("")) {
                userAnswer = "-1=-1=";
            }
            this.userAnswer = userAnswer;

            String[] userAnswers = userAnswer.split("=");
            primaryId = Integer.parseInt(userAnswers[0]);
            secondId = Integer.parseInt(userAnswers[1]);
            if (userAnswers.length == 3) {
                amount = userAnswers[2];
            }
        }

        public int getPrimaryId() {
            return primaryId;
        }

        public int getSecondId() {
            return secondId;
        }

        public String getAmount() {
            return amount;
        }
    }
}
