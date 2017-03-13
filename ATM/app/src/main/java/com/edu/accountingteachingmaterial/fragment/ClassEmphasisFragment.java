package com.edu.accountingteachingmaterial.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseFragment;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.constant.UriConstant;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;

import java.util.Map;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.COURSE_ID;

public class ClassEmphasisFragment extends BaseFragment {

    WebView wView;
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
    protected void initView(View view) {
        wView = bindView(R.id.emphasis_wv);
    }

    @Override
    protected void initData() {
        String courseId = PreferenceHelper.getInstance(context).getStringValue(COURSE_ID);
        String url = NetUrlContstant.getEmphasisUrl() + courseId + "-" + 289;
        Log.d("ClassEmphasisFragment", url);
        wView.loadUrl(UriConstant.ASSETS_PATH + "index.html");
//        wView.getSettings().setJavaScriptEnabled(true);
//        wView.loadUrl("www.baidu.com");
//        try {
//            extraHeaders = new HashMap<String, String>();
//            extraHeaders.put(TOKEN, PreferenceHelper.getInstance(context).getStringValue(TOKEN));
//            Log.d("ClassEmphasisFragment", String.valueOf(extraHeaders));
//            wView.loadUrl(url,extraHeaders);
//            wView.reload();
//            wView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//            wView.getSettings().setJavaScriptEnabled(true);
//            wView.setWebViewClient(new WebViewClient(){
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    // TODO Auto-generated method stub
//                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                    view.loadUrl(url,extraHeaders);
//                    return true;
//                }
//            });
//        } catch (Exception e) {
//            wView.loadUrl(UriConstant.ASSETS_PATH + "index.html");
//        }
        // TODO Auto-generated method stub
    }

}
