package com.edu.accountingteachingmaterial.bean;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2017/5/5.
 */

public class UpdateScoreBean  extends BaseData{

    private Long   topic_id;
    private float score;

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
