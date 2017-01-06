package com.edu.accountingteachingmaterial.base;

import android.content.Context;
import android.widget.BaseAdapter;

import com.edu.accountingteachingmaterial.adapter.ReviewHisAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public abstract class BaseCheckAdapter extends BaseAdapter {

    protected boolean checkIsShow = false;
    protected List<Boolean> checkList;
    protected  ReviewHisAdapter.OnCheckedListener checkedListener;
    protected Context context;

    public BaseCheckAdapter(Context context) {
        this.context = context;
    }



    // 初始化isSelected的数据
    private void checkRest(){
            for(int i=0; i<getCount();i++) {
                getIsChecked().add(i,false);
            }


    }


    public void setClickShow() {
        if (checkList==null){
            checkList  = new ArrayList<>();
            checkRest();
        }
        checkIsShow = true;

        notifyDataSetChanged();
    }
    public void setClickConceal() {
        checkIsShow = false;
        checkList=null;
        notifyDataSetChanged();
    }
    public  List<Boolean> getIsChecked() {
        return checkList;
    }
//    public  HashMap<Integer,Boolean> getIsChecked() {
//        return isCheck;
//    }

    public  void setIsChecked(int i,boolean b) {
        this.checkList.set(i,b);
    }

    public void setAllchecked() {
        for(int i=0; i< getIsChecked().size();i++) {
            getIsChecked().set(i,true);
        }
        notifyDataSetChanged();
    }
}
