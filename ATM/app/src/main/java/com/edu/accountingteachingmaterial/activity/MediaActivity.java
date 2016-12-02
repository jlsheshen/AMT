package com.edu.accountingteachingmaterial.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseMvpActivity;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.constant.UriConstant;
import com.edu.accountingteachingmaterial.presenterview.MediaAtyPresenter;
import com.edu.accountingteachingmaterial.presenterview.MediaAtyView;

import java.io.File;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MediaActivity extends BaseMvpActivity<MediaAtyView, MediaAtyPresenter> implements MediaAtyView {
    String TAG = "MediaActivity";
    public VideoView videoView;
    SeekBar seekBar;
    MediaController mController;
    private boolean isPlaying;
    //    private ClassicCase classicCase;
    private String url = NetUrlContstant.BASE_URL + "interface/filedown/down/";
    ExampleBean exampleBeans;

    @Override
    public int setLayout() {
        return R.layout.activity_media;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        upload();
        Bundle bundle = getIntent().getExtras();
        exampleBeans = (ExampleBean) bundle.getSerializable("exampleBeans");
        String mUrl = url + exampleBeans.getUrl();
        videoView = bindView(R.id.media_vv);
        mController = new MediaController(this);
        // presenter.start();
        // 设置播放视频源的路径
        //videoView.setVideoPath(UriConstant.VIDEO_PATH + "aaa.mp4");
        //videoView.setVideoURI(Uri.parse("http://192.168.1.159/interface/filedown/down/439"));
        videoView.setVideoURI(Uri.parse(mUrl));

        // 为VideoView指定MediaController
        videoView.setMediaController(mController);

        videoView.start();

        // 为MediaController指定控制的VideoView
        mController.setMediaPlayer(videoView);
        // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
        mController.setPrevNextListeners(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });


//     seekBar = bindView(R.id.media_sb);

//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                // 设置进度条的最大进度为视频流的最大播放时长
//               seekBar.setMax(videoView.getDuration());
//
//            }
//        });


//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // 当进度条停止修改的时候触发
//                // 取得当前进度条的刻度
//                int progress = seekBar.getProgress();
//                if (videoView != null && videoView.isPlaying()) {
//                    // 设置当前播放的位置
//                    videoView.seekTo(progress);
//                }
//            }
////        });
//        play(0);
    }

    protected void play(int msec) {
        Log.i(TAG, " 获取视频文件地址");
        String path = UriConstant.VIDEO_PATH + "VID_20161103_125654.mp4";

        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_LONG).show();
            return;
        }

        Log.i(TAG, "指定视频源路径");
        videoView.setVideoPath(file.getAbsolutePath());
        Log.i(TAG, "开始播放");
        videoView.start();

        // 按照初始位置播放
        videoView.seekTo(msec);
        // 开始线程，更新进度条的刻度
        new Thread() {

            @Override
            public void run() {
                try {
                    isPlaying = true;
                    while (isPlaying) {
                        // 如果正在播放，没0.5.毫秒更新一次进度条
                        int current = videoView.getCurrentPosition();
                        Log.i(TAG, " 文件目前为止" + current);

                        seekBar.setProgress(current);

                        sleep(500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;

            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 发生错误重新播放
                play(0);
                isPlaying = false;
                return false;
            }
        });
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

//
//    /**
//     * 根据用户id请求用户数据
//     */
//    private void upload() {
//        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
//        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.classicCaseUrl + "291-2");
//        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
//        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                if (jsonObject.getString("success").equals("true")) {
//                    List<ClassicCase> hData = JSON.parseArray(jsonObject.getString("message"), ClassicCase.class);
//                    classicCase = hData.get(0);
//                    Log.d(TAG, "classicCase" + "2016lkkkkkkkkkk");
//                }
//            }
//
//            @Override
//            public void onFailure(String errorInfo) {
//                Log.d("LaunchActivity", errorInfo);
//
//            }
//        });
//    }
}