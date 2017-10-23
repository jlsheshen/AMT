package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseMvpActivity;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.presenterview.MediaAtyPresenter;
import com.edu.accountingteachingmaterial.presenterview.MediaAtyView;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MediaActivity extends BaseMvpActivity<MediaAtyView, MediaAtyPresenter> implements MediaAtyView {

    private boolean isPlaying;
    JZVideoPlayerStandard player;
    private String url = NetUrlContstant.getMediaorPdfUrl();
    ExampleBean exampleBeans;

    @Override
    public int setLayout() {
        return R.layout.activity_media;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        exampleBeans = (ExampleBean) bundle.getSerializable("exampleBeans");
        String mUrl = url + exampleBeans.getUrl();
        Log.d("MediaActivity", mUrl);

        player = (JZVideoPlayerStandard) findViewById(R.id.player_video);
//        if (player != null) {
//            player.release();
//        }
        player.fullscreenButton.setVisibility(View.GONE);
        player.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.backPress();
                finish();
            }
        });
        player.setUp(mUrl, JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN, "");

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            finish();
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
    @Override
    public void initData() {

//        play(0);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onContinue() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void onDragBar() {

    }

    @Override
    public String timeNow() {
        return null;
    }

    @Override
    public String timeMax() {
        return null;
    }

    @Override
    public MediaAtyPresenter initPresenter() {
        return new MediaAtyPresenter();
    }

}