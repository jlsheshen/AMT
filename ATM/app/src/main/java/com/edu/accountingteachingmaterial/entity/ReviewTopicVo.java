package com.edu.accountingteachingmaterial.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ReviewTopicVo {

    private List<Float> level;
    private List<Integer> topic_type;

    public List<Float> getLevel() {
        return level;
    }

    public void setLevel(List<Float> level) {
        this.level = level;
    }

    public List<Integer> getTopic_type() {
        return topic_type;
    }

    public void setTopic_type(List<Integer> topic_type) {
        this.topic_type = topic_type;
    }
}
