package com.edu.accountingteachingmaterial.entity;

import com.edu.library.data.BaseData;

/**
 * Created by Administrator on 2016/11/30.
 */

public class StartExamData extends BaseData {


    /**
     * remaining : 13462
     * started_time : 4538
     */

    private int remaining;
    private int started_time;

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getStarted_time() {
        return started_time;
    }

    public void setStarted_time(int started_time) {
        this.started_time = started_time;
    }
}
