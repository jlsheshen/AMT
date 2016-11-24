package com.edu.accountingteachingmaterial.presenterview;

/**
 * Created by Administrator on 2016/11/8.
 */
public interface MediaAtyView {
    /**
     * 开始播放
     */
    void start();
    /**
     * 暂停功能
     * @return
     */
    void pause();

    /**
     * 继续功能
     */
    void onContinue();


    /**
     * 重新开始功能
     */
    void restart();

    /**
     * 拖动进度条
     */
    void onDragBar();

    /**
     * 显示当前播放进度
     * @return 当前播放进度Time
     */
    String timeNow();

    /**
     * 总时间
     * @return
     */
    String timeMax();

}
