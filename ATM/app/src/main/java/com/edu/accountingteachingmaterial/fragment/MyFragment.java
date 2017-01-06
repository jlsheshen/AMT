package com.edu.accountingteachingmaterial.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.view.CircleImageView;

public class MyFragment extends BaseFragment implements View.OnClickListener {
    CircleImageView circleImageView;
    TextView textView;
    RadioButton errorButton, downloadButton;
    MyErrorsFragment errorView, downloadView;


    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {
        circleImageView = bindView(R.id.my_head_civ);
        textView = bindView(R.id.my_name_tv);
        errorButton = bindView(R.id.my_download_rb);
        errorButton.setOnClickListener(this);
        downloadButton = bindView(R.id.my_error_rb);
        downloadButton.setOnClickListener(this);

        // TODO Auto-generated method stub

    }

    @Override
    protected void initData() {


        // TODO Auto-generated method stub

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null == errorView) {
            errorView = new MyErrorsFragment();
        }
        replaceFragment(errorView);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.my_download_rb:
                if (null == downloadView) {
                    downloadView = new MyErrorsFragment();
                }
                replaceFragment(downloadView);
                break;
            case R.id.my_error_rb:
                if (null == errorView) {
                    errorView = new MyErrorsFragment();

                }
                replaceFragment(errorView);

                break;

        }

    }

    @Override
    public void onDestroy() {
        errorView = null;
        super.onDestroy();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.my_view, fragment);
        // Commit the transaction
        transaction.commit();
        errorView.setData();

    }
}
