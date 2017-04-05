package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.util.net.EmphasisManager;

import java.util.Map;

/**
 * 重点难点
 */
public class ClassEmphasisFragment extends BaseFragment implements EmphasisManager.EmphasisListener {

    WebView wView;
    TextView nothingTv;

    int chapter;
    Map<String, String> extraHeaders;


    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
        Log.d("ClassEmphasisFragment", "chapter:" + chapter);

    }

    @Override
    protected int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.fragment_class_emphasis;
    }

    @Override
    public void onResume() {
        super.onResume();
        EmphasisManager.getSingleton(context).getEmphasis(this,chapter);

    }

    @Override
    protected void initView(View view) {
        wView = bindView(R.id.emphasis_wv);
        nothingTv = bindView(R.id.no_content_tv);

    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSuccess(String message) {
        Log.d("ClassEmphasisFragment", message);
        String encoding = "UTF-8";
        String mimeType = "text/html";
        Log.d("WebActivity", " ++++ " + message);
        WebSettings webSettings = wView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wView.addJavascriptInterface(this, "wx");
        wView.getSettings().setBuiltInZoomControls(true);
        wView.loadDataWithBaseURL("file://", message,mimeType, encoding, "about:blank");
    }

    @Override
    public void onFailure(String message) {
        nothingTv.setVisibility(View.VISIBLE);
        wView.setVisibility(View.GONE);

    }
    @android.webkit.JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "actionFromJsWithParam" + str, Toast.LENGTH_SHORT).show();

                Log.d("MainActivity", "触发图片点击事件");

//                showPicture("a1/" + str + ".png");
            }
        });
    }
}
